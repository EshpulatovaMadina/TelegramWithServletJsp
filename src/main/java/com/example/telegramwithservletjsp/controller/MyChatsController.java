package com.example.telegramwithservletjsp.controller;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.model.Chats;
import com.example.telegramwithservletjsp.service.ChatService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@WebServlet(name = "myChat",urlPatterns = "/my-chats")
public class MyChatsController extends HttpServlet {
    ChatService chatService = BeanConfig.applicationContext.getBean("chatService",ChatService.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> optionalCookie = Arrays.stream(cookies).filter(el -> el.getName().equals("userId")).findFirst();
        Cookie cookie = optionalCookie.get();
        String userId = cookie.getValue();

        List<Chats> chats = chatService.getAllChats(UUID.fromString(userId));
        req.setAttribute("chats", chats);
        req.getRequestDispatcher("/all-chats.jsp")
                .forward(req,resp);
    }
}
