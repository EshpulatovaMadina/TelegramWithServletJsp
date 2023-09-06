package com.example.telegramwithservletjsp.controller;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.service.ChatService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(name = "delete", urlPatterns = "/delete-chat")
public class DeleteChatController extends HttpServlet {
    ChatService chatService = BeanConfig.applicationContext.getBean("chatService" , ChatService.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chatId = req.getParameter("chatId");
        chatService.deleteChat(UUID.fromString(chatId));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        writer.write("<h1> Chat deleted </h1>"+
                "<br/><a href= \"/home.jsp\">menu</a>");
    }
}
