package com.averagesize.averagesize.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResDTO {
    private UUID idUser;
    private String name;
    private String lastName;
    private String email;
    private LocalDateTime createAt;
    private boolean active;
    private boolean isBetaTester;
    private List<LinkResDTO> Links;
}
