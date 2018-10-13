package com.epam.totalizator.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Service class, that send email's {@linkplain Mailer#send(String, String, String)}
 * from defined email {@linkplain Mailer#userName}.
 * Hold email and password of this defined user and configuration.
 */
public class Mailer {
	
	private String userName;
	private String password;
	private Properties prop;
	
	public Mailer() {
		this.userName = "atotalizator@gmail.com";
		this.password = "Abcd-123";
		
		prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
	}
	
	public void send(String subject, String text, String toEmail){
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            //form 
            message.setFrom(new InternetAddress(userName));
            //to
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //theme
            message.setSubject(subject);
            //body
            message.setText(text);
            //send message
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
