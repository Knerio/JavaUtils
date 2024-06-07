package de.derioo.javautils.paper.test.custom;

import de.derioo.javautils.paper.test.custom.annotation.MinecraftTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomTest {

    public void testAll() {
        for (Method declaredMethod : getClass().getDeclaredMethods()) {
            if (!declaredMethod.isAnnotationPresent(MinecraftTest.class)) continue;
            try {
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
