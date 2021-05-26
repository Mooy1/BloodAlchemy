package io.github.mooy1.bloodalchemy.implementation;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.blocks.BloodHopper;
import io.github.mooy1.bloodalchemy.implementation.blocks.altar.BloodAltar;
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
            "&7Used to create and infuse items with blood",
            "&7Right-Click to activate"
    );

    public static final SlimefunItemStack BLOOD_HOPPER = new SlimefunItemStack(
            "BLOOD_HOPPER",
            Material.HOPPER,
            "&cBlood Hopper",
            "&7Collects blood from dying creatures above",
            "",
            "&cChance: 10%"
    );

    public static final SlimefunItemStack INFUSED_BLOOD_HOPPER = new SlimefunItemStack(
            "INFUSED_BLOOD_HOPPER",
            Material.HOPPER,
            "&cBlood Hopper",
            "&7Collects blood from dying creatures above",
            "",
            "&cChance: 80%"
    );

    public static void setup(@Nonnull BloodAlchemy plugin, @Nonnull Category category) {

        new BloodAltar(category, BLOOD_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }).register(plugin);

        new BloodHopper(category, BLOOD_HOPPER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }, 10).register(plugin);

        new BloodHopper(category, INFUSED_BLOOD_HOPPER, BloodAltar.TYPE, new ItemStack[] {

        }, 80).register(plugin);

    }

}
