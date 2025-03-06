package validation;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidation {

    private static final String matchEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String matchName = "^[a-zA-Z]+(\\s[a-zA-Z]+)?$";
    private static final String matchCompany = "^[a-zA-Z\\s]+$";
    private static final String matchPinCode = "^[1-9][0-9]{5}$";


    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(matchName);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidCompany(String name) {
        Pattern pattern = Pattern.compile(matchCompany);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(matchEmail);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPinCode(String pincode) {
        Pattern pattern = Pattern.compile(matchPinCode);
        Matcher matcher = pattern.matcher(pincode);
        return matcher.matches();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Pin Code ");
        String testPinCode = s.next();

        if (isValidPinCode(testPinCode)) {
            System.out.println("Valid PIN code");
        } else {
            System.out.println("Invalid PIN code");
        }

    }
}
