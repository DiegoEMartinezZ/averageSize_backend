package com.averagesize.averagesize.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {
    private String name;
    private String lastName;
    private String email;
    private String password;
    private boolean isBetaTester;
}
