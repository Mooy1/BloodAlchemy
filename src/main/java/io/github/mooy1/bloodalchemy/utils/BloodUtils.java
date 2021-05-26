package io.github.mooy1.bloodalchemy.utils;

import java.util.List;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.bloodalchemy.BloodAlchemy;

/**
 * Utility methods for blood related things
 */
@UtilityClass
public final class BloodUtils {

    /**
     * The maximum amount of blood an item can store
     */
    public static final int MAX_STORED = 100;

    /**
     * The NamespacedKey for storing blood on an item in persistent data
     */
    private static final NamespacedKey STORED_KEY = BloodAlchemy.inst().getKey("stored_blood");

    /**
     * An instance of Redstone block data for creating blood particles
     */
    private static final BlockData PARTICLE_BLOCK_DATA = Material.REDSTONE_BLOCK.createBlockData();

    /**
     * The start of the line of lore that shows stored blood
     */
    private static final String STORED_LORE_START = ChatColor.DARK_RED + "Blood: " + ChatColor.RED;

    /**
     * Creates blood particles at the specified location with the specified amount
     */
    public static void spawnParticles(@Nonnull Location l, int count, int radius) {
        l.getWorld().spawnParticle(Particle.BLOCK_CRACK, l, count, radius, radius, radius, PARTICLE_BLOCK_DATA);
    }

    /**
     * Plays a blood noise for the player
     */
    public static void playSound(@Nonnull Location l) {
        l.getWorld().playSound(l, Sound.ENTITY_MAGMA_CUBE_SQUISH, 3, 1);
    }

    /**
     * Creates a line of lore which shows how much blood is stored out of the max
     */
    public static String getStoredLore(int stored) {
        return STORED_LORE_START + stored + ChatColor.DARK_RED + " / " + ChatColor.RED + MAX_STORED;
    }

    /**
     * Gets the amount of stored blood in an item
     */
    public static int getStored(@Nonnull ItemMeta meta) {
        return meta.getPersistentDataContainer().getOrDefault(STORED_KEY, PersistentDataType.INTEGER, 0);
    }

    /**
     * Sets the amount of stored blood in a an item and updates lore
     */
    public static void setStored(@Nonnull ItemMeta meta, int stored) {
        meta.getPersistentDataContainer().set(STORED_KEY, PersistentDataType.INTEGER, stored);

        if (meta.hasLore()) {
            List<String> lore = meta.getLore();

            // Find the blood lore line and replace it
            for (int i = 0 ; i < lore.size() ; i++) {
                if (lore.get(i).startsWith(STORED_LORE_START)) {
                    lore.set(i, getStoredLore(stored));
                    meta.setLore(lore);
                    break;
                }
            }
        }
    }

}
