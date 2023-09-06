package com.example.telegramwithservletjsp.model;

import lombok.ToString;

import java.util.UUID;


public record Chats(String name, String phoneNumber, UUID receiverId,String avatar) {
}
