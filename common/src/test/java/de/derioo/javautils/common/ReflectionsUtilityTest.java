package de.derioo.javautils.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static de.derioo.javautils.common.ReflectionsUtility.callMethod;

public class ReflectionsUtilityTest {

    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = getClass().getMethod("testMethod", String.class, Boolean.class);
        callMethod(method, this, (o, parameter) -> {
            if (o instanceof Boolean && ((Boolean) o).booleanValue()) return false;
            return true;
        }, "abcasd", true, false);
    }


    public void testMethod(String abc, Boolean abcd) {
        Assertions.assertThat(abc).isEqualTo("abcasd");
        Assertions.assertThat(abcd).isFalse();
    }

}
