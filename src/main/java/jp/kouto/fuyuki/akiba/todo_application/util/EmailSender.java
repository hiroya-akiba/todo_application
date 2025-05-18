package jp.kouto.fuyuki.akiba.todo_application.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jp.kouto.fuyuki.akiba.todo_application.exceptions.EmailSenderException;

public class EmailSender {

    private final Session session;
    private final String from;

    public EmailSender() throws EmailSenderException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("mail.properties")) {
            if (input == null) throw new EmailSenderException("mail.properties not found");
            props.load(input);
        } catch(IOException e) {
        	throw new EmailSenderException("mail.properties error", e);
        }

        this.from = props.getProperty("mail.smtp.from");

        this.session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        props.getProperty("mail.smtp.user"),
                        props.getProperty("mail.smtp.password")
                );
            }
        });
    }

    public void sendMail(String to, String subject, String body) throws EmailSenderException {
    	try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
    	} catch(MessagingException e) {
    		throw new EmailSenderException("EmailSender.sendMail() error.", e);
    	}
    }
}