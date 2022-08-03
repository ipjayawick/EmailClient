package Main.ClientProgram.Recipients;

public abstract class Recipient {
    public String name;
    String email;

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
