package com.averagesize.averagesize.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkUpdateDTO {
    private String urlOriginal;
    private String urlShort;
    private String title;
    private String description;
    private LocalDateTime expirationDate;
    private Boolean active;
}
