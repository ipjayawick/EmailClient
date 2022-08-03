package Main;

//200272L

import Main.ClientProgram.EmailClientProgram;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class EmailClientInterface {
    public static void main(String[] args) throws IOException, ClassNotFoundException, ParseException {
        Boolean programOn = true;
        EmailClientProgram program = new EmailClientProgram();
        Scanner scanner = new Scanner(System.in);
        String userInput;

        while (programOn) {
            System.out.println("Enter option type: \n" +
                    "1 - Adding a new recipient\n" +
                    "2 - Sending an email\n" +
                    "3 - Printing out all the recipients who have birthdays\n" +
                    "4 - Printing out details of all the emails sent\n" +
                    "5 - Printing out the number of recipient objects in the application\n" +
                    "6 - Exit");

            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1:
                    System.out.print("Enter recipient in this format --->\n" +
                            "\tofficial: <name>, <email>,<designation>\n" +
                            "\tOffice_friend: <name>,<email>,<designation>,<birthday(yyyy/MM/dd)>\n" +
                            "\tPersonal: <name>,<nick-name>,<email>,<birthday(yyyy/MM/dd)>\n\n"+
                            "\tExample---> Office_friend: kamal,kamal@gmail.com,clerk,2000/12/12\n");
                    userInput = scanner.nextLine();
                    program.addRecipient(userInput);
                    break;
                case 2:
                    // input format - email, subject, content
                    System.out.print("Enter details in the format ---> <email>, <subject>, <content>\n");
                    userInput = scanner.nextLine();
                    program.sendEmail(userInput);//exception thrown
                    break;
                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    System.out.print("Enter date in the format ---> yyyy/MM/dd (ex: 2018/09/17)\n");
                    userInput = scanner.nextLine();
                    program.printRecipientsWithBirthDate(userInput);
                    break;
                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    System.out.print("Enter date in the format ---> yyyy/MM/dd (ex: 2018/09/17)\n");
                    userInput = scanner.nextLine();
                    program.printEmailsSentOnDate(userInput);
                    // code to print the details of all the emails sent on the input date
                    break;
                case 5:
                    // code to print the number of recipient objects in the application
                    program.printRecipientCount();
                    break;
                case 6:
                    programOn = false;
                    break;
            }
        }
    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)