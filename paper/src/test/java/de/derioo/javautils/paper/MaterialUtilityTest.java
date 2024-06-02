package de.derioo.javautils.paper;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.*;

import static de.derioo.javautils.paper.MaterialUtility.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MaterialUtilityTest {

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
