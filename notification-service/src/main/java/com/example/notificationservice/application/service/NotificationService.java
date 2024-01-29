package com.example.notificationservice.application.service;

import com.example.notificationservice.application.dto.NotificationDto;
import com.example.notificationservice.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private EmailService notificationService;

    public void processNotification(NotificationDto notificationDto) {
        notificationService.sendEmail(notificationDto);
    }
}
