package Main.ClientProgram.Recipients;

public class PersonalRecipient extends Recipient implements Wishable {
    private String nickname;
    private final String birthday;

    public PersonalRecipient(String name, String nickname, String email, String birthday) {
        super(name, email);
        this.nickname = nickname;
        this.birthday = birthday;
    }

    @Override
    public String getBirthdayWishMsg() {
        return "Hugs and love on your birthday!";
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getBirthday() {
        return this.birthday;
    }
}
