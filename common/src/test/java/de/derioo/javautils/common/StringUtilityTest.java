package de.derioo.javautils.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilityTest {

    @Test
    public void testReverse() {
        assertThat(StringUtility.reverse("123")).isEqualTo("321");
        assertThat(StringUtility.reverse("abc cde")).isEqualTo("edc cba");
    }

    @Test
    public void testReplaceLast() {
        assertThat(StringUtility.replaceLast("Test Test", "Test", "1")).isEqualTo("Test 1");
        assertThat(StringUtility.replaceLast("Test", "Test", "")).isEqualTo("");
        assertThat(StringUtility.replaceLast("1 2 3", "[0-9] [0-9]", "")).isEqualTo("1 ");
        assertThat(StringUtility.replaceLast("1 2 3", "[0-9]", "")).isEqualTo("1 2 ");
    }

    @Test
    public void testCap() {
        assertThat(StringUtility.capAtNCharacters("12345", 3)).isEqualTo("...");
        assertThat(StringUtility.capAtNCharacters("12345678", 6)).isEqualTo("123...");
    }

}
