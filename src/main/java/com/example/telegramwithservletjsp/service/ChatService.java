package com.example.telegramwithservletjsp.service;

import com.example.telegramwithservletjsp.model.Chat;
import com.example.telegramwithservletjsp.model.Chats;
import com.example.telegramwithservletjsp.repository.chat.ChatRepository;

import java.util.List;
import java.util.UUID;

public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public String save(Chat chat) {
      return chatRepository.save(chat);
    }



    public Chat getChat(UUID senderId, UUID receiverId) {

        return chatRepository.getChat(senderId, receiverId);
    }

    public List<Chats> getAllChats(UUID userId) {
        return chatRepository.getAllChats(userId);
    }

    public void deleteChat(UUID chatId) {
        chatRepository.deleteById(chatId);
    }

//    public
}
