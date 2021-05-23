package io.github.mooy1.bloodharvest.util;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;

/**
 * Utility methods for blood related thing
 */
@UtilityClass
public final class BloodUtil {

    /**
     * An instance of Redstone block data for creating blood particles
     */
    private static final BlockData BLOOD_BLOCK_DATA = Material.REDSTONE_BLOCK.createBlockData();

    /**
     * Creates blood particles at the specified location with the specified amount
     */
    public static void spawnParticles(@Nonnull Location l, int count) {
        l.getWorld().spawnParticle(Particle.BLOCK_CRACK, l, count, 1, 1, 1, BLOOD_BLOCK_DATA);
    }

    /**
     * Plays a blood noise for the player
     */
    public static void playSound(@Nonnull Location l) {
        l.getWorld().playSound(l, Sound.ENTITY_MAGMA_CUBE_SQUISH, 2, 1);
    }

}
