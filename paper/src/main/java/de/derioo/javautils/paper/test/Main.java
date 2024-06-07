package de.derioo.javautils.paper.test;

import de.derioo.javautils.common.MathUtility;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            getLogger().log(Level.INFO, "ItemstackTest passed in (" + (MathUtility.computeTimeTaking(() -> new ItemStackUtilityTest().testAll()).toMillis()) + " ms)");
            getLogger().log(Level.INFO, "------------");
            getLogger().log(Level.INFO, "All tests passed");
        } catch (Throwable e) {
            getLogger().log(Level.SEVERE, "Doesnt work :(", e);
            System.exit(1);
        }
        Bukkit.shutdown();
    }

}
