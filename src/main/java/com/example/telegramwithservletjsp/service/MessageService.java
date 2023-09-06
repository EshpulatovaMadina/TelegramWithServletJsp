package com.example.telegramwithservletjsp.service;


import com.example.telegramwithservletjsp.exception.ValidationException;
import com.example.telegramwithservletjsp.model.Message;
import com.example.telegramwithservletjsp.repository.message.MessageRepository;

import java.util.List;
import java.util.UUID;

public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public String save(Message message) {
      return messageRepository.save(message);
    }



    public List<Message> getChatMessages(UUID chatId) {
        return messageRepository.getAllById(chatId);
    }

    private void validateMessage(Message message) {
        if(message.getText().length() == 0) {
            throw new ValidationException("message text cannot be empty");
        }
    }

    public void deleteMessage(UUID chatId, UUID messageId) {
        messageRepository.delete(chatId, messageId);
    }
}
