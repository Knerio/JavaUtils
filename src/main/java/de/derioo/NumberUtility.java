package de.derioo;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some useful methods for numbers
 */
@UtilityClass
public class NumberUtility {

    public final NumberFormat DEFAULT_NUMBERFORMAT = NumberFormat.getNumberInstance();

    public final Map<String, Long> ABBREVATION_MAP = Map.of(
            "k", 1_000L,
            "m", 1_000_000L,
            "b", 1_000_000_000L,
            "t", 1_000_000_000_000L
    );

    /**
     * Tells if a string is an integer (whole number)
     *
     * @param s the string to check
     * @return true if it's an integer, false otherwise
     * @see RegexUtility#INTEGER_PATTERN
     */
    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Tells if a string is a number (int, float, double..)
     *
     * @param s the string to check
     * @return true if it's a number, false otherwise
     * @see RegexUtility#NUMERIC_PATTERN
     */
    public boolean isNumber(String s) {
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
     *
     * @param s the string to check
     * @return true if the number is a number > 0
     * @see NumberUtility#isPositiveNumber(String, boolean) isPositiveNumber(String, includeZero)
     */
    public boolean isPositiveNumber(String s) {
        return isPositiveNumber(s, false);
    }

    /**
     * Checks if the number is positive
     *
     * @param s           the string
     * @param includeZero if true >= 0, otherwise > 0
     * @return true if the number is a number is positive
     */
    public boolean isPositiveNumber(String s, boolean includeZero) {
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
     *
     * @param s the string to check
     * @return true if it's a number and its negative, false otherwise
     * @see NumberUtility#isNegativeNumber(String, boolean) isNegativeNumber(String, includeZero)
     */
    public boolean isNegativeNumber(String s) {
        return isNegativeNumber(s, false);
    }

    /**
     * Checks if the number is negative
     *
     * @param s           the string
     * @param includeZero if true <= 0, otherwise < 0
     * @return true if the number is a number is negative
     */
    public boolean isNegativeNumber(String s, boolean includeZero) {
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
     *
     * @param s the string to check
     * @return true if the number is a number > 0
     * @see NumberUtility#isPositiveNumber(String, boolean) isPositiveNumber(String, includeZero)
     */
    public boolean isPositiveInteger(String s) {
        return isPositiveInteger(s, false);
    }

    /**
     * Checks if the int is positive
     *
     * @param s           the string
     * @param includeZero if true >= 0, otherwise > 0
     * @return true if the number is a number is positive
     */
    public boolean isPositiveInteger(String s, boolean includeZero) {
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
     *
     * @param s the string to check
     * @return true if it's a number and its negative, false otherwise
     * @see NumberUtility#isNegativeNumber(String, boolean) isNegativeNumber(String, includeZero)
     */
    public boolean isNegativeInteger(String s) {
        return isNegativeNumber(s, false);
    }

    /**
     * Checks if the int is negative
     *
     * @param s           the string
     * @param includeZero if true <= 0, otherwise < 0
     * @return true if the number is a number is negative
     */
    public boolean isNegativeInteger(String s, boolean includeZero) {
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

    /**
     * Formats a number to the default decimal format
     *
     * @param i the number to format
     * @return the format
     */
    public String format(Integer i) {
        return DEFAULT_NUMBERFORMAT.format(i);
    }

    /**
     * Formats a number to the default decimal format
     *
     * @param l the number to format
     * @return the format
     */
    public String format(Long l) {
        return DEFAULT_NUMBERFORMAT.format(l);
    }

    /**
     * Formats a number to the default decimal format
     * <br>
     * <b>WARNING:</b> The number is rounded to 2 decimal points, e. g. 0.9999 -> 1, 0.1299 -> 0.13
     *
     * @param d the number to format
     * @return the format
     */
    public String format(Double d) {
        return DEFAULT_NUMBERFORMAT.format(d);
    }

    /**
     * Formats a large number
     * e.g. 1000 -> 1k, 1_100_000 -> 1.1m
     * <br>
     * <b>WARNING:</b> Decimals get rounded after 2 places
     * <br>
     * This is the opposite to {@link NumberUtility#getNumberByLargeNumberFormat(String) }
     *
     * @param number the number to format
     * @return the formatted number
     */
    public String formatLargeNumber(Long number) {
        boolean negative = number < 0;
        long abs = Math.abs(number);
        if (abs <= 1000) return String.valueOf(number);

        long i = 1000;
        while (abs >= i * 1000) i *= 1000;
        for (Map.Entry<String, Long> entry : ABBREVATION_MAP.entrySet()) {
            if (entry.getValue().equals(i)) return computeFormat(abs, entry.getValue(), negative, entry.getKey());
        }
        return format(number);
    }

    /**
     * Gets the original number got from {@link NumberUtility#formatLargeNumber(Long)}
     *
     * @param formatted the formatted number (got from {@link NumberUtility#formatLargeNumber(Long)})
     * @return the original number
     * @throws NumberFormatException if the provided format is not a malformed format
     */
    public Long getNumberByLargeNumberFormat(String formatted) throws NumberFormatException {
        String regex = "^[+-]?(\\d*)\\.?(\\d+)([kmbt]?)$";
        Matcher matcher = Pattern.compile(regex).matcher(formatted);
        if (!matcher.matches())
            throw new NumberFormatException("Given string isnt a formatted string! got: " + formatted + " needed: " + regex);
        boolean negative = String.valueOf(formatted.charAt(0)).equals("-");
        double parsed = Double.parseDouble(matcher.group(1).isEmpty() ? matcher.group(2) : (matcher.group(1) + "." + matcher.group(2)));

        return (long) (parsed * ABBREVATION_MAP.getOrDefault(matcher.group(3), 1L)) * (negative ? -1L : 1L);
    }

    @Contract(pure = true)
    private @NotNull String computeFormat(long l, long divider, boolean negative, String abr) {
        long remainder = l % divider;
        long normal = (long) (l / (double) divider);
        String remainderString = String.valueOf(remainder);
        if (remainderString.length() > 2) {
            long firstTwo = Long.parseLong(remainderString.substring(0, 2));
            long third = Long.parseLong(remainderString.substring(2, 3));
            remainder = firstTwo + (third > 5 ? 1 : 0);
            while (remainder % 10 == 0) {
                remainder /= 10;
            }
        }
        return (negative ? "-" : "") + normal + (remainder == 0 ? "" : "." + remainder) + abr;
    }

}
