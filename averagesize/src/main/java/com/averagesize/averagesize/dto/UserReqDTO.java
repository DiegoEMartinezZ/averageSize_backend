package com.averagesize.averagesize.dto;

import lombok.AllArgsConstructor;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReqDTO {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private boolean isBetaTester;
}
