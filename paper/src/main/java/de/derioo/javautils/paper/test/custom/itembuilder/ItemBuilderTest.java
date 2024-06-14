package de.derioo.javautils.paper.test.custom.itembuilder;

import de.derioo.javautils.paper.itembuilder.ItemBuilder;
import de.derioo.javautils.paper.itembuilder.SkullBuilder;
import de.derioo.javautils.paper.test.custom.CustomTest;
import de.derioo.javautils.paper.test.custom.annotation.Test;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.SkullMeta;

import static org.assertj.core.api.Assertions.*;

public class ItemBuilderTest extends CustomTest {

    @Test
    public void testItemBuilder() throws Exception {
        assertThat(new ItemBuilder(Material.DIRT).lore(Component.text("")).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().lore().size()).isEqualTo(1);
                });
        assertThat(new ItemBuilder(Material.DIRT).lore(Component.text(""), Component.text("")).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().lore().size()).isEqualTo(2);
                });
        assertThat(new ItemBuilder(Material.DIRT).amount(20).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getAmount()).isEqualTo(20);
                });
        assertThat(new ItemBuilder(Material.DIRT).enchant(Enchantment.UNBREAKING, 3, false).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().getEnchants().get(Enchantment.UNBREAKING)).isEqualTo(3);
                });
        assertThat(new ItemBuilder(Material.DIRT).enchant(Enchantment.UNBREAKING).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().getEnchants().get(Enchantment.UNBREAKING)).isEqualTo(1);
                });
        assertThat(new ItemBuilder(Material.DIRT).glow().build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().getEnchants()).containsEntry(Enchantment.MENDING, 1);
                    assertThat(itemStack.getItemMeta().getItemFlags()).contains(ItemFlag.HIDE_ENCHANTS);
                });
        assertThat(new ItemBuilder(Material.DIRT).glow().glow(false).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().getEnchants()).doesNotContainEntry(Enchantment.MENDING, 1);
                    assertThat(itemStack.getItemMeta().getItemFlags()).doesNotContain(ItemFlag.HIDE_ENCHANTS);
                });
        assertThat(new ItemBuilder(Material.DIRT).flags(ItemFlag.HIDE_DYE).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().getItemFlags()).contains(ItemFlag.HIDE_DYE);
                });
        assertThat(new ItemBuilder(Material.DIRT).flags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS).removeFlags(ItemFlag.HIDE_DYE).build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().getItemFlags()).contains(ItemFlag.HIDE_ENCHANTS);
                });
        assertThat(new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.AQUA_AFFINITY).enchant(Enchantment.MENDING))
                .satisfies(builder -> {
                    assertThat(builder.clone().build().getItemMeta().getEnchants())
                            .containsEntry(Enchantment.AQUA_AFFINITY, 1)
                            .containsEntry(Enchantment.MENDING, 1);


                    assertThat(builder.clone().disenchant(Enchantment.MENDING).build().getItemMeta().getEnchants())
                            .containsEntry(Enchantment.AQUA_AFFINITY, 1);

                    assertThat(builder.clone().disenchant(Enchantment.AQUA_AFFINITY).disenchant().build().getItemMeta().getEnchants()).isEmpty();

                });
        assertThat(new ItemBuilder(Material.DIAMOND_SWORD).unbreakable().build())
                .satisfies(itemStack -> {
                    assertThat(itemStack.getItemMeta().isUnbreakable()).isTrue();
                });
    }

    @Test
    public void testLoreIfs() {
        assertThat(new ItemBuilder(Material.DIRT).lore(Component.text("1")).addLoreIf(() -> true, Component.text("asd")).build())
                .satisfies(item -> {
                    assertThat(item.getItemMeta().lore().size()).isEqualTo(2);
                });
        assertThat(new ItemBuilder(Material.DIRT).lore(Component.text("1")).addLoreIf(() -> false, Component.text("asd")).build())
                .satisfies(item -> {
                    assertThat(item.getItemMeta().lore().size()).isEqualTo(1);
                });

        assertThat(new ItemBuilder(Material.DIRT).loreIf(() -> true, Component.text("a")).addLore(Component.text("asd")).build())
                .satisfies(item -> {
                    assertThat(item.getItemMeta().lore().size()).isEqualTo(2);
                });
        assertThat(new ItemBuilder(Material.DIRT).loreIf(() -> false, Component.text("a")).addLore(Component.text("asd")).build())
                .satisfies(item -> {
                    assertThat(item.getItemMeta().lore().size()).isEqualTo(1);
                });
    }

    @Test
    public void testSkullBuilder() {
        assertThatNoException().isThrownBy(() -> new SkullBuilder(Material.ZOMBIE_WALL_HEAD));
        assertThatNoException().isThrownBy(() -> new SkullBuilder(Material.PLAYER_HEAD));
        assertThatNoException().isThrownBy(() -> new SkullBuilder(Material.PIGLIN_WALL_HEAD));
        assertThatThrownBy(() -> new SkullBuilder(Material.DIRT)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new SkullBuilder(Material.PISTON_HEAD)).isInstanceOf(IllegalArgumentException.class);
        assertThat(new SkullBuilder(Material.PLAYER_HEAD)
                .texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGUxYjk5ODlkZjYyNzA2NTYwZWJjMWExNzM1OTk0Y2FjNzEzMGZjMDY3Yzc5MmNiM2VjNTU0NDk2NDFmZmZiZiJ9fX0=")
                .build())
                .satisfies(item -> {
                    SkullMeta meta = ((SkullMeta) item.getItemMeta());
                    assertThat(meta.getPlayerProfile()
                            .getTextures()
                            .getSkin()
                            .toString())
                            .isEqualTo("http://textures.minecraft.net/texture/de1b9989df62706560ebc1a1735994cac7130fc067c792cb3ec55449641fffbf");
                });

    }

}
