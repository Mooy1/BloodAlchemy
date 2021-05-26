package io.github.mooy1.bloodalchemy.implementation;

import java.util.Arrays;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.blocks.altar.BloodAltar;
import io.github.mooy1.bloodalchemy.implementation.tools.SacrificialDagger;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Items added in this addon
 */
@UtilityClass
public final class Items {

    public static final SlimefunItemStack BLOOD_GEM = new SlimefunItemStack(
            "BLOOD_GEM",
            Material.RED_DYE,
            "&cBlood Gem",
            "&7Infused with the power of blood"
    );

    public static final SlimefunItemStack BLOOD = new SlimefunItemStack(
            "BLOOD",
            Material.REDSTONE,
            "&4Blood",
            "&7Gleaming with power"
    );

    public static void setup(@Nonnull BloodAlchemy plugin, @Nonnull Category category) {
        new SlimefunItem(category, BLOOD, SacrificialDagger.TYPE, Arrays.copyOf(new ItemStack[] {
                new CustomItem(Material.ZOMBIE_HEAD, "&cKill any mob")
        }, 9)).register(plugin);

        new SlimefunItem(category, BLOOD_GEM, BloodAltar.TYPE, Arrays.copyOf(new ItemStack[] {
                new ItemStack(Material.DIAMOND, 64), new SlimefunItemStack(BLOOD, 64)
        }, 9));
    }

}
