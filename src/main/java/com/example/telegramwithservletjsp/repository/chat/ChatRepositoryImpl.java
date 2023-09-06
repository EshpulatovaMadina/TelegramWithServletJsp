package com.example.telegramwithservletjsp.repository.chat;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.config.DbConnection;
import com.example.telegramwithservletjsp.exception.DataNotFoundException;
import com.example.telegramwithservletjsp.model.Chat;
import com.example.telegramwithservletjsp.model.Chats;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRepositoryImpl implements ChatRepository{
    private Connection connection;

    @Override
    public String save(Chat chat) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from add_chat(?,?)");
            preparedStatement.setString(1, String.valueOf(chat.getUser1Id()));
            preparedStatement.setString(2, String.valueOf(chat.getUser2Id()));
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            String addChat = resultSet.getString("add_chat");
            System.out.println("addChat = " + addChat);
            return addChat;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Chat> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Chat> getAll() {
        return null;
    }

    @Override
    public int deleteById(UUID id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("select * from delete_chat(?);");
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.execute();
            return 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int update(Chat update) {
        return 0;
    }

    @Override
    public Chat getChat(UUID senderId, UUID receiverId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from get_chat(?,?);");
            preparedStatement.setString(1, String.valueOf(senderId));
            preparedStatement.setString(2, String.valueOf(receiverId));
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Chat chat = Chat.builder()
                        .user1Id(UUID.fromString(resultSet.getString("user1")))
                        .user2Id(UUID.fromString(resultSet.getString("user2")))
                        .build();
                chat.setId(UUID.fromString(resultSet.getString("id")));
                return chat;
            }
            throw new DataNotFoundException("Chat not found");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Chats> getAllChats(UUID userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from get_all_chats(?);");
            preparedStatement.setString(1, String.valueOf(userId));
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Chats> chats = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phone_number");
                String avatar = resultSet.getString("avatar");
                Chats chat = new Chats(name, phoneNumber, userId,avatar);
                chats.add(chat);
            }

//            System.out.println("chats = " + chats.toString());
            return chats;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
