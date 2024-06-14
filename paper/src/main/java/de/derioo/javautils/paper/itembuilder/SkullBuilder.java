package de.derioo.javautils.paper.itembuilder;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.assertj.core.api.Assertions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.assertj.core.api.Assertions.assertThat;

public class SkullBuilder extends BaseItemBuilder<SkullBuilder, SkullMeta> {
    /**
     * Constructs a new {@link SkullBuilder} based on an {@link ItemStack}
     * @param itemStack the {@link ItemStack}
     */
    public SkullBuilder(ItemStack itemStack) {
        super(itemStack);
        if (isNonValidSkull()) throw new IllegalArgumentException("ItemStack is no skull");
    }

    /**
     * Constructs a new {@link SkullBuilder} based on a {@link Material}
     * @param material the {@link Material}
     */
    public SkullBuilder(Material material) {
        super(material);
        if (isNonValidSkull()) throw new IllegalArgumentException("ItemStack is no skull");
    }

    /**
     * Constructs a new {@link SkullBuilder} based on a {@link Material} and an amount
     * @param material the {@link Material}
     * @param amount the amount for the new {@link ItemStack}
     */
    public SkullBuilder(Material material, int amount) {
        super(material, amount);
        if (isNonValidSkull()) throw new IllegalArgumentException("ItemStack is no skull");
    }

    /**
     * Constructs a new {@link SkullBuilder} based on a {@link Material} and an amount and an {@link ItemMeta}
     * @param material the {@link Material}
     * @param amount the amount for the new {@link ItemStack}
     * @param meta the meta for the new {@link ItemMeta}
     */
    public SkullBuilder(Material material, int amount, ItemMeta meta) {
        super(material, amount, meta);
        if (isNonValidSkull()) throw new IllegalArgumentException("ItemStack is no skull");
    }

    /**
     * Sets the {@link PlayerProfile} for the {@link ItemStack}
     * @param profile the new {@link PlayerProfile}
     * @return this to make chain call
     * @see SkullMeta#setPlayerProfile(PlayerProfile)
     */
    public SkullBuilder playerProfile(PlayerProfile profile) {
        Preconditions.checkNotNull(profile, "profile");
        return editMeta(skullMeta -> skullMeta.setPlayerProfile(profile));
    }

    /**
     * Sets the owning {@link OfflinePlayer} of the skull
     * @param player the {@link OfflinePlayer}
     * @return this to make chain calls
     * @see SkullMeta#setOwningPlayer(OfflinePlayer)
     */
    public SkullBuilder owningPlayer(OfflinePlayer player) {
        checkNotNull(player, "player");
        return editMeta(meta -> meta.setOwningPlayer(player));
    }

    public SkullBuilder setSkullTextures(UUID uuid) {
        return editMeta(skullMeta -> {
            try {
                skullMeta.setPlayerProfile(Bukkit.createProfile(uuid).update().get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }


    /**
     * Sets skull textures
     * @param texture the base64 decoded string
     * @return this to make chain calls
     */
    public SkullBuilder texture(String texture) {
        Preconditions.checkNotNull(texture, "texture");
        return getMeta(meta -> {
            final PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), "");
            final PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(URI.create(getUrl(texture)).toURL());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            profile.setTextures(textures);
            playerProfile(profile);
        });
    }


    public boolean isNonValidSkull() {
        return !List.of(
                Material.CREEPER_HEAD,
                Material.DRAGON_HEAD,
                Material.CREEPER_WALL_HEAD,
                Material.PIGLIN_HEAD,
                Material.DRAGON_WALL_HEAD,
                Material.PLAYER_WALL_HEAD,
                Material.PIGLIN_WALL_HEAD,
                Material.ZOMBIE_HEAD,
                Material.ZOMBIE_WALL_HEAD,
                Material.PLAYER_HEAD
        ).contains(material());
    }

    public String getUrl(String encodedData) {
        checkNotNull(encodedData, "texture");
        String decoded = new String(Base64.getDecoder().decode(encodedData));
        JsonObject object = JsonParser.parseString(decoded).getAsJsonObject();
        return object.getAsJsonObject("textures").getAsJsonObject("SKIN").get("url").getAsString();
    }


}
