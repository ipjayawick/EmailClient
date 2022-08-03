package Main.ClientProgram.Utilities;

import Main.ClientProgram.Recipients.*;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RecipientCreator {

    private static final ArrayList<Recipient> allRecipients = new ArrayList<>();
    private static final ArrayList<Wishable> wishableRecipients = new ArrayList<>();
    private static final ArrayList<String[]> recipientDataList = new ArrayList<>();

    public static ArrayList<Recipient> getAllRecipients() {
        return allRecipients;
    }

    public static ArrayList<Wishable> getWishableRecipients() {
        return wishableRecipients;
    }

    public static Pair<ArrayList<Recipient>, ArrayList<Wishable>> initializeAndGetRecipientLists() {
        loadRecipientLists();
        return new Pair<>(allRecipients, wishableRecipients);
    }

    private static void loadRecipientLists() {
        lineByLineText();
        for (String[] recipientData : recipientDataList) {
            addRecipientToList(recipientData);
        }
    }

    public static void addRecipientToList(String[] recipientData) {
        String friendState = recipientData[0];
        switch (friendState) {
            case "Official":
                allRecipients.add(new OfficialRecipient(recipientData[1], recipientData[2], recipientData[3]));
                break;
            case "Office_friend":
                OfficeFriendRecipient officeFriendRecipient = new OfficeFriendRecipient(recipientData[1], recipientData[2], recipientData[3], recipientData[4]);
                allRecipients.add(officeFriendRecipient);
                wishableRecipients.add(officeFriendRecipient);
                break;
            case "Personal":
                PersonalRecipient personalRecipient = new PersonalRecipient(recipientData[1], recipientData[2], recipientData[3], recipientData[4]);
                allRecipients.add(personalRecipient);
                wishableRecipients.add(personalRecipient);
                break;
        }
    }

    private static void lineByLineText() {
        try {
            FileReader reader = new FileReader("src/Main/SavedFiles/clientList.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] recipientData = line.split(":|\\,");
                recipientDataList.add(recipientData);
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
