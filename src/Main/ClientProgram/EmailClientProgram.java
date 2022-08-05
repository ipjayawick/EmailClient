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

import javax.mail.MessagingException;
import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class EmailClientProgram {
    private final ArrayList<Email> emails = new ArrayList<>();
    private ArrayList<Recipient> allRecipients = new ArrayList<>();
    private ArrayList<Wishable> wishableRecipients = new ArrayList<>();
    private static String lastAccess=null;

    public EmailClientProgram() throws IOException, ParseException, ClassNotFoundException, MessagingException {
        System.out.println("Email Client Program is Starting...");
        new File("src/Main/SavedFiles/log.txt").createNewFile();
        new File("src/Main/SavedFiles/Emails.ser").createNewFile();
        new File("src/Main/SavedFiles/clientList.txt").createNewFile();
        refreshLog();
        loadRecipientLists();
        sendBirthdayGreetings();
        loadEmails();
    }

    private void refreshLog() throws IOException {
        File file=new File("src/Main/SavedFiles/log.txt");
        if(file.length()==0){
            lastAccess=null;
        }else {
            String date = new BufferedReader(new FileReader(file)).readLine();
            lastAccess=date;
        }
        FileWriter writer = new FileWriter("src/Main/SavedFiles/log.txt");
        writer.write(DateChecker.getCurrentDate());
        writer.close();
    }

    private void loadRecipientLists() throws IOException {
        Pair<ArrayList<Recipient>, ArrayList<Wishable>> pair = RecipientCreator.initializeAndGetRecipientLists();
        allRecipients = pair.getKey();
        wishableRecipients = pair.getValue();
    }

    private void sendBirthdayGreetings() throws IOException, MessagingException, ParseException {
        if(lastAccess!=null&&lastAccess.equals(DateChecker.getCurrentDate())){
            return;
        }
        System.out.println("Sending Birthday wishes...");
        for (Wishable wishable : wishableRecipients) {
            if (DateChecker.isTodayBirthday(wishable.getBirthday())) {
                Email email = new BirthdayEmailCreator().createEmail(wishable);
                MailComposer.sendEmail(email);
                saveOnDisk(email);
            }
        }
    }

    private void sendBirthdayGreetings(Wishable wishable) {
        System.out.println("Sending birthday wishes to newly added recipient!");
        Email email = new BirthdayEmailCreator().createEmail(wishable);
        try {
            MailComposer.sendEmail(email);
            saveOnDisk(email);
        } catch (MessagingException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addRecipient(String userInput) {
        String[] recipient = userInput.replaceAll("\\s+", "").split(":|,");
        Recipient recipientObj = RecipientCreator.createRecipient(recipient);
        if (recipientObj==null) {
            System.out.println("Error: Recipient not Added!");
            return;
        }
        try {
            saveOnDisk(userInput);
            System.out.println("Recipient added Successfully!");
        } catch (IOException e) {
            System.out.println("Error: Recipient Not Saved on disk!");
            System.out.println(e.getMessage());
        }

        if (recipientObj instanceof Wishable){
            Wishable wishableRecipient=(Wishable) recipientObj;
            try {
                if (DateChecker.isTodayBirthday(wishableRecipient.getBirthday())){
                    sendBirthdayGreetings(wishableRecipient);
                }
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void saveOnDisk(String recipient) throws IOException {
        FileWriter writer = new FileWriter("src/Main/SavedFiles/clientList.txt", true);
        writer.write(recipient + "\n");
        writer.close();
    }

    public void sendEmail(String userInput) {
        String[] emailData = userInput.split(",");
        String recipientEmail;
        String subject;
        String content;
        try {
            recipientEmail = emailData[0];
            subject = emailData[1];
            content = emailData[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid Email Format!");
            return;
        }

        Email email = new CustomEmailCreator().createEmail(recipientEmail, subject, content);
        try {
            MailComposer.sendEmail(email);
            System.out.println("Email Sent Successfully!");
            emails.add(email);
            try {
                saveOnDisk(email);
            } catch (IOException e) {
                System.out.println("Error: Email not saved to Disk!");
                System.out.println(e.getMessage());
            }
        } catch (MessagingException e) {
            System.out.println("Error: Email Not Sent!");
            System.out.println(e.getMessage());
        }
    }

    private void saveOnDisk(Email email) throws IOException {
        File f = new File("src/Main/SavedFiles/Emails.ser");
        FileOutputStream fos = new FileOutputStream("src/Main/SavedFiles/Emails.ser", true);
        if (f.length() == 0) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(email);
            oos.close();
        } else {
            MyObjectOutputStream oos = new MyObjectOutputStream(fos);
            oos.writeObject(email);
            oos.close();
        }
        fos.close();
    }

    public void printRecipientCount() {
        System.out.println("Number of Recipients : " + Recipient.RecipientCount);
    }

    public void printEmailsSentOnDate(String inputDate) {
        boolean isPresent = false;
        for (Email email : emails) {
            try {
                if (DateChecker.isEqual(email.getSentDate(), inputDate)) {
                    System.out.println(email.getEmailSummary());
                    isPresent = true;
                }
            } catch (ParseException e) {
                System.out.println("Error:");
                System.out.println(e.getMessage());
                return;
            }
        }
        if (!isPresent) System.out.println("No Emails on given date");
    }

    private void loadEmails() throws IOException, ClassNotFoundException {
        File file = new File("src/Main/SavedFiles/Emails.ser");
        if (file.length() == 0) return;
        FileInputStream fileStream = new FileInputStream(file);
        ObjectInputStream os = new ObjectInputStream(fileStream);

        while (true) {
            try {
                emails.add((Email) os.readObject());
            } catch (EOFException exc) {
                os.close();
                break;
            }
        }
    }

    public void printRecipientsWithBirthDate(String inputDate) {
        boolean isPresent = false;
        for (Wishable wishable : wishableRecipients) {
            try {
                if (DateChecker.isEqual(wishable.getBirthday(), inputDate)) {
                    System.out.println(wishable.getName());
                    isPresent = true;
                }
            } catch (ParseException e) {
                System.out.println("Error:");
                System.out.println(e.getMessage());
                return;
            }
        }
        if (!isPresent) System.out.println("No birthdays on given date");
    }
}
