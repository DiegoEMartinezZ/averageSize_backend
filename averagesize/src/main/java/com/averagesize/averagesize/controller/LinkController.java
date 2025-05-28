package com.averagesize.averagesize.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averagesize.averagesize.dto.LinkReqDTO;
import com.averagesize.averagesize.dto.LinkResDTO;
import com.averagesize.averagesize.dto.LinkUpdateDTO;
import com.averagesize.averagesize.service.LinkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    // Create Link
    @PostMapping("/{userId}")
    public ResponseEntity<LinkResDTO> createLink(@PathVariable UUID userId,
            @RequestBody LinkReqDTO linkReqDTO) {
        LinkResDTO createdLink = linkService.createLink(linkReqDTO, userId);
        return ResponseEntity.ok(createdLink);
    }

    // Get link by ID
    @GetMapping("/{id}")
    public ResponseEntity<LinkResDTO> getLinkById(@PathVariable long id,
            @RequestBody LinkReqDTO linkReqDTO) {
        LinkResDTO link = linkService.getLinkById(id);
        if (link == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(link);
    }

    // Get link by Short URL
    @GetMapping("/shortUrl/{shortUrl}")
    public ResponseEntity<LinkResDTO> getLinkByShortUrl(@PathVariable String shortUrl) {
        LinkResDTO link = linkService.getLinkByShortUrl(shortUrl);
        if (link == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(link);
    }

    // Get all links by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LinkResDTO>> getAllLinksByUserId(@PathVariable UUID userId) {
        List<LinkResDTO> links = linkService.getAllLinksByUserId(userId);
        if (links.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(links);
    }

    // Get all active links
    @GetMapping("/active")
    public ResponseEntity<List<LinkResDTO>> getAllActiveLinks() {
        List<LinkResDTO> links = linkService.getAllActiveLinks();
        if (links.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(links);
    }

    // Update link
    @PostMapping("/{linkId}/update")
    public ResponseEntity<LinkResDTO> updateLink(@PathVariable long linkId,
            @RequestBody LinkUpdateDTO linkUpdateDTO) {
        LinkResDTO updatedLink = linkService.updateLink(linkId, linkUpdateDTO);
        if (updatedLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedLink);
    }

    // Delete Link
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable long id) {
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }

    // Deactivate link
    @PostMapping("/{id}/deactivate")
    public ResponseEntity<LinkResDTO> deactivateLink(@PathVariable long id) {
        LinkResDTO deactivatedLink = linkService.deactivateLink(id);
        if (deactivatedLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deactivatedLink);
    }

    // Activate link
    @PostMapping("/{id}/activate")
    public ResponseEntity<LinkResDTO> activateLink(@PathVariable long id) {
        LinkResDTO activatedLink = linkService.activateLink(id);
        if (activatedLink == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(activatedLink);
    }

    // Generate short URL
    @GetMapping("/generateShortUrl")
    public ResponseEntity<String> generateShortUrl() {
        String shortUrl = linkService.generateShortUrl();
        return ResponseEntity.ok(shortUrl);
    }
}
