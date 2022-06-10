package com.ogc.pharmagcode.Utils;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.util.Properties;


import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class MailUtils {
    // Manda OTP
    // Manda Nuova Password
    public static void inviaMail(String msg, String dest, String subject) {

        // create a client
        HttpClient client = HttpClient.newHttpClient();

        dest = URLEncoder.encode(dest, StandardCharsets.UTF_8);
        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        subject = URLEncoder.encode(subject, StandardCharsets.UTF_8);
        String base_url = "https://script.google.com/macros/s/AKfycbxhsYULGupk_IuMEKca9aYKFB7pbhQS2yBa0X3YTv8VAaOW9piYxynh8s7W_NlF2LZhYw/exec";

        var request = HttpRequest.newBuilder(URI.create(base_url + "?to=" + dest + "&subject=" + subject + "&body=" + msg))
                .header("accept", "application/json")
                .GET()
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            // se fallisce sticazzi..
            System.err.println("Qualcosa è andato storto con la mail");
        }
    }
}