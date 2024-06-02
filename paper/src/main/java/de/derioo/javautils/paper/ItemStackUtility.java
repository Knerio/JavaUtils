package de.derioo.javautils.paper;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@UtilityClass
public class ItemStackUtility {

    /**
     * Used to convert <b>multiple</b> {@link ItemStack}s into
     * a {@link Base64} string
     *
     * @param itemStacks the items
     * @return the base64 string
     * @throws IOException any exception while writing
     */
    public String decodeItemStacksToString(final ItemStack @NotNull [] itemStacks) throws IOException {
        return Base64.getEncoder().encodeToString(decodeItemStacksToBytes(itemStacks));
    }

    /**
     * Used to convert <b>multiple</b> {@link ItemStack}s into
     * a {@link Byte} array
     *
     * @param itemStacks the items
     * @return the {@link Byte} array
     * @throws IOException any exception while writing
     */
    public byte[] decodeItemStacksToBytes(final ItemStack @NotNull [] itemStacks) throws IOException {
        final ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(itemStacks.length);
        for (ItemStack current : itemStacks) {
            if (current == null) current = new ItemStack(Material.AIR);

            byte[] toWrite = current.serializeAsBytes();
            stream.write(toWrite.length);
            stream.write(toWrite);
        }
        return stream.toByteArray();
    }

    /**
     * Use to convert the  {@link ItemStackUtility#decodeItemStacksToString(ItemStack[]) encoded string}
     * back to the {@link ItemStack}s
     *
     * @param encoded the {@link ItemStackUtility#decodeItemStacksToString(ItemStack[]) items as a string}
     * @return the original {@link ItemStack}s
     * @throws IOException any exception while reading
     */
    public ItemStack[] encodeItemStacksFromString(final String encoded) throws IOException {
        final byte[] bytes = Base64.getDecoder().decode(encoded);
        return encodeItemStacksFromBytes(bytes);
    }

    /**
     * Use to convert the {@link ItemStackUtility#decodeItemStacksToBytes(ItemStack[])  encoded bytes}
     * back to the {@link ItemStack}s
     *
     * @param encoded the {@link ItemStackUtility#decodeItemStacksToBytes(ItemStack[]) items as bytes}
     * @return the original {@link ItemStack}s
     * @throws IOException any exception while reading
     */
    public ItemStack[] encodeItemStacksFromBytes(final byte[] encoded) throws IOException {
        final ByteArrayInputStream stream = new ByteArrayInputStream(encoded);
        final int length = stream.read();

        final ItemStack[] array = new ItemStack[length];
        for (int i = 0; i < length; i++) {
            int byteLength = stream.read();
            array[i] = ItemStack.deserializeBytes(stream.readNBytes(byteLength));
        }
        return array;
    }

}
