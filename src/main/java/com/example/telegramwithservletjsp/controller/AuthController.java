package com.example.telegramwithservletjsp.controller;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.model.User;
import com.example.telegramwithservletjsp.service.ChatService;
import com.example.telegramwithservletjsp.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "auth", urlPatterns = {"/sign-up", "/sign-in"})
public class AuthController extends HttpServlet {

    private UserService userService = BeanConfig.applicationContext.getBean("userService",UserService.class);
    private ChatService chatService = BeanConfig.applicationContext.getBean("chatService", ChatService.class);
    public AuthController() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        System.out.println(requestURI);
        if(requestURI.equals("/sign-up")) {
            resp.sendRedirect("signUp.jsp");
        } else {
            resp.sendRedirect("signIn.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();

        switch (requestURI) {
            case "/sign-in" -> {
                String phoneNumber = req.getParameter("phoneNumber");
                String password = req.getParameter("password");
                User user = userService.signIn(phoneNumber, password);
                Cookie userId = new Cookie("userId", user.getId().toString());
                resp.addCookie(userId);
                req.setAttribute("user", user);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
                requestDispatcher.forward(req, resp);
            }
            case "/sign-up" -> {
                String phoneNumber = req.getParameter("phoneNumber");
                String password = req.getParameter("password");
                String username = req.getParameter("username");
                String name = req.getParameter("name");
                User user = new User(name, username, password, phoneNumber, null, null);
                userService.save(user);
                resp.sendRedirect("first.jsp");

            }
        }
    }
}
