package io.github.mooy1.bloodalchemy.implementation;

import java.util.Arrays;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.blocks.altar.BloodAltar;
import io.github.mooy1.bloodalchemy.implementation.tools.BloodTotem;
import io.github.mooy1.bloodalchemy.implementation.tools.SacrificialDagger;
import io.github.mooy1.bloodalchemy.implementation.tools.VampireBlade;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * Tools added in this addon
 */
@UtilityClass
public final class Tools {

    public static final SlimefunItemStack SACRIFICIAL_DAGGER = new SlimefunItemStack(
            "SACRIFICIAL_DAGGER",
            Material.IRON_SWORD,
            "&fSacrificial Dagger",
            "&7Collects blood from killing creatures",
            "&7Right-Click to collect blood from yourself"
    );

    public static final SlimefunItemStack BLOOD_TOTEM = new SlimefunItemStack(
            "BLOOD_TOTEM",
            Material.TOTEM_OF_UNDYING,
            "&4Blood Totem",
            "&7Becomes more powerful as you feed it blood",
            "",
            BloodUtils.getStoredLore(0)
    );

    public static final SlimefunItemStack VAMPIRE_BLADE = new SlimefunItemStack(
            "VAMPIRE_BLADE",
            Material.NETHERITE_SWORD,
            "&4Vampire Blade",
            "&7Becomes more powerful as you feed it blood",
            "",
            BloodUtils.getStoredLore(0)
    );

    public static void setup(@Nonnull BloodAlchemy plugin, @Nonnull Category category) {

        new SacrificialDagger(category, SACRIFICIAL_DAGGER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                null, SlimefunItems.SILVER_INGOT, SlimefunItems.SILVER_INGOT,
                null, SlimefunItems.SILVER_INGOT, null,
                null, new ItemStack(Material.STICK), null
        }).register(plugin);

        new VampireBlade(category, VAMPIRE_BLADE, BloodAltar.TYPE, Arrays.copyOf(new ItemStack[] {

        }, 9)).register(plugin);

        new BloodTotem(category, BLOOD_TOTEM, BloodAltar.TYPE, Arrays.copyOf(new ItemStack[] {
                new ItemStack(Material.TOTEM_OF_UNDYING),
                new SlimefunItemStack(Items.BLOOD_GEM, 8),
                new SlimefunItemStack(Items.BLOOD, 64)
        }, 9)).register(plugin);

    }

}
