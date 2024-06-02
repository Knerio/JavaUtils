package de.derioo.javautils.paper;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.*;

import static de.derioo.javautils.paper.ItemStackUtility.*;
import static org.assertj.core.api.Assertions.assertThat;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        try {
            ItemStack[] itemStacks = new ItemStack[]{new ItemStack(Material.DIAMOND), new ItemStack(Material.DIRT)};
            byte[] bytes = decodeItemStacksToBytes(itemStacks);
            assertThat(bytes).isNotNull();
            assertThat(encodeItemStacksFromBytes(bytes)).isEqualTo(itemStacks);
            assertThat(encodeItemStacksFromBytes(decodeItemStacksToBytes(new ItemStack[]{itemStacks[0]}))).isEqualTo(new ItemStack[]{itemStacks[0]});
            assertThat(false).isTrue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Bukkit.shutdown();
    }
}
