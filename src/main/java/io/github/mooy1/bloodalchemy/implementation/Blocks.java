package io.github.mooy1.bloodalchemy.implementation;

import java.util.Arrays;

import javax.annotation.Nonnull;
import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.blocks.BloodHopper;
import io.github.mooy1.bloodalchemy.implementation.blocks.SlimefunSeed;
import io.github.mooy1.bloodalchemy.implementation.blocks.SlimefunCrop;
import io.github.mooy1.bloodalchemy.implementation.blocks.altar.BloodAltar;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * Blocks added in this addon
 */
@UtilityClass
public final class Blocks {

    public static final SlimefunItemStack GOLDEN_SEEDS = new SlimefunItemStack(
            "GOLDEN_SEEDS",
            Material.WHEAT_SEEDS,
            "&eGolden Seeds",
            "&7Alchemically imbued with gold"
    );

    public static final SlimefunItemStack GOLDEN_WHEAT = new SlimefunItemStack(
            "GOLDEN_WHEAT",
            Material.WHEAT,
            "&eGolden Wheat",
            "&7Infused with the power of gold"
    );

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

    new BloodAltar(category, BLOOD_ALTAR, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
            null, Items.BLOOD, null,
            Items.BLOOD, new ItemStack(Material.ENCHANTING_TABLE), Items.BLOOD,
            null, Items.BLOOD, null
        }).register(plugin);

        new BloodHopper(category, BLOOD_HOPPER, BloodAltar.TYPE, Arrays.copyOf(new ItemStack[] {
            new ItemStack(Material.HOPPER), new SlimefunItemStack(Items.BLOOD, 16)
        }, 9), 10).register(plugin);

        new BloodHopper(category, INFUSED_BLOOD_HOPPER, BloodAltar.TYPE, Arrays.copyOf(new ItemStack[] {
            BLOOD_HOPPER, new SlimefunItemStack(Items.BLOOD_GEM, 4)
        }, 9), 80).register(plugin);

        new SlimefunSeed(category, GOLDEN_SEEDS, BloodAltar.TYPE, Arrays.copyOf(new ItemStack[] {
                new ItemStack(Material.WHEAT_SEEDS, 16),
                new SlimefunItemStack(Items.BLOOD, 16),
                new ItemStack(Material.GOLD_INGOT, 16)
        }, 9), GOLDEN_WHEAT).register(plugin);

        new SlimefunCrop(category, GOLDEN_WHEAT, SlimefunSeed.TYPE, Arrays.copyOf(new ItemStack[] {
                GOLDEN_SEEDS
        }, 9), GOLDEN_SEEDS).register(plugin);

    }

}
