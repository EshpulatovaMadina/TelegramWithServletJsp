package com.example.telegramwithservletjsp.controller;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.exception.DataNotFoundException;
import com.example.telegramwithservletjsp.model.Chat;
import com.example.telegramwithservletjsp.model.Message;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@WebServlet(name = "write", urlPatterns = "/write")
public class WriteController extends HttpServlet {
    MessageService messageService = BeanConfig.applicationContext.getBean("messageService",MessageService.class);
    UserService userService = BeanConfig.applicationContext.getBean("userService",UserService.class);
    ChatService chatService = BeanConfig.applicationContext.getBean("chatService",ChatService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> optionalCookie = Arrays.stream(cookies).filter(el -> el.getName().equals("userId")).findFirst();
        Cookie cookie = optionalCookie.get();
        String userId = cookie.getValue();

        String messageId = req.getParameter("messageId");
        String chatId = req.getParameter("chatId");
        String receiverId = req.getParameter("receiverId");

        try{
            messageService.deleteMessage(UUID.fromString(chatId),UUID.fromString(messageId));
            Chat chat = chatService.getChat(UUID.fromString(userId), UUID.fromString(receiverId));

            User user = userService.getById(UUID.fromString(receiverId));
            req.setAttribute("receiver", user);
            req.setAttribute("userId", userId);

            req.setAttribute("messages", messageService.getChatMessages(chat.getId()));
            req.setAttribute("chat", chat);
        }catch (DataNotFoundException e){

            Chat chat1 = Chat.builder()
                    .user1Id(UUID.fromString(userId))
                    .user2Id(UUID.fromString(receiverId))
                    .build();
            chatService.save(chat1);
            chat1 = chatService.getChat(UUID.fromString(userId),UUID.fromString(receiverId));

            User user = userService.getById(UUID.fromString(receiverId));
            req.setAttribute("receiver", user);
            req.setAttribute("userId", userId);

            req.setAttribute("messages", Collections.emptyList());
            req.setAttribute("chat", chat1);
        }
        resp.sendRedirect(req.getContextPath()+"/open-chat?receiverId="+receiverId);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> optionalCookie = Arrays.stream(cookies).filter(el -> el.getName().equals("userId")).findFirst();
        Cookie cookie = optionalCookie.get();
        String userId = cookie.getValue();
        Object chatObject = req.getAttribute("chat");
        String text = req.getParameter("text");

        String receiverId = req.getParameter("receiverId");

        try {
            Chat chat = chatService.getChat(UUID.fromString(userId), UUID.fromString(receiverId));

            Message message = Message.builder()
                    .chatId(chat.getId())
                    .senderId(UUID.fromString(userId))
                    .text(text)
                    .build();
            messageService.save(message);

            User user = userService.getById(UUID.fromString(receiverId));
            req.setAttribute("receiver", user);
            req.setAttribute("userId", userId);

            req.setAttribute("messages", messageService.getChatMessages(chat.getId()));
            req.setAttribute("chat", chat);
        }catch (DataNotFoundException e ) {
            Chat chat1 = Chat.builder()
                    .user1Id(UUID.fromString(userId))
                    .user2Id(UUID.fromString(receiverId))
                    .build();
            chatService.save(chat1);
            chat1 = chatService.getChat(UUID.fromString(userId),UUID.fromString(receiverId));

            Message message = Message.builder()
                    .chatId(chat1.getId())
                    .senderId(UUID.fromString(userId))
                    .text(text)
                    .build();
            messageService.save(message);

            User user = userService.getById(UUID.fromString(receiverId));
            req.setAttribute("receiver", user);
            req.setAttribute("userId", userId);

            req.setAttribute("messages", messageService.getChatMessages(chat1.getId()));
            req.setAttribute("chat", chat1);
        }




//            User user = userService.getById(UUID.fromString(receiverId));
//            req.setAttribute("receiver", user);
//            req.setAttribute("userId", userId);
//
//                req.setAttribute("messages", messageService.getChatMessages(chat.getId()));
//                req.setAttribute("chat", chat);
//            } catch (DataNotFoundException e) {
//                req.setAttribute("messages", Collections.emptyList());
//                req.setAttribute("chat", null);
//            }
//

        resp.sendRedirect(req.getContextPath()+"/open-chat?receiverId="+receiverId);
//        req.getRequestDispatcher("openChat.jsp")
//                    .forward(req, resp);


    }
}
