package de.derioo.javautils.paper;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static de.derioo.javautils.paper.ItemStackUtility.*;
import static org.assertj.core.api.Assertions.*;

public class ItemStackUtilityTest {

    private ServerMock server;
    @BeforeEach
    public void setUp()
    {
        server = MockBukkit.mock();
    }

    @AfterEach
    public void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    public void testItemStackDecodingAndEncoding() {
        ItemStack[] itemStacks = new ItemStack[]{new ItemStack(Material.DIAMOND), new ItemStack(Material.DIRT)};
        try {
            byte[] bytes = decodeItemStacksToBytes(itemStacks);
            assertThat(bytes).isNotNull();
            assertThat(encodeItemStacksFromBytes(bytes)).isEqualTo(itemStacks);
            assertThat(encodeItemStacksFromBytes(decodeItemStacksToBytes(new ItemStack[]{itemStacks[0]}))).isEqualTo(new ItemStack[]{itemStacks[0]});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
