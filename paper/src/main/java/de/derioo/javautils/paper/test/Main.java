package de.derioo.javautils.paper.test;

import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Log
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            long start = System.currentTimeMillis();
            new ItemStackTest();
            log.log(Level.FINE, "ItemstackTest passed in (" + (System.currentTimeMillis() - start) + " ms)");
            log.log(Level.FINE, "------------");
            log.log(Level.FINE, "All tests passed");
            throw new RuntimeException("Test");
//            Bukkit.shutdown();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Doesnt work :(");
            System.exit(1);
        }

    }
}
