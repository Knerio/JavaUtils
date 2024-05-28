package de.derioo;

import java.util.regex.Pattern;

/**
 * Some (maybe) useful regexes
 */
public class RegexUtility {

    /**
     * Simple regex for a IPV4 Adress
     * for example '127.0.0.1'
     */
    public static final Pattern IPV4_ADDRESS_PATTERN = Pattern.compile("^(?:(?:25[0-5]|2[0-4]\\d|1?\\d{1,2})(?:\\.(?!$)|$)){4}$");

}
