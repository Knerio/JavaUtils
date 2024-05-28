package de.derioo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.derioo.StringUtility.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilityTest {

    @Test
    public void testReverse() {
        assertThat(reverse("123")).isEqualTo("321");
        assertThat(reverse("abc cde")).isEqualTo("edc cba");
    }

    @Test
    public void testReplaceLast() {
        assertThat(replaceLast("Test Test", "Test", "1")).isEqualTo("Test 1");
        assertThat(replaceLast("Test", "Test", "")).isEqualTo("");
        assertThat(replaceLast("1 2 3", "[0-9] [0-9]", "")).isEqualTo("1 ");
        assertThat(replaceLast("1 2 3", "[0-9]", "")).isEqualTo("1 2 ");
    }

}
