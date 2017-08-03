package com.partner.campaign.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.partner.campaign")
@EnableScheduling
public class Application {
    public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
