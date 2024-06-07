package de.derioo.javautils.paper;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static de.derioo.javautils.paper.ItemStackUtility.decodeItemStacksToBytes;
import static de.derioo.javautils.paper.ItemStackUtility.encodeItemStacksFromBytes;
import static de.derioo.javautils.paper.MaterialUtility.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MaterialUtilityTest {

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

    static Random random = new Random();

    @Test
    public void testRandomMaterial() {
        assertThat(getRandomMaterial(new Material[0])).isNull();
        assertThat(getRandomMaterial(new Material[]{Material.DIRT}))
                .isNotNull()
                .isEqualTo(Material.DIRT);
        assertThat(getRandomMaterial())
                .isIn(Arrays.stream(Material.values()).toList())
                .isNotNull();
        Set<Material> materials = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            materials.add(Material.values()[random.nextInt(Material.values().length - 1)]);
        }
        assertThat(getRandomMaterial(materials.toArray(new Material[]{})))
                .isIn(materials)
                .isNotNull();
    }

}
