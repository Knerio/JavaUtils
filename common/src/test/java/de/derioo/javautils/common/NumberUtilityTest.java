package de.derioo.javautils.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberUtilityTest {

    @Test
    public void testInt() {
        Assertions.assertThat(NumberUtility.isInteger("1")).isTrue();
        Assertions.assertThat(NumberUtility.isInteger("-1")).isTrue();
        Assertions.assertThat(NumberUtility.isInteger("+1")).isTrue();
        Assertions.assertThat(NumberUtility.isInteger("12")).isTrue();

        Assertions.assertThat(NumberUtility.isInteger("1.2")).isFalse();
        Assertions.assertThat(NumberUtility.isInteger("-1.2")).isFalse();
        Assertions.assertThat(NumberUtility.isInteger("+-1")).isFalse();
        Assertions.assertThat(NumberUtility.isInteger("12a")).isFalse();
    }

    @Test
    public void testPosInt() {
        Assertions.assertThat(NumberUtility.isPositiveInteger("1")).isTrue();
        Assertions.assertThat(NumberUtility.isPositiveInteger("12")).isTrue();

        Assertions.assertThat(NumberUtility.isPositiveInteger("0")).isFalse();
        Assertions.assertThat(NumberUtility.isPositiveInteger("-1")).isFalse();
        Assertions.assertThat(NumberUtility.isPositiveInteger("a")).isFalse();
        Assertions.assertThat(NumberUtility.isPositiveInteger("-a")).isFalse();

        Assertions.assertThat(NumberUtility.isPositiveInteger("1", true)).isTrue();
        Assertions.assertThat(NumberUtility.isPositiveInteger("0", true)).isTrue();
        Assertions.assertThat(NumberUtility.isPositiveInteger("12", true)).isTrue();

        Assertions.assertThat(NumberUtility.isPositiveInteger("-1", true)).isFalse();
        Assertions.assertThat(NumberUtility.isPositiveInteger("-122", true)).isFalse();
        Assertions.assertThat(NumberUtility.isPositiveInteger("a", true)).isFalse();
        Assertions.assertThat(NumberUtility.isPositiveInteger("-a", true)).isFalse();
    }

    @Test
    public void testNegInt() {
        Assertions.assertThat(NumberUtility.isNegativeInteger("-1")).isTrue();
        Assertions.assertThat(NumberUtility.isNegativeInteger("-12")).isTrue();

        Assertions.assertThat(NumberUtility.isNegativeInteger("0")).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeInteger("1")).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeInteger("a")).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeInteger("-a")).isFalse();

        Assertions.assertThat(NumberUtility.isNegativeInteger("0", true)).isTrue();
        Assertions.assertThat(NumberUtility.isNegativeInteger("1", true)).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeInteger("-12", true)).isTrue();

        Assertions.assertThat(NumberUtility.isNegativeInteger("1", true)).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeInteger("122", true)).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeInteger("+a", true)).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeInteger("-a", true)).isFalse();
    }

    @Test
    public void testNumber() {
        Assertions.assertThat(NumberUtility.isNumber("1.2")).isTrue();
        Assertions.assertThat(NumberUtility.isNumber("1")).isTrue();
        Assertions.assertThat(NumberUtility.isNumber("-1")).isTrue();
        Assertions.assertThat(NumberUtility.isNumber("-1.2")).isTrue();

        Assertions.assertThat(NumberUtility.isNumber("-a1.2")).isFalse();
        Assertions.assertThat(NumberUtility.isNumber("-ä")).isFalse();
    }

    @Test
    public void testPosNumber() {
        Assertions.assertThat(NumberUtility.isPositiveNumber("12.1")).isTrue();
        Assertions.assertThat(NumberUtility.isPositiveNumber("0.1")).isTrue();
        Assertions.assertThat(NumberUtility.isPositiveNumber("10.1")).isTrue();

        Assertions.assertThat(NumberUtility.isPositiveNumber("0")).isFalse();
        Assertions.assertThat(NumberUtility.isPositiveNumber("-10")).isFalse();

        Assertions.assertThat(NumberUtility.isPositiveNumber("0", true)).isTrue();
        Assertions.assertThat(NumberUtility.isPositiveNumber("-1", true)).isFalse();
    }

    @Test
    public void testNegNumber() {
        Assertions.assertThat(NumberUtility.isNegativeNumber("-12.1")).isTrue();
        Assertions.assertThat(NumberUtility.isNegativeNumber("-0.1")).isTrue();
        Assertions.assertThat(NumberUtility.isNegativeNumber("-10.1")).isTrue();

        Assertions.assertThat(NumberUtility.isNegativeNumber("-0")).isFalse();
        Assertions.assertThat(NumberUtility.isNegativeNumber("10")).isFalse();

        Assertions.assertThat(NumberUtility.isNegativeNumber("0", true)).isTrue();
        Assertions.assertThat(NumberUtility.isNegativeNumber("1", true)).isFalse();
    }

    @Test
    public void testNumberFormat() {
        Assertions.assertThat(NumberUtility.format(1)).isEqualTo("1");
        Assertions.assertThat(NumberUtility.format(1.1)).isEqualTo("1.1");
        Assertions.assertThat(NumberUtility.format(1_000_000)).isEqualTo("1,000,000");
        Assertions.assertThat(NumberUtility.format(1_000_000.12)).isEqualTo("1,000,000.12");
        Assertions.assertThat(NumberUtility.format(1_000_000.129999)).isEqualTo("1,000,000.13");
        Assertions.assertThat(NumberUtility.format(1_000_000.999999)).isEqualTo("1,000,001");

        Assertions.assertThat(NumberUtility.format(-1)).isEqualTo("-1");
        Assertions.assertThat(NumberUtility.format(-1.1)).isEqualTo("-1.1");
        Assertions.assertThat(NumberUtility.format(-1_000_000)).isEqualTo("-1,000,000");
        Assertions.assertThat(NumberUtility.format(-1_000_000.12)).isEqualTo("-1,000,000.12");
        Assertions.assertThat(NumberUtility.format(-1_000_000.129999)).isEqualTo("-1,000,000.13");
        Assertions.assertThat(NumberUtility.format(-1_000_000.999999)).isEqualTo("-1,000,001");
    }

    @Test
    public void testLargeNumberFormat() {
        Long largeNum = 1_234L;
        String formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("1.23k");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(1230L);
        largeNum = 1_100_234L;
        formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("1.1m");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(1_100_000);
        largeNum = 1_000_000_000L;
        formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("1b");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(largeNum);
        largeNum = 1_000_000_000_000L;
        formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("1t");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(largeNum);

        largeNum = -1_234L;
        formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("-1.23k");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(-1230L);
        largeNum = -1_100_234L;
        formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("-1.1m");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(-1_100_000);
        largeNum = -1_000_000_000L;
        formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("-1b");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(largeNum);
        largeNum = -1_000_000_000_000L;
        formatted = NumberUtility.formatLargeNumber(largeNum);
        assertThat(formatted).isEqualTo("-1t");
        Assertions.assertThat(NumberUtility.getNumberByLargeNumberFormat(formatted)).isEqualTo(largeNum);
    }
}
