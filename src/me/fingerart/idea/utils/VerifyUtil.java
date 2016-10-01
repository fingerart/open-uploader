package me.fingerart.idea.utils;

import org.apache.http.util.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by FingerArt on 16/10/1.
 */
public class VerifyUtil {
    public static boolean verifyUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^http://[a-zA-Z0-9.]+..*");
        return pattern.matcher(url).matches();
    }

}
