package com.example.telegramwithservletjsp.repository.message;

import com.example.telegramwithservletjsp.config.DbConnection;
import com.example.telegramwithservletjsp.exception.DataNotFoundException;
import com.example.telegramwithservletjsp.model.Message;
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
@Setter
@Getter
public class MessageRepositoryImpl implements MessageRepository{

    private Connection connection;
    @Override
    public String save(Message message) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from add_message(?,?,?)");
            preparedStatement.setString(1, String.valueOf(message.getSenderId()));
            preparedStatement.setString(2, String.valueOf(message.getChatId()));
            preparedStatement.setString(3,message.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return resultSet.getString("add_message");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public int deleteById(UUID id) {
        return 0;
    }

    @Override
    public int update(Message update) {
        return 0;
    }

    @Override
    public List<Message> getAllById(UUID chatId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from get_messages_by_chat_id(?);");
            preparedStatement.setString(1, String.valueOf(chatId));
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Message> messages = new ArrayList<>();

            while (resultSet.next()){
                Message message = Message.builder()
                        .text(resultSet.getString("text"))
                        .senderId(UUID.fromString(resultSet.getString("sender_id")))
                        .chatId(UUID.fromString(resultSet.getString("chat_id")))
                        .build();
                message.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                message.setId(UUID.fromString(resultSet.getString("id")));
                messages.add(message);
            }
            if(messages.isEmpty()){
                throw new DataNotFoundException("Messages not found");
            }
            System.out.println("messages = " + messages.toString());
            return messages;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID chatId, UUID messageId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from delete_message(?,?);");
            preparedStatement.setString(1, String.valueOf(chatId));
            preparedStatement.setString(2, String.valueOf(messageId));
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            System.out.println("resultSet.getString(\"delete_message\") = " + resultSet.getString("delete_message"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
