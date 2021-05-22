package io.github.mooy1.bloodarcana.implementation;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodarcana.BloodArcana;
import io.github.mooy1.bloodarcana.implementation.blocks.BloodAltar;
import io.github.mooy1.bloodarcana.implementation.blocks.BloodCollector;
import io.github.mooy1.bloodarcana.implementation.blocks.BloodPurifier;
import io.github.mooy1.infinitylib.presets.LorePreset;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

@UtilityClass
public final class Items {

    public static final SlimefunItemStack IMPURE_BLOOD = new SlimefunItemStack(
            "IMPURE_BLOOD",
            Material.NETHER_WART,
            "&cImpure Blood",
            "&7Must be purified to be used in arcana"
    );

    public static final SlimefunItemStack PURIFIED_BLOOD = new SlimefunItemStack(
            "PURIFIED_BLOOD",
            Material.RED_DYE,
            "&4Purified Blood",
            "&7Gleaming with power for use in arcana"
    );

    public static final SlimefunItemStack BLOOD_ALTAR = new SlimefunItemStack(
            "BLOOD_ALTAR",
            Material.ENCHANTING_TABLE,
            "&cBlood Altar",
            "&7Used to create and infuse items with blood",
            "",
            LorePreset.speed(1)
    );

    public static final SlimefunItemStack INFUSED_BLOOD_ALTAR = new SlimefunItemStack(
            "INFUSED_BLOOD_ALTAR",
            Material.ENCHANTING_TABLE,
            "&cInfused Blood Altar",
            "&7Used to create and infuse items with blood",
            "",
            LorePreset.speed(8)
    );

    public static final SlimefunItemStack BLOOD_COLLECTOR = new SlimefunItemStack(
            "BLOOD_COLLECTOR",
            Material.CAULDRON,
            "&cBlood Collector",
            "&7Collects impure blood from dying creatures above",
            "&7Automatically outputs blood to inventories below",
            "",
            LorePreset.speed(1)
    );

    public static final SlimefunItemStack INFUSED_BLOOD_COLLECTOR = new SlimefunItemStack(
            "INFUSED_BLOOD_COLLECTOR",
            Material.CAULDRON,
            "&cBlood Collector",
            "&7Collects impure blood from dying creatures above",
            "&7Automatically outputs blood to inventories below",
            "",
            LorePreset.speed(8)
    );

    public static final SlimefunItemStack SACRIFICIAL_DAGGER = new SlimefunItemStack(
            "SACRIFICIAL_DAGGER",
            Material.IRON_SWORD,
            "&fSacrificial Dagger",
            "&7Collects impure blood from killing creatures",
            "&7Right-Click to stab yourself for impure blood"
    );

    public static final SlimefunItemStack BLOOD_PURIFIER = new SlimefunItemStack(
            "BLOOD_PURIFIER",
            Material.NETHER_BRICKS,
            "&4Blood Purifier",
            "&7Purifies impure blood",
            "",
            LorePreset.speed(1)
    );

    public static final SlimefunItemStack INFUSED_BLOOD_PURIFIER = new SlimefunItemStack(
            "INFUSED_BLOOD_PURIFIER",
            Material.NETHER_BRICKS,
            "&4Infused Blood Purifier",
            "&7Purifies impure blood",
            "",
            LorePreset.speed(8)
    );

    public static void setup(BloodArcana plugin) {
        Category category = new Category(plugin.getKey("blood_arcana"),
                new CustomItem(Material.NETHER_WART_BLOCK, "&4Blood Arcana"));

        new SlimefunItem(category, IMPURE_BLOOD, BloodCollector.TYPE, new ItemStack[] {

        }).register(plugin);

        new SlimefunItem(category, PURIFIED_BLOOD, BloodPurifier.TYPE, new ItemStack[] {

        }).register(plugin);

        new BloodAltar(category, BLOOD_ALTAR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }).register(plugin);

        new BloodAltar(category, INFUSED_BLOOD_ALTAR, BloodAltar.TYPE, new ItemStack[] {

        }).register(plugin);

        new BloodCollector(category, BLOOD_COLLECTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }).register(plugin);

        new BloodCollector(category, INFUSED_BLOOD_COLLECTOR, BloodAltar.TYPE, new ItemStack[] {

        }).register(plugin);

        new BloodPurifier(category, BLOOD_PURIFIER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {

        }).register(plugin);

        new BloodPurifier(category, INFUSED_BLOOD_PURIFIER, BloodAltar.TYPE, new ItemStack[] {

        }).register(plugin);
    }

}
