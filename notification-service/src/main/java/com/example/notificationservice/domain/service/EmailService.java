package com.example.notificationservice.domain.service;

import com.example.notificationservice.application.dto.NotificationDto;

public interface EmailService {
    void sendEmail(NotificationDto notificationDto);

}
