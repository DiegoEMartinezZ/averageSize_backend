package com.averagesize.averagesize.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.averagesize.averagesize.dto.LinkReqDTO;
import com.averagesize.averagesize.dto.LinkResDTO;
import com.averagesize.averagesize.dto.LinkUpdateDTO;
import com.averagesize.averagesize.entity.Link;
import com.averagesize.averagesize.entity.User;
import com.averagesize.averagesize.exceptions.ResourceNotFoundException;
import com.averagesize.averagesize.mapper.LinkMapper;
import com.averagesize.averagesize.repository.LinkRepository;
import com.averagesize.averagesize.repository.UserRepository;

@Service
public class LinkServiceImpl implements LinkService {
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final LinkMapper linkMapper;

    public LinkServiceImpl(LinkRepository linkRepository, UserRepository userRepository, LinkMapper linkMapper) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.linkMapper = linkMapper;
    }

    @Override
    @Transactional
    public LinkResDTO createLink(LinkReqDTO linkDTO, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        String shortUrl = generateShortUrl();
        Link link = linkMapper.toEntity(linkDTO, user, shortUrl);
        link.setCreatedAt(LocalDateTime.now());
        link.setActive(true);

        Link savedLink = linkRepository.save(link);
        return linkMapper.toDto(savedLink);
    }

    @Override
    @Transactional(readOnly = true)
    public LinkResDTO getLinkById(long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found with id: " + id));
        return linkMapper.toDto(link);
    }

    @Override
    @Transactional(readOnly = true)
    public LinkResDTO getLinkByShortUrl(String shortUrl) {
        Link link = linkRepository.findByUrlShort(shortUrl)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found with short URL: " + shortUrl));
        return linkMapper.toDto(link);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkResDTO> getAllLinksByUserId(UUID userId) {
        List<Link> links = linkRepository.findByUserIdUser(userId);
        return links.stream()
                .map(linkMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LinkResDTO> getAllActiveLinks() {
        List<Link> links = linkRepository.findByActiveTrue();
        return links.stream()
                .map(linkMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LinkResDTO updateLink(long linkId, LinkUpdateDTO linkDTO) {
        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found with id: " + linkId));

        linkMapper.updateEntityFromDto(linkDTO, link);
        Link updatedLink = linkRepository.save(link);
        return linkMapper.toDto(updatedLink);
    }

    @Override
    @Transactional
    public void deleteLink(long id) {
        if (!linkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Link not found with id: " + id);
        }
        linkRepository.deleteById(id);
    }

    @Override
    @Transactional
    public LinkResDTO deactivateLink(long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found with id: " + id));
        link.setActive(false);
        Link updatedLink = linkRepository.save(link);
        return linkMapper.toDto(updatedLink);
    }

    @Override
    @Transactional
    public LinkResDTO activateLink(long id) {
        Link link = linkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Link not found with id: " + id));
        link.setActive(true);
        Link updatedLink = linkRepository.save(link);
        return linkMapper.toDto(updatedLink);
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
