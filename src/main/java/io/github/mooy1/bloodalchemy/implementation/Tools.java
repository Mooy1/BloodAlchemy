package io.github.mooy1.bloodalchemy.implementation;

import java.util.Arrays;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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

    public static final SlimefunItemStack TEST_POTION = new SlimefunItemStack(
            "TEST_POTION",
            Material.POTION,
            "&4Test Potion",
            meta -> {
                PotionMeta potion = (PotionMeta) meta;
                potion.setColor(Color.RED);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 1), true);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 1), true);
            }
    );

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

        new SacrificialDagger(category, SACRIFICIAL_DAGGER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
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
