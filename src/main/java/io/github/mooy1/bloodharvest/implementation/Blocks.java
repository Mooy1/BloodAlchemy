package io.github.mooy1.bloodharvest.implementation;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.blocks.AlchemyCauldron;
import io.github.mooy1.bloodharvest.implementation.blocks.BloodAltar;
import io.github.mooy1.bloodharvest.implementation.blocks.BloodHopper;
import io.github.mooy1.infinitylib.presets.LorePreset;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * Blocks added in this addon
 */
@UtilityClass
public final class Blocks {

    public static final SlimefunItemStack ALCHEMY_CAULDRON = new SlimefunItemStack(
            "ALCHEMY_CAULDRON",
            Material.CAULDRON,
            "&5Alchemy Cauldron",
            "&7Used to combine blood and essence into powerful potions"
    );

    public static final SlimefunItemStack BLOOD_ALTAR = new SlimefunItemStack(
            "BLOOD_ALTAR",
            Material.ENCHANTING_TABLE,
            "&cBlood Altar",
            "&7Used to create and infuse items with blood, right click to activate"
    );

    public static final SlimefunItemStack INFUSED_BLOOD_ALTAR = new SlimefunItemStack(
            "INFUSED_BLOOD_ALTAR",
            Material.ENCHANTING_TABLE,
            "&cInfused Blood Altar",
            "&7Used to automatically create and infuse items with blood"
    );

    public static final SlimefunItemStack BLOOD_HOPPER = new SlimefunItemStack(
            "BLOOD_HOPPER",
            Material.HOPPER,
            "&cBlood Hopper",
            "&7Collects blood from dying creatures above",
            "",
            LorePreset.speed(1)
    );

    public static final SlimefunItemStack INFUSED_BLOOD_HOPPER = new SlimefunItemStack(
            "INFUSED_BLOOD_HOPPER",
            Material.HOPPER,
            "&cBlood Hopper",
            "&7Collects blood from dying creatures above",
            "",
            LorePreset.speed(8)
    );

    public static void setup(@Nonnull BloodHarvest plugin, @Nonnull Category category) {

        new BloodAltar(category, BLOOD_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }).register(plugin);

        new BloodHopper(category, BLOOD_HOPPER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }, 1).register(plugin);

        new BloodHopper(category, INFUSED_BLOOD_HOPPER, BloodAltar.TYPE, new ItemStack[] {

        }, 8).register(plugin);

        new AlchemyCauldron(category, ALCHEMY_CAULDRON, BloodAltar.TYPE, new ItemStack[] {

        }).register(plugin);
    }

}
