package de.derioo.javautils.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.NumberFormat;
import java.util.regex.Matcher;

/**
 * Some useful methods for numbers
 */
@UtilityClass
public class NumberUtility {

    public final NumberFormat DEFAULT_NUMBERFORMAT = NumberFormat.getNumberInstance();



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
     * Tells if a string is a number (int, float, double...)
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
     * <b>WARNING:</b> The number is rounded to 2 decimal points, e.g., 0.9999 -> 1, 0.1299 -> 0.13
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

        AbbreviationEntry highest = AbbreviationEntry.getHighestAbbreviation(abs);
        if (highest == null) return format(number);
        return computeFormat(abs, highest.getAmount(), negative, highest.getAbbreviation());
    }

    /**
     * Gets the original number got from {@link NumberUtility#formatLargeNumber(Long)}
     *
     * @param formatted the formatted number (got from {@link NumberUtility#formatLargeNumber(Long)})
     * @return the original number
     * @throws NumberFormatException if the provided format is not a malformed format
     */
    public Long getNumberByLargeNumberFormat(String formatted) throws NumberFormatException {
        Matcher matcher = RegexUtility.LARGE_NUMBER_REGEX.matcher(formatted);
        if (!matcher.matches())
            throw new NumberFormatException("Given string isn't a formatted big number string! got: " + formatted + " needed: " + RegexUtility.LARGE_NUMBER_REGEX.pattern());

        boolean negative = String.valueOf(formatted.charAt(0)).equals("-");

        double parsed = Double.parseDouble(matcher.group(1).isEmpty() ? matcher.group(2) : (matcher.group(1) + "." + matcher.group(2)));
        return (long) (parsed * AbbreviationEntry.getEntryByAbbreviation(matcher.group(3), AbbreviationEntry.one).amount) * (negative ? -1L : 1L);
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


    @AllArgsConstructor
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    private class AbbreviationEntry {

        static final AbbreviationEntry one = new AbbreviationEntry("", 1);
        static final AbbreviationEntry thousand = new AbbreviationEntry("k", 1_000L);
        static final AbbreviationEntry million = new AbbreviationEntry("m", 1_000_000L);
        static final AbbreviationEntry billion = new AbbreviationEntry("b", 1_000_000_000L);
        static final AbbreviationEntry trillion = new AbbreviationEntry("t", 1_000_000_000_000L);


        private static final AbbreviationEntry[] ABBREVATION_ENTRIES = new AbbreviationEntry[]{
                thousand,
                million,
                billion,
                trillion
        };

        String abbreviation;
        long amount;

        @Contract(pure = true)
        private static @Nullable AbbreviationEntry getHighestAbbreviation(long number) {
            for (int i = 0; i < ABBREVATION_ENTRIES.length; i++) {
                AbbreviationEntry entry = ABBREVATION_ENTRIES[i];
                AbbreviationEntry next = i != ABBREVATION_ENTRIES.length - 1 ? ABBREVATION_ENTRIES[i + 1] : null;
                if (next != null && next.amount <= number) continue;
                if (entry.amount <= number) return entry;

            }
            return null;
        }

        private static AbbreviationEntry getEntryByAbbreviation(String abbreviation, AbbreviationEntry fallback) {
            for (AbbreviationEntry entry : ABBREVATION_ENTRIES) {
                if (entry.abbreviation.equals(abbreviation)) return entry;
            }
            return fallback;
        }

    }

}
