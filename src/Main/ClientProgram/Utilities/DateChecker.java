package Main.ClientProgram.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateChecker {
    private static final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");

    public static boolean isEqual(String date1, String date2) throws ParseException {
        Date d1 = sdFormat.parse(date1);
        Date d2 = sdFormat.parse(date2);
        boolean isEqual = sdFormat.format(d1).equals(sdFormat.format(d2));
        return isEqual;
    }

    public static boolean isTodayBirthday(String date) throws ParseException {
        Date inputDate = sdFormat.parse(date);
        Date today = new Date();
        return (today.getMonth() == inputDate.getMonth() && today.getDate() == inputDate.getDate());
    }

    public static String getCurrentDate() {
        return sdFormat.format(new Date());
    }
}
