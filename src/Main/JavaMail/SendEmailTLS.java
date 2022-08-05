package Main.JavaMail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailTLS {
    /** Send an email via Gmail SMTP server with TLS*/
    private final String username = "isurupramudith.20@cse.mrt.ac.lk";//My email
    private final String password = "cuhdilhfhzgocxzd";//Application-specific password generated using Google App password
    private Session session;

    SendEmailTLS() {
        initialize();
    }

    private void initialize() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    void send(String recipientEmail, String subject, String content) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail)
        );
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);
    }
}