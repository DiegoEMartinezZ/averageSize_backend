package com.averagesize.averagesize.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClickResDTO {
    private Long idClick;
    private String linkShortUrl;
    private long linkId;
    private String ipUser;
    private String navigator;
    private String device;
    private String location;
    private LocalDateTime clickAt;
}
