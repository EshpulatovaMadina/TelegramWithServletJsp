package com.example.telegramwithservletjsp.repository.user;

import com.example.telegramwithservletjsp.config.DbConnection;
import com.example.telegramwithservletjsp.exception.AuthException;
import com.example.telegramwithservletjsp.exception.DataNotFoundException;
import com.example.telegramwithservletjsp.model.Chats;
import com.example.telegramwithservletjsp.model.User;
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
public class UserRepositoryImpl implements UserRepository {
    private Connection connection;

    @Override
    public String save(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from add_user(?,?,?,?,?,?)");
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getUsername());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getPhoneNumber());
            preparedStatement.setString(5,user.getAvatar());
            preparedStatement.setString(6,user.getBio());
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            return  resultSet.getString("add_user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> getById(UUID id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from get_by_id(?);");
            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                User user = User.builder()
                        .name(resultSet.getString("name"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .username(resultSet.getString("username"))
                        .avatar(resultSet.getString("avatar"))
                        .bio(resultSet.getString("bio"))
                        .build();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                return Optional.of(user);

            }
            throw new DataNotFoundException("user with this id not found");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public int deleteById(UUID id) {
        return 0;
    }

    @Override
    public int update(User update) {
        return 0;
    }

    @Override
    public User signIn(String phoneNumber, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from sign_in(?,?)");
            preparedStatement.setString(1,phoneNumber);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                User user = User.builder()
                        .name(resultSet.getString("name"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .username(resultSet.getString("username"))
                        .build();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
                return user;

            }
            throw new AuthException("wrong phone number or password");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Chats> findByPhoneNum(String phoneNum) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from find_by_phone_number(?);");
            preparedStatement.setString(1,phoneNum);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Chats> chats = new ArrayList<>();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String phoneNumber = resultSet.getString("phone_number");
                String avatar = resultSet.getString("avatar");
                String userId = resultSet.getString("receiver_id");
                Chats chat = new Chats(name, phoneNumber, UUID.fromString(userId),avatar);
                chats.add(chat);
            }

//            System.out.println("chats = " + chats.toString());

            if(chats.isEmpty()){
                throw new DataNotFoundException("user with this id not found");

            }
            return chats;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from get_by_phone_number(?)");
            preparedStatement.setString(1,phoneNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;
            if(resultSet.next()){
                 user = User.builder()
                        .name(resultSet.getString("name"))
                        .phoneNumber(resultSet.getString("phone_number"))
                        .username(resultSet.getString("username"))
                         .avatar(resultSet.getString("avatar"))
                         .bio(resultSet.getString("bio"))
                        .build();
                user.setId(UUID.fromString(resultSet.getString("id")));
                user.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAvatar(UUID id, String avatar, String bio) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set avatar = ?,bio = ? where id = ? :: uuid");
            preparedStatement.setString(1,avatar);
            preparedStatement.setString(2,bio);
            preparedStatement.setString(3, String.valueOf(id));

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
