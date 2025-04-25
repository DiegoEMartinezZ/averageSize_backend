package com.averagesize.averagesize.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkReqDTO {
    private String urlOriginal;
    private String urlShort;
    private String title;
    private String description;
}
