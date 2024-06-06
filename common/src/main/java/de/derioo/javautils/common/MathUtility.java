package de.derioo.javautils.common;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@UtilityClass
public class MathUtility {

    @FunctionalInterface
    public interface ThrowableRunnable {

        void run() throws Throwable;

    }

    public Duration computeTimeTaking(@NotNull ThrowableRunnable runnable) throws Throwable {
        long start = System.currentTimeMillis();
        runnable.run();
        return Duration.ofMillis(System.currentTimeMillis() - start);
    }

}
