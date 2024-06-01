package de.derioo.javautils.common;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class RegexUtilityTest {


    @Test
    public void testIpV4AddressPattern() {
        assertThat(matchesIpV4("127.0.0.1")).isTrue();
        assertThat(matchesIpV4("192.168.0.1")).isTrue();
        assertThat(matchesIpV4("192.168.0")).isFalse();
        assertThat(matchesIpV4("256.100.50.2")).isFalse();
        assertThat(matchesIpV4("255.100.50.a")).isFalse();
        assertThat(matchesIpV4("255.100.50.1.2")).isFalse();
    }

    @Test
    public void testNumber() {
        assertThat(matchesNumeric("1.2")).isTrue();
        assertThat(matchesNumeric("1")).isTrue();
        assertThat(matchesNumeric("-1")).isTrue();
        assertThat(matchesNumeric("+1")).isTrue();
        assertThat(matchesNumeric("1.2")).isTrue();
        assertThat(matchesNumeric("-1.2")).isTrue();
        assertThat(matchesNumeric("+1.2")).isTrue();

        assertThat(matchesNumeric("1.2.1")).isFalse();
        assertThat(matchesNumeric("+-1")).isFalse();
        assertThat(matchesNumeric("a")).isFalse();
        assertThat(matchesNumeric("1.000.000")).isFalse();
        assertThat(matchesNumeric("10000+")).isFalse();
    }

    @Test
    public void testInt() {
        assertThat(matchesInteger("1")).isTrue();
        assertThat(matchesInteger("-1")).isTrue();
        assertThat(matchesInteger("+1")).isTrue();

        assertThat(matchesInteger("1.2b")).isFalse();
        assertThat(matchesInteger("+-1")).isFalse();
        assertThat(matchesInteger("a")).isFalse();
        assertThat(matchesInteger("1.000.000")).isFalse();
        assertThat(matchesInteger("10000+")).isFalse();
        assertThat(matchesInteger("1.2")).isFalse();
        assertThat(matchesInteger("-1.1")).isFalse();
    }

    public static boolean matchesIpV4(String s) {
        return Pattern.matches(RegexUtility.IPV4_ADDRESS_PATTERN.pattern(), s);
    }

    public static boolean matchesInteger(String s) {
        return Pattern.matches(RegexUtility.INTEGER_PATTERN.pattern(), s);
    }

    public static boolean matchesNumeric(String s) {
        return Pattern.matches(RegexUtility.NUMERIC_PATTERN.pattern(), s);
    }

}
