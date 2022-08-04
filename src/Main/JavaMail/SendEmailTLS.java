package Main.JavaMail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailTLS {
    private final String username = "isurupramudith.20@cse.mrt.ac.lk";
    private final String password = "qwerzxcvasdf1234cse";
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
//        try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("from@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(recipientEmail)
        );
        message.setSubject(subject);
        message.setText(content);

        Transport.send(message);

//
//        } catch (MessagingException e) {
//            System.out.println("Email not Sent!");
//            e.printStackTrace();
//        }

    }
}