package io.github.mooy1.bloodharvest.implementation;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.blocks.AlchemyCauldron;
import io.github.mooy1.bloodharvest.implementation.blocks.BloodAltar;
import io.github.mooy1.bloodharvest.implementation.tools.BloodTotem;
import io.github.mooy1.bloodharvest.implementation.tools.SacrificialDagger;
import io.github.mooy1.bloodharvest.implementation.tools.VampireBlade;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * Tools added in this addon
 */
@UtilityClass
public final class Tools {

    public static final SlimefunItemStack TEST_POTION = AlchemyCauldron.createPotion(
            Color.RED,
            "&4Test",
            new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 1200, 1),
            new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1200, 1)
    );

    public static final SlimefunItemStack SACRIFICIAL_DAGGER = new SlimefunItemStack(
            "SACRIFICIAL_DAGGER",
            Material.IRON_SWORD,
            "&fSacrificial Dagger",
            "&7Collects blood from killing creatures",
            "&7Right-Click to collect blood from yourself"
    );

    // TODO infused blood, blood, on death heal
    public static final SlimefunItemStack BLOOD_TOTEM = new SlimefunItemStack(
            "BLOOD_TOTEM",
            Material.TOTEM_OF_UNDYING,
            "&4Blood Totem",
            "&7Becomes more powerful as you feed it blood"
    );

    // TODO infused blood, blood, right click to dash
    public static final SlimefunItemStack VAMPIRE_BLADE = new SlimefunItemStack(
            "VAMPIRE_BLADE",
            Material.NETHERITE_SWORD,
            "&4Vampire Blade",
            "&7Becomes more powerful as you feed it blood"
    );

    public static void setup(@Nonnull BloodHarvest plugin, @Nonnull Category category) {

        new SacrificialDagger(category, SACRIFICIAL_DAGGER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, SlimefunItems.SILVER_INGOT, SlimefunItems.SILVER_INGOT,
                null, SlimefunItems.SILVER_INGOT, null,
                null, new ItemStack(Material.STICK), null
        }).register(plugin);

        new VampireBlade(category, VAMPIRE_BLADE, BloodAltar.TYPE, new ItemStack[] {

        }).register(plugin);

        new BloodTotem(category, BLOOD_TOTEM, BloodAltar.TYPE, new ItemStack[] {

        }).register(plugin);

        new SlimefunItem(category, TEST_POTION, AlchemyCauldron.TYPE, new ItemStack[] {

        }).register(plugin);

    }

}
