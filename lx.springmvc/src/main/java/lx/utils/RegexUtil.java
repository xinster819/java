package lx.utils;

public class RegexUtil {

    final static String MOBILE_REGEX = "1(3|4|5|7|8)[0-9]{9}";

    final static String IDENTITY_CARD_REGEX = "(^[0-9]{15}$)|(^[0-9]{17}([0-9]|X)$)";

    final static String EMAIL_REGEX = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
    
    final static String SPECHARS_REGEX = "[^a-zA-Z0-9]";
    
    public static boolean mobileMatch(String input) {
        return input.matches(MOBILE_REGEX);
    }

    public static boolean identityCardMatch(String input) {
        return input.matches(IDENTITY_CARD_REGEX);
    }

    public static boolean emailMatch(String input) {
        return input.matches(EMAIL_REGEX);
    }

    public static String specharsReplace(String input) {
        return input.replaceAll(SPECHARS_REGEX, "");
    }

    public static void main(String[] args) {
        System.out.println(specharsReplace("540818255@@qq.com"));
    }
}
