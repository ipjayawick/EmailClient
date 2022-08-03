package Main.ClientProgram.Emails;

import Main.ClientProgram.Recipients.Wishable;

public class BirthdayEmailCreator extends EmailCreator {
    Wishable wishableRecipient;

    public Email createEmail(Wishable wishable) {
        this.wishableRecipient = wishable;
        this.email = new Email(wishableRecipient.getEmail(), "subject", wishableRecipient.getBirthdayWishMsg());
        return email;
    }
}
