package io.github.mooy1.bloodharvest.implementation;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.blocks.altar.BloodAltar;
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

    public static final SlimefunItemStack GROWTH_ESSENCE = new SlimefunItemStack(
            "GROWTH_ESSENCE",
            Material.LIME_DYE,
            "&aGrowth Essence",
            "&7The essence of a growing organism"
    );

    public static final SlimefunItemStack HARVEST_ESSENCE = new SlimefunItemStack(
            "HARVEST_ESSENCE",
            Material.YELLOW_DYE,
            "&eHarvest Essence",
            "&7The essence of a harvested plant"
    );

    public static final SlimefunItemStack TERRA_ESSENCE = new SlimefunItemStack(
            "TERRA_ESSENCE",
            Material.BROWN_DYE,
            "&6Terra Essence",
            "&7The essence of the earth"
    );

    public static final SlimefunItemStack DEEP_ESSENCE = new SlimefunItemStack(
            "TERRA_ESSENCE",
            Material.GRAY_DYE,
            "&8Deep Essence",
            "&7The essence of the deep"
    );

    public static final SlimefunItemStack BLOOD = new SlimefunItemStack(
            "BLOOD",
            Material.RED_DYE,
            "&cBlood",
            "&7Gleaming with potential power"
    );

    public static final SlimefunItemStack INFUSED_BLOOD = new SlimefunItemStack(
            "INFUSED_BLOOD",
            Material.RED_DYE,
            "&cInfused Blood",
            "&7Gleaming with power, infused with essence"
    );

    public static void setup(@Nonnull BloodHarvest plugin, @Nonnull Category category) {
        new SlimefunItem(category, BLOOD, SacrificialDagger.TYPE, new ItemStack[] {
                null, null, null,
                null, new CustomItem(Material.ZOMBIE_HEAD, "&7Any Mob"), null,
                null, null, null
        }).register(plugin);

        new SlimefunItem(category, INFUSED_BLOOD, BloodAltar.TYPE, new ItemStack[] {
                BLOOD, DEEP_ESSENCE, GROWTH_ESSENCE, HARVEST_ESSENCE, TERRA_ESSENCE
        }).register(plugin);

    }


}
