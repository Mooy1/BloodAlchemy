package io.github.mooy1.bloodharvest.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;

/**
 * Utility methods for this addon
 */
@UtilityClass
public final class Util {

    /**
     * An instance of Redstone block data for creating blood particles
     */
    private static final BlockData BLOOD = Material.REDSTONE_BLOCK.createBlockData();

    /**
     * Creates blood particles at the specified location with the specified amount
     */
    public static void spawnBloodParticles(@Nonnull Location l, int count) {
        l.getWorld().spawnParticle(Particle.BLOCK_CRACK, l, count, .4, .4, .4, BLOOD);
    }

    /**
     * Plays a blood noise for the player
     */
    public static void playBloodNoise(@Nonnull Location l) {
        l.getWorld().playSound(l, Sound.ENTITY_MAGMA_CUBE_SQUISH, 1, 1);
    }

    /**
     * @return A 'level' determined by the 'growth'
     */
    public static int getGrowthLevel(int growth, double base, double increase) {
        return 1 + (int) (Math.log(growth / base) / Math.log(increase));
    }

    /**
     * @return The needed 'growth' to reach the specified 'level'
     */
    public static int getNeededGrowth(int currentLevel, double base, double increase) {
        return (int) (base * Math.pow(increase, currentLevel));
    }

    @Nonnull
    public static SlimefunItemStack createPotion(Color color, String name, String description, PotionEffect... effects) {
        return new SlimefunItemStack(
                ChatColor.stripColor(ChatColors.color(name)).toUpperCase(Locale.ROOT) + "_POTION",
                Material.POTION,
                name + " Potion",
                meta -> {
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add(description);
                    meta.setLore(lore);
                    PotionMeta potion = (PotionMeta) meta;
                    potion.setColor(color);
                    for (PotionEffect effect : effects) {
                        potion.addCustomEffect(effect, true);
                    }
                }
        );
    }

}
