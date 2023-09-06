package com.example.telegramwithservletjsp.service;
import com.example.telegramwithservletjsp.model.Chats;
import com.example.telegramwithservletjsp.model.User;
import com.example.telegramwithservletjsp.repository.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository ;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String save(User user) {
     return userRepository.save(user);
    }

    public User signIn(String phoneNumber, String password) {
    return userRepository.signIn(phoneNumber, password);
    }

    public User getById(UUID id) {
        Optional<User> byId = userRepository.getById(id);
        return byId.get();
    }


    public List<Chats> findByPhoneNumber(String phoneNumber) {
      return userRepository.findByPhoneNum(phoneNumber);

    }

    public User getByPhoneNum(String phoneNumber) {
        return userRepository.getByPhoneNumber(phoneNumber);
    }

    public void update(UUID id, String avatar, String bio) {
        userRepository.updateAvatar(id,avatar,bio);
    }
}
