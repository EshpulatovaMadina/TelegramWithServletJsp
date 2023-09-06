package com.example.telegramwithservletjsp.repository.user;

import com.example.telegramwithservletjsp.model.Chat;
import com.example.telegramwithservletjsp.model.Chats;
import com.example.telegramwithservletjsp.model.User;
import com.example.telegramwithservletjsp.repository.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User> {
    User signIn(String phoneNumber, String password);

    List<Chats> findByPhoneNum(String phoneNumber);

    User getByPhoneNumber(String name);

    void updateAvatar(UUID id, String avatar, String bio);

}
