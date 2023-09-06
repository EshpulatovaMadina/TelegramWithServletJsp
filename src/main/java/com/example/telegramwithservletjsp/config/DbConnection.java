package com.example.telegramwithservletjsp.config;

import lombok.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DbConnection {
    private  String url;
    private  String username;
    private  String password;
    public static Connection connection;

    public  Connection connection(){
        if (connection == null){
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        url,
                        username,
                        password
                );
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
