package com.example.telegramwithservletjsp.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanConfig {
    public static ApplicationContext applicationContext= new ClassPathXmlApplicationContext("beans.xml");
}
