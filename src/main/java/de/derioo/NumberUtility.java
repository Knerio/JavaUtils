package de.derioo;

import java.util.regex.Pattern;

/**
 * Some useful methods for numbers
 */
public class NumberUtility {

    /**
     * Tells if a string is an integer (whole number)
     * @param s the string to check
     * @return true if it's an integer, false otherwise
     *
     * @see RegexUtility#INTEGER_PATTERN
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Tells if a string is a number (int, float, double..)
     * @param s the string to check
     * @return true if it's a number, false otherwise
     *
     * @see RegexUtility#NUMERIC_PATTERN
     */
    public static boolean isNumber(String s) {
        try {
            Float.parseFloat(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the give string is a positive number
     * Positive means > 0
     * @see NumberUtility#isPositiveNumber(String, boolean) isPositiveNumber(String, includeZero)
     * @param s the string to check
     * @return true if the number is a number > 0
     */
    public static boolean isPositiveNumber(String s) {
        return isPositiveNumber(s, false);
    }

    /**
     * Checks if the number is positive
     * @param s the string
     * @param includeZero if true >= 0, otherwise > 0
     * @return true if the number is a number is positive
     */
    public static boolean isPositiveNumber(String s, boolean includeZero) {
        try {
            float v = Float.parseFloat(s);
            if (includeZero) {
                return v >= 0;
            }
            return v > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the given number is negative (< 0)
     * @param s the string to check
     * @return true if it's a number and its negative, false otherwise
     * @see NumberUtility#isNegativeNumber(String, boolean) isNegativeNumber(String, includeZero)
     */
    public static boolean isNegativeNumber(String s) {
        return isNegativeNumber(s, false);
    }

    /**
     * Checks if the number is negative
     * @param s the string
     * @param includeZero if true <= 0, otherwise < 0
     * @return true if the number is a number is negative
     */
    public static boolean isNegativeNumber(String s, boolean includeZero) {
        try {
            float v = Float.parseFloat(s);
            if (includeZero) {
                return v <= 0;
            }
            return v < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the give string is a positive int
     * Positive means > 0
     * @see NumberUtility#isPositiveNumber(String, boolean) isPositiveNumber(String, includeZero)
     * @param s the string to check
     * @return true if the number is a number > 0
     */
    public static boolean isPositiveInteger(String s) {
        return isPositiveInteger(s, false);
    }

    /**
     * Checks if the int is positive
     * @param s the string
     * @param includeZero if true >= 0, otherwise > 0
     * @return true if the number is a number is positive
     */
    public static boolean isPositiveInteger(String s, boolean includeZero) {
        try {
            int i = Integer.parseInt(s);
            if (includeZero) {
                return i >= 0;
            }
            return i > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if the given int is negative (< 0)
     * @param s the string to check
     * @return true if it's a number and its negative, false otherwise
     * @see NumberUtility#isNegativeNumber(String, boolean) isNegativeNumber(String, includeZero)
     */
    public static boolean isNegativeInteger(String s) {
        return isNegativeNumber(s, false);
    }

    /**
     * Checks if the int is negative
     * @param s the string
     * @param includeZero if true <= 0, otherwise < 0
     * @return true if the number is a number is negative
     */
    public static boolean isNegativeInteger(String s, boolean includeZero) {
        try {
            int i = Integer.parseInt(s);
            if (includeZero) {
                return i <= 0;
            }
            return i < 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }



}
