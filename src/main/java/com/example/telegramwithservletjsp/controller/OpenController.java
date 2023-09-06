package com.example.telegramwithservletjsp.controller;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.exception.DataNotFoundException;
import com.example.telegramwithservletjsp.model.Chat;
import com.example.telegramwithservletjsp.model.User;
import com.example.telegramwithservletjsp.service.ChatService;
import com.example.telegramwithservletjsp.service.MessageService;
import com.example.telegramwithservletjsp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@WebServlet(name = "chat", urlPatterns = "/open-chat")
public class OpenController extends HttpServlet {
    private ChatService chatService = BeanConfig.applicationContext.getBean("chatService", ChatService.class);
    private MessageService messageService = BeanConfig.applicationContext.getBean("messageService",MessageService.class);
    private UserService userService = BeanConfig.applicationContext.getBean("userService",UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String senderId = "";
        for ( Cookie c: cookies) {
            if (c.getName().equals("userId")) {
                senderId = c.getValue();
                break;
            }
        }

        String receiverId = req.getParameter("receiverId");

        User user = userService.getById(UUID.fromString(receiverId));
        req.setAttribute("receiver", user);
        req.setAttribute("userId", senderId);
        try {
            Chat chat = chatService.getChat(UUID.fromString(senderId), UUID.fromString(receiverId));
            req.setAttribute("messages", messageService.getChatMessages(chat.getId()));
            req.setAttribute("chat", chat);
        } catch (DataNotFoundException e) {
            req.setAttribute("messages", Collections.emptyList());
            req.setAttribute("chat", null);
        }

        req.getRequestDispatcher("openChat.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String senderId = "";
        for ( Cookie c: cookies) {
            if (c.getName().equals("userId")) {
                senderId = c.getValue();
                break;
            }
        }

        String receiverPhoneNum = req.getParameter("phoneNumber");

        User user = userService.getByPhoneNum(receiverPhoneNum);
        req.setAttribute("receiver", user);
        req.setAttribute("userId", senderId);
        try {
            Chat chat = chatService.getChat(UUID.fromString(senderId), user.getId());
            req.setAttribute("messages", messageService.getChatMessages(chat.getId()));
            req.setAttribute("chat", chat);
        } catch (DataNotFoundException e) {
            req.setAttribute("messages", Collections.emptyList());
            req.setAttribute("chat", null);
        }

        req.getRequestDispatcher("openChat.jsp")
                .forward(req, resp);
    }
}
