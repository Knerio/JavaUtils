package de.derioo.javautils.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class MathUtilityTest {

    MathUtility.ThrowableRunnable runnable = () -> {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };

    @Test
    public void testTimeTaking() throws Throwable {
        long start = System.currentTimeMillis();
        runnable.run();
        long time = System.currentTimeMillis() - start;
        Duration duration = Duration.ofMillis(time);
        Assertions.assertThat(MathUtility.computeTimeTaking(runnable)).isCloseTo(duration, Duration.ofMillis(20));
    }

}
