package Main.ClientProgram.Recipients;

public class OfficeFriendRecipient extends Recipient implements Wishable {
    private String designation;
    private String birthday;

    public OfficeFriendRecipient(String name, String email, String designation, String birthday) {
        super(name, email);
        this.birthday = birthday;
        this.designation = designation;
    }

    @Override
    public String getBirthdayWishMsg() {
        return "Wish you a Happy Birthday!";
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return this.birthday;
    }
}
