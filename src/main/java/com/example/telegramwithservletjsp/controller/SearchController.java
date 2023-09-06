package com.example.telegramwithservletjsp.controller;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.exception.DataNotFoundException;
import com.example.telegramwithservletjsp.model.Chat;
import com.example.telegramwithservletjsp.model.Chats;
import com.example.telegramwithservletjsp.model.User;
import com.example.telegramwithservletjsp.service.ChatService;
import com.example.telegramwithservletjsp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "search", urlPatterns = "/search")
public class SearchController extends HttpServlet {
    private ChatService chatService = BeanConfig.applicationContext.getBean("chatService",ChatService.class);
    private UserService userService = BeanConfig.applicationContext.getBean("userService",UserService.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneNumber = req.getParameter("phoneNumber");
        try {
            List<Chats> chats = userService.findByPhoneNumber(phoneNumber);

            System.out.println("chats = " + chats.toString());

            req.setAttribute("chats", chats);
            req.getRequestDispatcher("search.jsp")
                    .forward(req, resp);
        } catch (DataNotFoundException e) {
            req.setAttribute("message", "user not found");
            req.getRequestDispatcher("search.jsp")
                    .forward(req, resp);
        }
    }
}
