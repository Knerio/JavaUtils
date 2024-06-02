package de.derioo.javautils.paper.test;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

import static de.derioo.javautils.paper.ItemStackUtility.decodeItemStacksToBytes;
import static de.derioo.javautils.paper.ItemStackUtility.encodeItemStacksFromBytes;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemStackTest {

    public ItemStackTest() throws IOException {
        ItemStack[] itemStacks = new ItemStack[]{new ItemStack(Material.DIAMOND), new ItemStack(Material.DIRT)};
        byte[] bytes = decodeItemStacksToBytes(itemStacks);
        assertThat(bytes).isNotNull();
        assertThat(encodeItemStacksFromBytes(bytes)).isEqualTo(itemStacks);
        assertThat(encodeItemStacksFromBytes(decodeItemStacksToBytes(new ItemStack[]{itemStacks[0]}))).isEqualTo(new ItemStack[]{itemStacks[0]});
    }
}
