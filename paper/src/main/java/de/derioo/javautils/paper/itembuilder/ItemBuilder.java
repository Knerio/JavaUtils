package de.derioo.javautils.paper.itembuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder extends BaseItemBuilder<ItemBuilder, ItemMeta> {

    /**
     * Constructs a new {@link ItemBuilder} based on an {@link ItemStack}
     * @param itemStack the {@link ItemStack}
     */
    public ItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Constructs a new {@link ItemBuilder} based on a {@link Material}
     * @param material the {@link Material}
     */
    public ItemBuilder(Material material) {
        super(material);
    }

    /**
     * Constructs a new {@link ItemBuilder} based on a {@link Material} and an amount
     * @param material the {@link Material}
     * @param amount the amount for the new {@link ItemStack}
     */
    public ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    /**
     * Constructs a new {@link ItemBuilder} based on a {@link Material} and an amount and an {@link ItemMeta}
     * @param material the {@link Material}
     * @param amount the amount for the new {@link ItemStack}
     * @param meta the meta for the new {@link ItemMeta}
     */
    public ItemBuilder(Material material, int amount, ItemMeta meta) {
        super(material, amount, meta);
    }


}
