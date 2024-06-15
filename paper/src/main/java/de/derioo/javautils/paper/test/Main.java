package de.derioo.javautils.paper.test;

import de.derioo.javautils.common.MathUtility;
import de.derioo.javautils.paper.test.custom.CustomTest;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.time.Duration;
import java.util.logging.Level;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        String unitTest = System.getenv("UNIT_TEST");
        if (unitTest == null || !unitTest.equals("false")) {
            runTests();
            return;
        }

        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private void runTests() {
        try {
            Reflections reflections = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage("de.derioo.javautils.paper.test"))
                    .setScanners(Scanners.SubTypes));
            reflections.getSubTypesOf(CustomTest.class).forEach(clazz -> {
                try {
                    Duration took = MathUtility.computeTimeTaking(() -> {
                        CustomTest instance = clazz.getDeclaredConstructor().newInstance();
                        instance.testAll();
                    });
                    getLogger().log(Level.INFO, clazz.getSimpleName() + ".class passed in (" + took.toMillis() + " ms)");
                } catch (Throwable e) {
                    System.exit(1);
                    throw new RuntimeException(e);
                }
            });

            getLogger().log(Level.INFO, "------------");
            getLogger().log(Level.INFO, "All tests passed");
        } catch (Throwable e) {
            getLogger().log(Level.SEVERE, "Doesnt work :(", e);
            System.exit(1);
        }
        Bukkit.shutdown();
        return;
    }

}
