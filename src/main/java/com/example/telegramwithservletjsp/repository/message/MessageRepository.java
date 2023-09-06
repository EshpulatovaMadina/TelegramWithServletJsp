package com.example.telegramwithservletjsp.repository.message;

import com.example.telegramwithservletjsp.model.Message;
import com.example.telegramwithservletjsp.repository.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends BaseRepository<Message> {
    List<Message> getAllById(UUID chatId);

    void delete(UUID chatId, UUID messageId);
}
