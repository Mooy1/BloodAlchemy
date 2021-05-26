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
            Material.REDSTONE,
            "&4Blood",
            "&7Gleaming with power"
    );

    public static final SlimefunItemStack ESSENCE_OF_BLOOD = new SlimefunItemStack(
            "ESSENCE_OF_BLOOD",
            Material.RED_DYE,
            "&cEssence of Blood",
            "&7Contains the pure power of blood"
    );

    public static final SlimefunItemStack ESSENCE_OF_HARVEST = new SlimefunItemStack(
            "ESSENCE_OF_HARVEST",
            Material.YELLOW_DYE,
            "&eEssence of Harvest",
            "&7Contains the pure power of harvest"
    );

    public static final SlimefunItemStack ESSENCE_OF_GROWTH = new SlimefunItemStack(
            "ESSENCE_OF_GROWTH",
            Material.LIME_DYE,
            "&aEssence of Growth",
            "&7Contains the pure power of growth"
    );

    public static void setup(@Nonnull BloodHarvest plugin, @Nonnull Category category) {
        new SlimefunItem(category, BLOOD, SacrificialDagger.TYPE, new ItemStack[] {
                new CustomItem(Material.ZOMBIE_HEAD, "&cKill any mob"),
                null, null, null, null, null, null, null, null
        }).register(plugin);


    }

}
