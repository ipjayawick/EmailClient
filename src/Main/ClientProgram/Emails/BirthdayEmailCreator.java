package Main.ClientProgram.Emails;

import Main.ClientProgram.Recipients.Wishable;

public class BirthdayEmailCreator extends EmailCreator {
     private Wishable wishableRecipient;

    public Email createEmail(Wishable wishable) {
        this.wishableRecipient = wishable;
        this.email = new Email(wishableRecipient.getEmail(), "Birthday Wishes!", wishableRecipient.getBirthdayWishMsg() + "\n" + "Isuru");
        return email;
    }
}
