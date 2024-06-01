package de.derioo.javautils.common;

import de.derioo.javautils.paper.javautils.common.StringUtility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilityTest {

    @Test
    public void testReverse() {
        Assertions.assertThat(StringUtility.reverse("123")).isEqualTo("321");
        Assertions.assertThat(StringUtility.reverse("abc cde")).isEqualTo("edc cba");
    }

    @Test
    public void testReplaceLast() {
        Assertions.assertThat(StringUtility.replaceLast("Test Test", "Test", "1")).isEqualTo("Test 1");
        Assertions.assertThat(StringUtility.replaceLast("Test", "Test", "")).isEqualTo("");
        Assertions.assertThat(StringUtility.replaceLast("1 2 3", "[0-9] [0-9]", "")).isEqualTo("1 ");
        Assertions.assertThat(StringUtility.replaceLast("1 2 3", "[0-9]", "")).isEqualTo("1 2 ");
    }

}
