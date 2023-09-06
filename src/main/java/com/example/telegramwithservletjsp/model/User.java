package com.example.telegramwithservletjsp.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseModel {
    private String name;
    private String username;
    private String password;
    private String phoneNumber;
    private String avatar;
    private String bio;
}
