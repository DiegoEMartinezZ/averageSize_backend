package com.averagesize.averagesize.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.averagesize.averagesize.dto.ClickResDTO;
import com.averagesize.averagesize.service.ClickService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/click")
@RequiredArgsConstructor
public class ClickController {
    private final ClickService clickService;

    // List clicks
    @GetMapping
    public ResponseEntity<List<ClickResDTO>> findAll() {
        List<ClickResDTO> clicks = clickService.findAll();
        return ResponseEntity.ok(clicks);
    }

    // Find clicks by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClickResDTO> findById(long id) {
        ClickResDTO click = clickService.findById(id);
        if (click == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(click);
    }

    // Find clicks by link ID
    @GetMapping("/link/{linkId}")
    public ResponseEntity<List<ClickResDTO>> findByLinkId(@PathVariable long linkId) {
        List<ClickResDTO> clicks = clickService.findByLinkId(linkId);
        return ResponseEntity.ok(clicks);
    }

    // Count clicks for a specific link
    @GetMapping("/count/{linkId}")
    public ResponseEntity<Long> countByLinkIdLink(@PathVariable long linkId) {
        long count = clickService.countByLinkId(linkId);
        return ResponseEntity.ok(count);
    }
}
