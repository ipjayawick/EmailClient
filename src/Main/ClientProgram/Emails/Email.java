package Main.ClientProgram.Emails;

import Main.ClientProgram.Utilities.DateChecker;

import java.io.Serializable;

public class Email implements Serializable {
    private final String subject;
    private final String content;
    private final String recipientEmail;
    private final String sentDate;


    public Email(String recipientEmail, String subject, String content) {
        this.subject = subject;
        this.content = content;
        this.recipientEmail = recipientEmail;
        this.sentDate = DateChecker.getCurrentDate();
    }


    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getSentDate() {
        return sentDate;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getEmailSummary() {
        return ("Recipient:\t" + recipientEmail +
                "\nSubject:\t" + subject +
                "\nMessage:\t" + content + "\n");
    }
}
