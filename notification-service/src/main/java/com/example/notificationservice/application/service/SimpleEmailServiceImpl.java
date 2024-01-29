package com.example.notificationservice.application.service;

import com.example.notificationservice.application.dto.NotificationDto;
import com.example.notificationservice.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class SimpleEmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;


    @Override
    public void sendEmail(NotificationDto notificationDto) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setTo(notificationDto.getEmail());
            helper.setSubject("Confirm account!");

            String confirmationLink = "http://localhost:8081/user/auth/confirm/" + notificationDto.getOtp();  //bu URL user-ms terfinden diqqet edilmelidir, Kenan sene aiddir :D
            String emailContent = "<html><body><p>Please click the following link to confirm your account:</p>"
                    + "<a href=\"" + confirmationLink + "\">Confirm Account</a>"
                    + "</body></html>";

            helper.setText(emailContent, true);

            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Email could not be sent");
        }
    }
}
