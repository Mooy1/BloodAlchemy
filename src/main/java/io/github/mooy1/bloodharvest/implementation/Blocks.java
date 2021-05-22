package io.github.mooy1.bloodharvest.implementation;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.blocks.altar.BloodAltar;
import io.github.mooy1.bloodharvest.implementation.blocks.BloodCollector;
import io.github.mooy1.bloodharvest.implementation.blocks.altar.InfusedBloodAltar;
import io.github.mooy1.infinitylib.presets.LorePreset;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * Blocks added in this addon
 */
@UtilityClass
public final class Blocks {

    public static final SlimefunItemStack BLOOD_ALTAR = new SlimefunItemStack(
            "BLOOD_ALTAR",
            Material.ENCHANTING_TABLE,
            "&cBlood Altar",
            "&7Used to create and infuse items with blood"
    );

    public static final SlimefunItemStack INFUSED_BLOOD_ALTAR = new SlimefunItemStack(
            "INFUSED_BLOOD_ALTAR",
            Material.ENCHANTING_TABLE,
            "&cInfused Blood Altar",
            "&7Used to automatically create and infuse items with blood"
    );

    public static final SlimefunItemStack BLOOD_COLLECTOR = new SlimefunItemStack(
            "BLOOD_COLLECTOR",
            Material.CAULDRON,
            "&cBlood Collector",
            "&7Collects blood from dying creatures above",
            "&7Automatically outputs blood to inventories below",
            "",
            LorePreset.speed(1)
    );

    public static final SlimefunItemStack INFUSED_BLOOD_COLLECTOR = new SlimefunItemStack(
            "INFUSED_BLOOD_COLLECTOR",
            Material.CAULDRON,
            "&cBlood Collector",
            "&7Collects blood from dying creatures above",
            "&7Automatically outputs blood to inventories below",
            "",
            LorePreset.speed(8)
    );

    public static void setup(@Nonnull BloodHarvest plugin, @Nonnull Category category) {

        new BloodAltar(category, BLOOD_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }).register(plugin);

        new InfusedBloodAltar(category, INFUSED_BLOOD_ALTAR, BloodAltar.TYPE, new ItemStack[] {

        }).register(plugin);

        new BloodCollector(category, BLOOD_COLLECTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }, 1).register(plugin);

        new BloodCollector(category, INFUSED_BLOOD_COLLECTOR, BloodAltar.TYPE, new ItemStack[] {

        }, 8).register(plugin);
    }

}
