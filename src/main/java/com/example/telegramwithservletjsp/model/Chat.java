package com.example.telegramwithservletjsp.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Chat extends BaseModel{
    private UUID user1Id;
    private UUID user2Id;
}
