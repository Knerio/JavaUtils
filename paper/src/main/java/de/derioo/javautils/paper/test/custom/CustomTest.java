package de.derioo.javautils.paper.test.custom;

import de.derioo.javautils.paper.test.custom.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomTest {

    public final void testAll() {
        for (Method declaredMethod : getClass().getDeclaredMethods()) {
            if (!declaredMethod.isAnnotationPresent(Test.class)) continue;
            try {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

}
