package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final String verificationEndPoint = "http://127.0.0.1:8083/api/v1/user/verification/";

    @Autowired
    private JavaMailSender emailSender;

    private String createVerificationLink(String userId, String verificationCode) {
        return verificationEndPoint + userId + "/" + verificationCode;
    }

    private String getVerificationBody(String verificationLink) {
        String content = "Welcome to Spacify!\n\n"
                + "Please click the link below to verify your registration:\n"
                + verificationLink
                + "\n\nThank you,\n"
                + "Team Spacify";
        return content;
    }

    private String getNewPasswordBody(String password, String name) {
        String content = "Hello " + name + ",\n\n"
                + "Here is the new password for your Spacify account: " + password
                + "\nPlease remember to change your password once you have signed in. You can change your password in the User Profile section."
                + "\n\nThank you,\n"
                + "Team Spacify";
        return content;
    }

    public void sendSimpleMessageForVerification(String to, String userId, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Please verify your registration");
        message.setText(getVerificationBody(createVerificationLink(userId, verificationCode)));
        this.emailSender.send(message);
    }

    public void sendNewPassword(String to, String name, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your new account password!");
        message.setText(getNewPasswordBody(password, name));
        this.emailSender.send(message);
    }
}