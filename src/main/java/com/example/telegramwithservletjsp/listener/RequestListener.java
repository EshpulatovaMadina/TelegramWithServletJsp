package com.example.telegramwithservletjsp.listener;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

public class RequestListener implements ServletRequestListener {


    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        System.out.println("Servlet request initialized. Remote IP = " + servletRequest.getRemoteAddr());
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        System.out.println("Servlet request destroyed. Remote IP = " + servletRequest.getRemoteAddr());
    }
}
