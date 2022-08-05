package Main.ClientProgram.Utilities;

import Main.ClientProgram.Recipients.*;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RecipientCreator {
    /**Create Recipient objects using recipient's data stored in the disk.
     *Create Recipient objects using user input data.
     *Store Created Recipient objects in lists and Return then when requested*/

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
        return new Pair<>(new ArrayList<Recipient>(allRecipients), new ArrayList<Wishable>(wishableRecipients));
    }

    private static void loadRecipientLists() throws IOException {
        lineByLineText();
        for (String[] recipientData : recipientDataList) {
            createRecipient(recipientData);
        }
    }

    public static Recipient createRecipient(String[] recipientData) {
        String friendState = recipientData[0].toLowerCase().trim();
        switch (friendState) {
            case "official":
                OfficialRecipient officialRecipient = new OfficialRecipient(recipientData[1], recipientData[2], recipientData[3]);
                allRecipients.add(officialRecipient);
                return officialRecipient;
            case "office_friend":
                OfficeFriendRecipient officeFriendRecipient = new OfficeFriendRecipient(recipientData[1], recipientData[2], recipientData[3], recipientData[4]);
                allRecipients.add(officeFriendRecipient);
                wishableRecipients.add(officeFriendRecipient);
                return officeFriendRecipient;
            case "personal":
                PersonalRecipient personalRecipient = new PersonalRecipient(recipientData[1], recipientData[2], recipientData[3], recipientData[4]);
                allRecipients.add(personalRecipient);
                wishableRecipients.add(personalRecipient);
                return personalRecipient;
            default:
                System.out.println("Invalid Recipient data!");
                return null;
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
