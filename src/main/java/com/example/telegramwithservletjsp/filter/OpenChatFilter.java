package com.example.telegramwithservletjsp.filter;

import com.example.telegramwithservletjsp.exception.AuthException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class OpenChatFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            Optional<Cookie> optionalCookie = Arrays.stream(cookies).filter(el -> el.getName().equals("userId")).findFirst();
            Cookie cookie = optionalCookie.get();
            if(cookie.getName().equals("userId")){
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }
        throw  new AuthException("unauthorized request");
    }
}
