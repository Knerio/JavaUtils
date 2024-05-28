package de.derioo;

import java.util.regex.Pattern;

public class RegexUtility {

    public static final Pattern IPV4_ADDRESS_PATTERN = Pattern.compile("^(?:(?:25[0-5]|2[0-4]\\d|1?\\d{1,2})(?:\\.(?!$)|$)){4}$");

}
