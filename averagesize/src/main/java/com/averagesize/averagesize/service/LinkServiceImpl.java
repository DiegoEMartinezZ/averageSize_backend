package com.averagesize.averagesize.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.averagesize.averagesize.entity.Link;
import com.averagesize.averagesize.repository.LinkRepository;

@Service
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;

    public LinkServiceImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public Link createLink(Link link) {
        if (link.getCreatedAt() == null) {
            link.setCreatedAt(LocalDateTime.now());
        }

        if (link.getUrlShort() == null || link.getUrlShort().isEmpty()) {
            link.setUrlShort(generateShortUrl());
        }

        link.setActive(true);
        return linkRepository.save(link);
    }

    @Override
    public Optional<Link> getLinkById(long id) {
        return linkRepository.findById(id);
    }

    @Override
    public Link getLinkByShortUrl(String shortUrl) {
        return linkRepository.findByUrlShort(shortUrl)
                .orElseThrow(() -> new RuntimeException("Link not found with short URL: " + shortUrl));
    }

    @Override
    public List<Link> getAllLinksByUserId(long userId) {
        return linkRepository.findByUserIdUser(userId);
    }

    @Override
    public List<Link> getAllActiveLinks() {
        return linkRepository.findByActiveTrue();
    }

    @Override
    public Link updateLink(Link link) {
        if (!linkRepository.existsById(link.getIdLink())) {
            throw new RuntimeException("Link not found with id: " + link.getIdLink());
        }
        return linkRepository.save(link);
    }

    @Override
    public void deleteLink(long id) {
        linkRepository.deleteById(id);
    }

    @Override
    public void deactivateLink(long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found with id: " + id));
        link.setActive(false);
        linkRepository.save(link);
    }

    @Override
    public void activateLink(long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Link not found with id: " + id));
        link.setActive(true);
        linkRepository.save(link);
    }

    @Override
    public String generateShortUrl() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortUrl = new StringBuilder();
        Random random = new Random();

        // Generate a 6-character short URL
        for (int i = 0; i < 6; i++) {
            shortUrl.append(chars.charAt(random.nextInt(chars.length())));
        }

        // Check if the generated short URL already exists
        if (linkRepository.findByUrlShort(shortUrl.toString()).isPresent()) {
            // Regenerate if it exists
            return generateShortUrl();
        }

        return shortUrl.toString();
    }
}
