package com.example.telegramwithservletjsp.repository.chat;

import com.example.telegramwithservletjsp.model.Chat;
import com.example.telegramwithservletjsp.model.Chats;
import com.example.telegramwithservletjsp.repository.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends BaseRepository<Chat> {
    Chat getChat(UUID senderId, UUID receiverId);

    List<Chats> getAllChats(UUID userId);
}
