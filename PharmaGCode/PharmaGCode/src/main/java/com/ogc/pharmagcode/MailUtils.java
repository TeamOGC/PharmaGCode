package com.ogc.pharmagcode;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class MailUtils {
    // Manda OTP
    // Manda Nuova Password
    public static void inviaMail(String msg, String dest){
        int port=25;

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session=Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("pharmaogc@gmail.com","OggiCarbonara22");
            }
        });
//        Session session=Session.getDefaultInstance(prop);


        Message message=new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("pharmaogc@gmail.com","Never Gonna Give You Up"));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(dest));
            message.setSubject("OTP");


            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
