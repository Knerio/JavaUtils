package de.derioo.javautils.paper.test;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            new ItemStackTest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Bukkit.shutdown();
    }
}
