package com.kolon.biotech.util;

import com.kolon.biotech.web.dto.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Slf4j
@Component
//@RequiredArgsConstructor
public class MailUtil {

    @Autowired
    private MailContentBuilder mailContentBuilder;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMailInFile(MailDto mailDto) throws MessagingException, UnsupportedEncodingException{
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper msgHp = new MimeMessageHelper(msg, true, "UTF-8");

        String[]  toMail = null;
        if(mailDto.getToAddress().contains(",")){
            toMail = mailDto.getToAddress().split(",");
        }else{
            toMail = new String[1];
            toMail[0] = mailDto.getToAddress();
        }

        InternetAddress[] address = new InternetAddress[toMail.length];

        int pint = 0;
        for(String i : toMail) {
            address[pint] = new InternetAddress(i);
            pint++;
        }

        msg.setFrom(new InternetAddress(mailDto.getFromAddress()));
        msg.setSentDate(new Date());
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setHeader("Content-Type", "text/plan; charset=UTF-8");
        msg.setSubject(mailDto.getSubject(),"UTF-8");

        String content = mailContentBuilder.build(mailDto);

        File f = null;
        if(mailDto.getFile() != null){
            f = mailDto.getFile();

            if(f != null && f.isFile() && f.length() > 0){
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(content, "text/html; charset=utf-8");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                messageBodyPart = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(f);
                messageBodyPart.setDataHandler(new DataHandler(fds));
                messageBodyPart.setFileName(fds.getName());
                multipart.addBodyPart(messageBodyPart);

                msg.setContent(multipart);
            }
        }else{
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
        }

        javaMailSender.send(msg);

        if(f!=null && f.isFile()) f.delete();


    }

}
