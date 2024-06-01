package de.derioo.javautils.paper.javautils.common;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * Some (maybe) useful regexes
 */
@UtilityClass
public class RegexUtility {

    /**
     * Simple regex for a IPV4 Adress
     * for example '127.0.0.1'
     */
    public final Pattern IPV4_ADDRESS_PATTERN = Pattern.compile("^(?:(?:25[0-5]|2[0-4]\\d|1?\\d{1,2})(?:\\.(?!$)|$)){4}$");

    /**
     *
     * Matches numeric strings
     * Also decimal numbers and negative/positives
     *
     * @see NumberUtility
     */
    public final Pattern NUMERIC_PATTERN = Pattern.compile("^[+-]?(\\d{1,3}(,(\\d{3})?)+(\\.\\d+)?|(\\d*\\.)?\\d+)$");

    /**
     *
     * Matches numeric strings
     * Only integers numbers
     *
     * @see NumberUtility
     */
    public final Pattern INTEGER_PATTERN = Pattern.compile("^[+-]?[0-9]+$");

    /**
     * Matches a Large Number format
     * e.g. -100k, 200, 12m
     * @see NumberUtility#formatLargeNumber(Long)
     */
    public final Pattern LARGE_NUMBER_REGEX = Pattern.compile("^[+-]?(\\d*)\\.?(\\d+)([kmbt]?)$");

}
