package validation;

import java.util.Scanner;
import java.util.regex.*;

public class IDCardValidation {


    private static final String matchNid = "^[0-9]{11}$";

    public static boolean isValidNID(String nid) {
        Pattern pattern = Pattern.compile(matchNid);
        Matcher matcher = pattern.matcher(nid);
        return matcher.matches();
    }

    //     Passport Validation
    public static boolean isValidPassportNumber(String passportNumber) {
        String regex = "^[A-Z]{1}[0-9]{7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(passportNumber);
        return matcher.matches();
    }

    //     Driving License Validation
    public static boolean isValidDrivingLicenseNumber(String licenseNumber) {
        String regex = "^[A-Za-z]{2}\\d{2}-[0-9]{7}-[0-9]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(licenseNumber);
        return matcher.matches();
    }



}
