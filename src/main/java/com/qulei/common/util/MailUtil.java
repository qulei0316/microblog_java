package com.qulei.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender mailSender;

    //发送验证码邮件
    public void  setMailhtml(String to,String code){
        MimeMessage message = null;
        try{
            message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(sender);
            helper.setTo(to);
            helper.setSubject("验证码");

            StringBuffer sb = new StringBuffer();
            sb.append("【mcBlog】您的激活码是："+code+",有效时间为三分钟");
            helper.setText(sb.toString(),true);
        }catch (Exception e){
            e.printStackTrace();
        }
        mailSender.send(message);
    }
}
