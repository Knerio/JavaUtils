package de.derioo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static de.derioo.RegexUtility.IPV4_ADDRESS_PATTERN;
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

    public static boolean matchesIpV4(String s) {
        return Pattern.matches(IPV4_ADDRESS_PATTERN.pattern(), s);
    }

}
