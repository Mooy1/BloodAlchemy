package io.github.mooy1.bloodharvest.implementation;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.tools.SacrificialDagger;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Items added in this addon
 */
@UtilityClass
public final class Items {

    public static final SlimefunItemStack BLOOD = new SlimefunItemStack(
            "BLOOD",
            Material.RED_DYE,
            "&cBlood",
            "&7Gleaming with untapped power"
    );

    public static final SlimefunItemStack BLOOD_ESSENCE = new SlimefunItemStack(
            "BLOOD_ESSENCE",
            Material.RED_DYE,
            "&cBlood Essence",
            "&7Contains the power of blood"
    );

    public static final SlimefunItemStack HARVEST_ESSENCE = new SlimefunItemStack(
            "HARVEST_ESSENCE",
            Material.YELLOW_DYE,
            "&eHarvest Essence",
            "&7Contains the power of harvest"
    );

    public static final SlimefunItemStack GROWTH_ESSENCE = new SlimefunItemStack(
            "GROWTH_ESSENCE",
            Material.LIME_DYE,
            "&aGrowth Essence",
            "&7Contains the power of growth"
    );

    public static void setup(@Nonnull BloodHarvest plugin, @Nonnull Category category) {

        new SlimefunItem(category, BLOOD, SacrificialDagger.TYPE, new ItemStack[] {
                new CustomItem(Material.ZOMBIE_HEAD, "&cKill any mob"),
                null, null, null, null, null, null, null, null
        }).register(plugin);
        /*
        new SlimefunItem(category, BLOOD_ESSENCE, , new ItemStack[] {
                null, null, null, null, null, null, null, null, null
        }).register(plugin);

        new SlimefunItem(category, GROWTH_ESSENCE, , new ItemStack[] {
                null, null, null, null, null, null, null, null, null
        }).register(plugin);

        new SlimefunItem(category, HARVEST_ESSENCE, , new ItemStack[] {
                null, null, null, null, null, null, null, null, null
        }).register(plugin);
        */
    }

}
