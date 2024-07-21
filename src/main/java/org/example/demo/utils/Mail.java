package org.example.demo.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    public static void sendEmail(String to, String subject, String body) {
        final String username = "apikey";
        final String password = "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";
        final String verifiedEmail = "yanndjoumessi@gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.starttls.enable", "true");
        //Use smtp.googlemail.com as mail server
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(verifiedEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // main method to test the program
    public static void main(String[] args) {
        sendEmail("ydjoumes@students.kennesaw.edu", "Test API", "Java API bro");
    }
}
