package Main.JavaMail;

import Main.ClientProgram.Emails.Email;

public class MailComposer {
    static SendEmailTLS sendEmailTLS = new SendEmailTLS();

    private MailComposer() {
    }

    public static boolean sendEmail(Email email) {
        boolean isSent = sendEmailTLS.send(email.getRecipientEmail(), email.getSubject(), email.getContent());
        return isSent;
    }
}
