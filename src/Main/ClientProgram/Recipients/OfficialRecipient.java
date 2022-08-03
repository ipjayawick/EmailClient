package Main.ClientProgram.Recipients;

public class OfficialRecipient extends Recipient {
    String designation;

    public OfficialRecipient(String name, String email, String designation) {
        super(name, email);
        this.designation = designation;
    }
}
