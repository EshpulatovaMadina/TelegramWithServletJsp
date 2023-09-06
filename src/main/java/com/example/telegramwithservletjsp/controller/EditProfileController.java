package com.example.telegramwithservletjsp.controller;

import com.example.telegramwithservletjsp.config.BeanConfig;
import com.example.telegramwithservletjsp.model.User;
import com.example.telegramwithservletjsp.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@MultipartConfig
        (
        maxFileSize = 1024*1024*10, // 10Mb
        fileSizeThreshold = 1024*20, // 20Kb (kilo bytes)
        maxRequestSize = 1024*1024*20 // 20Mb
)
@WebServlet(name = "Edit", urlPatterns = "/edit-profile")
public class EditProfileController extends HttpServlet {
    UserService userService = BeanConfig.applicationContext.getBean("userService",UserService.class);
    String UPLOAD_DIR = "D:\\Telegram\\TelegramWithServletJsp\\TelegramWithServletJsp\\src\\main\\webapp\\public";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/edit-profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Optional<Cookie> optionalCookie = Arrays.stream(cookies).filter(el -> el.getName().equals("userId")).findFirst();
        Cookie cookie = optionalCookie.get();
        String userId = cookie.getValue();

        User user = userService.getById(UUID.fromString(userId));

        File fileSaveDir = new File(UPLOAD_DIR);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());

        String fileName = null;
        String saveFileName = null;
        Part bioPart = null;

        // Get all the parts from request and write the file on the server
        for (Part part : req.getParts()) {
            String name = part.getName();

            if (name.equals("img")) {
                fileName = getFileName(part);
                int index = fileName.lastIndexOf(".");
                saveFileName = userId + fileName.substring(index);
                part.write(UPLOAD_DIR + File.separator + saveFileName);
            } else if (name.equals("bio")) {
                // Get the bio value from the part
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8))) {
                    bioPart = part;
                }
            }
        }

        String bio = null;
        if (bioPart != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(bioPart.getInputStream(), StandardCharsets.UTF_8))) {
                bio = reader.readLine();
            }
        }
        userService.update(user.getId(), "public/" + saveFileName, bio);
        resp.sendRedirect("/home.jsp");
    }
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        System.out.println("content-disposition header= "+contentDisp);
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length()-1);
            }
        }
        return "";
    }
}
