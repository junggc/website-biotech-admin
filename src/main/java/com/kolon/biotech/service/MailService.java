package com.kolon.biotech.service;

import com.kolon.biotech.web.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {

    private JavaMailSender mailSender;

    public void mailSend(MailDto mailDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("iopqwas@naver.com");
        message.setTo("iopqwas@naver.com");
        message.setSubject("제목입니다.");
        message.setText("내용입니다.");

        mailSender.send(message);
    }
}
