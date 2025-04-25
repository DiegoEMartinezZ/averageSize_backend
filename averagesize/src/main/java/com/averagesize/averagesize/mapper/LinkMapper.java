package com.averagesize.averagesize.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.averagesize.averagesize.dto.LinkReqDTO;
import com.averagesize.averagesize.dto.LinkResDTO;
import com.averagesize.averagesize.dto.LinkUpdateDTO;
import com.averagesize.averagesize.entity.Link;
import com.averagesize.averagesize.entity.User;

@Component
public class LinkMapper {
    // Convert Link to LinkResDTO
    public Link toEntity(LinkReqDTO dto, User user, String generatedShortUrl) {
        return Link.builder().urlOriginal(dto.getUrlOriginal())
                .urlShort(generatedShortUrl)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                // .expirationDate(dto.getExpirationDate())
                .active(true)
                .user(user)
                .build();
    }

    // Update Link from DTO
    public void updateEntityFromDto(LinkUpdateDTO dto, Link link) {
        if (dto.getTitle() != null) {
            link.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            link.setDescription(dto.getDescription());
        }
        if (dto.getActive() != null) {
            link.setActive(dto.getActive());
        }
        if (dto.getUrlShort() != null) {
            link.setUrlShort(dto.getUrlShort());
        }
        if (dto.getUrlOriginal() != null) {
            link.setUrlOriginal(dto.getUrlOriginal());
        }
    }

    // Convert Link to LinkResDTO
    public LinkResDTO toDto(Link link) {
        return LinkResDTO.builder()
                .idLink(link.getIdLink())
                .urlOriginal(link.getUrlOriginal())
                .urlShort(link.getUrlShort())
                .title(link.getTitle())
                .description(link.getDescription())
                .createdAt(link.getCreatedAt())
                .expirationDate(link.getExpirationDate())
                .active(link.isActive())
                .userId(link.getUser().getIdUser().toString())
                .build();
    }
}
