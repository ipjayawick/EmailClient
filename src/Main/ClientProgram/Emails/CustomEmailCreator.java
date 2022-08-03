package Main.ClientProgram.Emails;

public class CustomEmailCreator extends EmailCreator {

    public Email createEmail(String recipientEmail, String subject, String content) {
        this.email = new Email(recipientEmail, subject, content);
        return email;
    }
}
