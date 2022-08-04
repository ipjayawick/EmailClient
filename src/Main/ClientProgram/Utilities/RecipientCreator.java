package Main.ClientProgram.Utilities;

import Main.ClientProgram.Recipients.*;
import javafx.util.Pair;

import java.io.*;
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

    public static Pair<ArrayList<Recipient>, ArrayList<Wishable>> initializeAndGetRecipientLists() throws IOException {
        loadRecipientLists();
        return new Pair<>(allRecipients, wishableRecipients);
    }

    private static void loadRecipientLists() throws IOException {
        lineByLineText();
        for (String[] recipientData : recipientDataList) {
            addRecipientToList(recipientData);
        }
    }

    public static void addRecipientToList(String[] recipientData) {
        String friendState = recipientData[0].toLowerCase().trim();
        switch (friendState) {
            case "official":
                allRecipients.add(new OfficialRecipient(recipientData[1], recipientData[2], recipientData[3]));
                break;
            case "office_friend":
                OfficeFriendRecipient officeFriendRecipient = new OfficeFriendRecipient(recipientData[1], recipientData[2], recipientData[3], recipientData[4]);
                allRecipients.add(officeFriendRecipient);
                wishableRecipients.add(officeFriendRecipient);
                break;
            case "personal":
                PersonalRecipient personalRecipient = new PersonalRecipient(recipientData[1], recipientData[2], recipientData[3], recipientData[4]);
                allRecipients.add(personalRecipient);
                wishableRecipients.add(personalRecipient);
                break;
        }
    }

    private static void lineByLineText() throws IOException {
            FileReader reader = new FileReader("src/Main/SavedFiles/clientList.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] recipientData = line.split(":|\\,");
                recipientDataList.add(recipientData);
            }
            reader.close();
    }

}
