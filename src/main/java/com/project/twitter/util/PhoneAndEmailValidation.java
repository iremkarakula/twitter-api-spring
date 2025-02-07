package com.project.twitter.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneAndEmailValidation {

    public static boolean isPhone(String contact) {
        String phoneRegex = "^[1-9]\\d{9}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }


    public static boolean isEmail(String contact) {

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }
}
