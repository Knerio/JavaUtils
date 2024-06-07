package de.derioo.javautils.paper.test;

import de.derioo.javautils.paper.test.custom.CustomTest;
import de.derioo.javautils.paper.test.custom.annotation.MinecraftTest;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

import static de.derioo.javautils.paper.ItemStackUtility.*;
import static org.assertj.core.api.Assertions.*;

public class ItemStackUtilityTest extends CustomTest {

    @MinecraftTest
    private void testItemStackDecodingAndEncoding() throws IOException {
        ItemStack[] itemStacks = new ItemStack[]{new ItemStack(Material.DIAMOND), new ItemStack(Material.DIRT)};
        byte[] bytes = decodeItemStacksToBytes(itemStacks);
        assertThat(bytes).isNotNull();
        assertThat(encodeItemStacksFromBytes(bytes)).isEqualTo(itemStacks);

        String s = decodeItemStacksToString(itemStacks);
        assertThat(s).isNotNull();
        assertThat(encodeItemStacksFromString(s)).isEqualTo(itemStacks);

        assertThat(encodeItemStacksFromBytes(new ItemStack(Material.DIRT).serializeAsBytes())[0]).isEqualTo(new ItemStack(Material.DIRT));

        assertThat(encodeItemStacksFromBytes(decodeItemStacksToBytes(new ItemStack[]{itemStacks[0]}))).isEqualTo(new ItemStack[]{itemStacks[0]});
    }

}
