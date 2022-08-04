package Main.ClientProgram.Recipients;

public abstract class Recipient {
    public static int RecipientCount=0;
    public String name;
    String email;

    public Recipient(String name, String email) {
        RecipientCount++;
        this.name = name;
        this.email = email;
    }

}
