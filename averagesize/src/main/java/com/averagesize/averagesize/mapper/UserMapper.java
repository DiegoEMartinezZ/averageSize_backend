package com.averagesize.averagesize.mapper;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.averagesize.averagesize.dto.LinkResDTO;
import com.averagesize.averagesize.dto.UserReqDTO;
import com.averagesize.averagesize.dto.UserResDTO;
import com.averagesize.averagesize.dto.UserUpdateDTO;
import com.averagesize.averagesize.entity.Link;
import com.averagesize.averagesize.entity.User;

@Component
public class UserMapper {
    // Convert User to UserResDTO
    public User toEntity(UserReqDTO dto) {
        return User.builder().name(dto.getName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // In practice, you'd encrypt this
                .createAt(LocalDateTime.now())
                .active(true)
                .isBetaTester(dto.isBetaTester())
                .build();
    }

    // Update User from DTO
    public void updateEntityfromDto(UserUpdateDTO dto, User user) {
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        user.setBetaTester(dto.isBetaTester());

    }

    // convert User to UserResDTO
    public UserResDTO toDto(User user) {
        UserResDTO dto = UserResDTO.builder()
                .idUser(user.getIdUser())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createAt(user.getCreateAt())
                .active(user.isActive())
                .isBetaTester(user.isBetaTester())
                .build();
        if (user.getLinks() != null && !user.getLinks().isEmpty()) {
            // Include full link DTOs with all relevant information
            dto.setLinks(user.getLinks().stream()
                    .map(this::toLinkDto)
                    .collect(Collectors.toList()));
        }

        return dto;

    }

    // Helper method
    private LinkResDTO toLinkDto(Link link) {
        return LinkResDTO.builder()
                .idLink(link.getIdLink())
                .urlOriginal(link.getUrlOriginal())
                .urlShort(link.getUrlShort())
                .title(link.getTitle())
                .description(link.getDescription())
                .createdAt(link.getCreatedAt())
                .expirationDate(link.getExpirationDate())
                .active(link.isActive())
                .build();
    }
}