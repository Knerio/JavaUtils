package de.derioo.javautils.common;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * This class adds some utility functions for strings
 */
@UtilityClass
public class StringUtility {

    /**
     * Replaces the last seen `regex` string in the string with
     * the `replacement`
     *
     * @param s           the string which will be modified
     * @param regex       the regex to match
     * @param replacement the replacement
     * @return the modified string
     */
    public String replaceLast(@NotNull String s, String regex, String replacement) {
        return s.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    /**
     * Reverses the given string
     *
     * @param s the string to reverse
     * @return the reversed string
     */
    public @NotNull String reverse(@NotNull String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public String replaceFirst(String base, String matcher, String replacement) {
        return base.replaceFirst(Pattern.quote(matcher), replacement);
    }

}
