package Main.JavaMail;

import Main.ClientProgram.Emails.Email;

import javax.mail.MessagingException;

public class MailComposer {
    private static final SendEmailTLS sendEmailTLS = new SendEmailTLS();

    private MailComposer() {
    }

    public static void sendEmail(Email email) throws MessagingException {
        sendEmailTLS.send(email.getRecipientEmail(), email.getSubject(), email.getContent());
    }
}
