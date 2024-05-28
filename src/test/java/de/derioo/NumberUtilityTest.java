package de.derioo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.derioo.NumberUtility.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilityTest {

    @Test
    public void testInt() {
        assertThat(isInteger("1")).isTrue();
        assertThat(isInteger("-1")).isTrue();
        assertThat(isInteger("+1")).isTrue();
        assertThat(isInteger("12")).isTrue();

        assertThat(isInteger("1.2")).isFalse();
        assertThat(isInteger("-1.2")).isFalse();
        assertThat(isInteger("+-1")).isFalse();
        assertThat(isInteger("12a")).isFalse();
    }

    @Test
    public void testPosInt() {
        assertThat(isPositiveInteger("1")).isTrue();
        assertThat(isPositiveInteger("12")).isTrue();

        assertThat(isPositiveInteger("0")).isFalse();
        assertThat(isPositiveInteger("-1")).isFalse();
        assertThat(isPositiveInteger("a")).isFalse();
        assertThat(isPositiveInteger("-a")).isFalse();

        assertThat(isPositiveInteger("1", true)).isTrue();
        assertThat(isPositiveInteger("0", true)).isTrue();
        assertThat(isPositiveInteger("12", true)).isTrue();

        assertThat(isPositiveInteger("-1", true)).isFalse();
        assertThat(isPositiveInteger("-122", true)).isFalse();
        assertThat(isPositiveInteger("a", true)).isFalse();
        assertThat(isPositiveInteger("-a", true)).isFalse();
    }

    @Test
    public void testNegInt() {
        assertThat(isNegativeInteger("-1")).isTrue();
        assertThat(isNegativeInteger("-12")).isTrue();

        assertThat(isNegativeInteger("0")).isFalse();
        assertThat(isNegativeInteger("1")).isFalse();
        assertThat(isNegativeInteger("a")).isFalse();
        assertThat(isNegativeInteger("-a")).isFalse();

        assertThat(isNegativeInteger("0", true)).isTrue();
        assertThat(isNegativeInteger("1", true)).isFalse();
        assertThat(isNegativeInteger("-12", true)).isTrue();

        assertThat(isNegativeInteger("1", true)).isFalse();
        assertThat(isNegativeInteger("122", true)).isFalse();
        assertThat(isNegativeInteger("+a", true)).isFalse();
        assertThat(isNegativeInteger("-a", true)).isFalse();
    }

    @Test
    public void testNumber() {
        assertThat(isNumber("1.2")).isTrue();
        assertThat(isNumber("1")).isTrue();
        assertThat(isNumber("-1")).isTrue();
        assertThat(isNumber("-1.2")).isTrue();

        assertThat(isNumber("-a1.2")).isFalse();
        assertThat(isNumber("-ä")).isFalse();
    }

    @Test
    public void testPosNumber() {
        assertThat(isPositiveNumber("12.1")).isTrue();
        assertThat(isPositiveNumber("0.1")).isTrue();
        assertThat(isPositiveNumber("10.1")).isTrue();

        assertThat(isPositiveNumber("0")).isFalse();
        assertThat(isPositiveNumber("-10")).isFalse();

        assertThat(isPositiveNumber("0", true)).isTrue();
        assertThat(isPositiveNumber("-1", true)).isFalse();
    }

    @Test
    public void testNegNumber() {
        assertThat(isNegativeNumber("-12.1")).isTrue();
        assertThat(isNegativeNumber("-0.1")).isTrue();
        assertThat(isNegativeNumber("-10.1")).isTrue();

        assertThat(isNegativeNumber("-0")).isFalse();
        assertThat(isNegativeNumber("10")).isFalse();

        assertThat(isNegativeNumber("0", true)).isTrue();
        assertThat(isNegativeNumber("1", true)).isFalse();
    }

}