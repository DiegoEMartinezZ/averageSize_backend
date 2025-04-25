package com.averagesize.averagesize.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClickReqDTO {
    private long linkId;
    private String ipUser;
    private String navigator;
    private String device;
    private String location;
}
