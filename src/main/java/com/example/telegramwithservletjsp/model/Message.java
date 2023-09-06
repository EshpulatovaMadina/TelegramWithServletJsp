package com.example.telegramwithservletjsp.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Message extends BaseModel{
    private UUID senderId;
    private UUID chatId;
    private String text;
}
