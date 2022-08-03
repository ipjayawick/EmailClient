package Main.ClientProgram;

import Main.ClientProgram.Emails.BirthdayEmailCreator;
import Main.ClientProgram.Emails.CustomEmailCreator;
import Main.ClientProgram.Emails.Email;
import Main.ClientProgram.Recipients.Recipient;
import Main.ClientProgram.Recipients.Wishable;
import Main.ClientProgram.Utilities.DateChecker;
import Main.ClientProgram.Utilities.MyObjectOutputStream;
import Main.ClientProgram.Utilities.RecipientCreator;
import Main.JavaMail.MailComposer;
import javafx.util.Pair;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

public class EmailClientProgram {
    private final ArrayList<Wishable> birthdayRecipients = new ArrayList<>();
    private ArrayList<Recipient> allRecipients = new ArrayList<>();
    private ArrayList<Wishable> wishableRecipients = new ArrayList<>();
    private final ArrayList<Email> emails = new ArrayList<>();

    public EmailClientProgram() throws IOException, ParseException, ClassNotFoundException {
        loadRecipientLists();
        loadBirthdayRecipients();
        sendBirthdayGreetings();
        loadEmails();
    }

    private void loadRecipientLists() {
        Pair<ArrayList<Recipient>, ArrayList<Wishable>> pair = RecipientCreator.initializeAndGetRecipientLists();
        allRecipients = pair.getKey();
        wishableRecipients = pair.getValue();
    }

    private void loadBirthdayRecipients() throws ParseException {
        for (Wishable wishable : wishableRecipients) {
            if (DateChecker.isToday(wishable.getBirthday())) {
                birthdayRecipients.add(wishable);
            }
        }
    }

    private void sendBirthdayGreetings() throws IOException {
        for (Wishable wishable : birthdayRecipients) {
            Email email = new BirthdayEmailCreator().createEmail(wishable);
            boolean isEmailSent = MailComposer.sendEmail(email);
            if (isEmailSent) {
                saveOnDisk(email);
                emails.add(email);
            }
        }
    }

    public void addRecipient(String userInput) throws IOException {
        String[] recipient = userInput.split(":|\\,");
        RecipientCreator.addRecipientToList(recipient);
        allRecipients = RecipientCreator.getAllRecipients();
        wishableRecipients = RecipientCreator.getWishableRecipients();
        saveOnDisk(userInput);
    }

    private void saveOnDisk(String recipient) throws IOException {
//        FileWriter writer = new FileWriter("src/Main/SavedFiles/clientList.txt",true);
//        BufferedWriter buffer = new BufferedWriter(writer);
//        buffer.write(recipient+"\n");
//        buffer.flush();
//        buffer.close();
        FileWriter writer = new FileWriter("src/Main/SavedFiles/clientList.txt", true);
        writer.write(recipient + "\n");
        writer.close();
    }

    public void sendEmail(String userInput) throws IOException {
        String[] emailData = userInput.split(",");
        String recipientEmail = emailData[0];
        String subject = emailData[1];
        String content = emailData[2];

        Email email = new CustomEmailCreator().createEmail(recipientEmail, subject, content);
        boolean isEmailSent = MailComposer.sendEmail(email);
        if (isEmailSent) {
            saveOnDisk(email);
            emails.add(email);
            System.out.println("Email Sent!");
        } else {
            System.out.println("Email Not Sent!");
        }
    }

    private void saveOnDisk(Email email) throws IOException {
//        FileOutputStream fileStream = new FileOutputStream("src/Main/SavedFiles/Emails.ser");
//        ObjectOutputStream os = new ObjectOutputStream(fileStream);
//        os.writeObject(email);
//        os.close();
        File f=new File("src/Main/SavedFiles/Emails.ser");
        try {
            FileOutputStream fos = new FileOutputStream("src/Main/SavedFiles/Emails.ser", true);
            if (f.length() == 0) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(email);
                oos.close();
            }
            else {
                MyObjectOutputStream oos= new MyObjectOutputStream(fos);
                oos.writeObject(email);
                oos.close();
            }
            fos.close();
        }
        catch (Exception e) {
            System.out.println("Error Occurred" + e);
        }
    }

    public void printRecipientCount() {
        System.out.println(allRecipients.size());
    }

    public void printEmailsSentOnDate(String inputDate) throws IOException, ClassNotFoundException, ParseException {
        for (Email email : emails) {
            if (DateChecker.isEqual(email.getSentDate(), inputDate)) {
                System.out.println(email.getEmailSummary());
            }
        }
    }

    private void loadEmails() throws IOException, ClassNotFoundException {
        try {
            FileInputStream fileStream = new FileInputStream("src/Main/SavedFiles/Emails.ser");
            ObjectInputStream os = new ObjectInputStream(fileStream);
            while (true) {
                try {
                    emails.add((Email)os.readObject());
                } catch (EOFException exc) {
                    os.close();
                    break;
                }
            }
        } catch (EOFException e) {
        }
    }

    public void printRecipientsWithBirthDate(String inputDate) throws ParseException {
        for (Wishable wishable : wishableRecipients) {
            if (DateChecker.isEqual(wishable.getBirthday(), inputDate)) {
                System.out.println(wishable.getName());
            }
        }
    }
}
