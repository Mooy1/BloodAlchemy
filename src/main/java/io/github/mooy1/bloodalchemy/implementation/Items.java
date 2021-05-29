package io.github.mooy1.bloodalchemy.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.blocks.BloodHopper;
import io.github.mooy1.bloodalchemy.implementation.blocks.GoldenSeeds;
import io.github.mooy1.bloodalchemy.implementation.blocks.GoldenWheat;
import io.github.mooy1.bloodalchemy.implementation.blocks.GrowingShroom;
import io.github.mooy1.bloodalchemy.implementation.blocks.altar.BloodAltar;
import io.github.mooy1.bloodalchemy.implementation.tools.BloodTotem;
import io.github.mooy1.bloodalchemy.implementation.tools.BloodWolfRune;
import io.github.mooy1.bloodalchemy.implementation.tools.InfusedVampireBlade;
import io.github.mooy1.bloodalchemy.implementation.tools.SacrificialDagger;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Items added in this addon
 */
@UtilityClass
public final class Items {

    //region Items
    public static final SlimefunItemStack BLOOD_GEM = new SlimefunItemStack(
            "BLOOD_GEM",
            Material.RED_DYE,
            "&cBlood Gem",
            "&7A Diamond infused with the power of blood"
    );
    public static final SlimefunItemStack BLOOD = new SlimefunItemStack(
            "BLOOD",
            Material.REDSTONE,
            "&4Blood",
            "&7Gleaming with power"
    );
    //endregion

    //region Tools
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
            "&7Gains blood and upon attacking or killing",
            "&7Revives you upon death when filled with blood",
            "",
            BloodUtils.getStoredLore(0)
    );
    public static final SlimefunItemStack BLOOD_WOLF_RUNE = new SlimefunItemStack(
            "BLOOD_WOLF_RUNE",
            Material.RED_GLAZED_TERRACOTTA,
            "&4Blood Wolf Rune",
            "&7Gives a wolf the ability to heal itself and draw blood when attacking",
            "&7Right-Click on a wolf to activate"
    );
    public static final SlimefunItemStack INFUSED_VAMPIRE_BLADE = new SlimefunItemStack(
            "INFUSED_VAMPIRE_BLADE",
            Material.NETHERITE_SWORD,
            "&4Infused Vampire Blade",
            meta -> {
                List<String> lore = new ArrayList<>();
                lore.add("&7Gains blood and heals you upon attacking or killing");
                lore.add("&7Right-Click to teleport for 20 blood");
                lore.add("");
                lore.add(BloodUtils.getStoredLore(0));
                meta.setLore(lore);
                meta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
            }
    );
    public static final SlimefunItemStack BLOOD_APPLE = new SlimefunItemStack(
            "BLOOD_APPLE",
            Material.ENCHANTED_GOLDEN_APPLE,
            "&cInfused Blood Apple",
            "&7Infused with the power of blood"
    );
    public static final SlimefunItemStack GOLDEN_POTION = new SlimefunItemStack(
            "GOLDEN_POTION",
            Material.POTION,
            "&cGolden Potion",
            meta -> {
                PotionMeta potion = (PotionMeta) meta;
                potion.setColor(Color.YELLOW);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.ABSORPTION, 14400, 4), true);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 14400, 2), true);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.LUCK, 14400, 2), true);
            }
    );
    public static final SlimefunItemStack VAMPIRIC_SPEED_POTION = new SlimefunItemStack(
            "VAMPIRIC_SPEED_POTION",
            Material.POTION,
            "&cVampiric Speed Potion",
            meta -> {
                PotionMeta potion = (PotionMeta) meta;
                potion.setColor(Color.AQUA);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 14400, 2), true);
            }
    );
    public static final SlimefunItemStack VAMPIRIC_STRENGTH_POTION = new SlimefunItemStack(
            "VAMPIRIC_STRENGTH_POTION",
            Material.POTION,
            "&cVampiric Strength Potion",
            meta -> {
                PotionMeta potion = (PotionMeta) meta;
                potion.setColor(Color.PURPLE);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 14400, 2), true);
            }
    );
    public static final SlimefunItemStack VAMPIRIC_REGENERATION_POTION = new SlimefunItemStack(
            "VAMPIRIC_REGENERATION_POTION",
            Material.POTION,
            "&cVampiric Regeneration Potion",
            meta -> {
                PotionMeta potion = (PotionMeta) meta;
                potion.setColor(Color.RED);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 14400, 2), true);
            }
    );
    public static final SlimefunItemStack DEATH_POTION = new SlimefunItemStack(
            "DEATH_POTION",
            Material.SPLASH_POTION,
            "&8Death Potion",
            meta -> {
                PotionMeta potion = (PotionMeta) meta;
                potion.setColor(Color.BLACK);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 0, 4), true);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 1200, 2), true);
                potion.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, 400, 2), true);
            }
    );
    //endregion

    //region Blocks
    public static final SlimefunItemStack GOLDEN_SEEDS = new SlimefunItemStack(
            "GOLDEN_SEEDS",
            Material.WHEAT_SEEDS,
            "&eGolden Seeds",
            "&7Alchemically imbued with gold"
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
    public static final SlimefunItemStack GOLDEN_WHEAT = new SlimefunItemStack(
            "GOLDEN_WHEAT",
            Material.WHEAT,
            "&eGolden Wheat",
            "&7Infused with the power of gold"
    );
    public static final SlimefunItemStack BLOOD_SHROOM = new SlimefunItemStack(
            "BLOOD_SHROOM",
            Material.RED_MUSHROOM,
            "&cBlood Shroom",
            "&7Gives health to those who come near",
            "&7Spreads to nearby red mushrooms"
    );
    public static final SlimefunItemStack DEATH_SHROOM = new SlimefunItemStack(
            "DEATH_SHROOM",
            Material.BROWN_MUSHROOM,
            "&8Death Shroom",
            "&7Brings death upon those who get too close",
            "&7Spreads to nearby brown mushrooms"
    );
    //endregion

    public static void setup(@Nonnull BloodAlchemy plugin, @Nonnull Category category) {

        new BloodAltar(category, BLOOD_ALTAR, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                null, Items.BLOOD, null,
                Items.BLOOD, new ItemStack(Material.ENCHANTING_TABLE), Items.BLOOD,
                null, Items.BLOOD, null
        }).register(plugin);

        new BloodHopper(category, BLOOD_HOPPER, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.HOPPER),
                new SlimefunItemStack(Items.BLOOD, 16)
        }, 10).register(plugin);

        new BloodHopper(category, INFUSED_BLOOD_HOPPER, BloodAltar.TYPE, new ItemStack[] {
                BLOOD_HOPPER,
                Items.BLOOD_GEM
        }, 80).register(plugin);

        new GoldenSeeds(category, GOLDEN_SEEDS, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.WHEAT_SEEDS, 16),
                new SlimefunItemStack(Items.BLOOD, 16),
                new ItemStack(Material.GOLD_INGOT, 16)
        }, GOLDEN_WHEAT).register(plugin);

        new GoldenWheat(category, GOLDEN_WHEAT, GoldenSeeds.TYPE, new ItemStack[] {
                GOLDEN_SEEDS
        }, GOLDEN_SEEDS).register(plugin);

        new SlimefunItem(category, BLOOD_APPLE, BloodAltar.TYPE, new ItemStack[] {
                new SlimefunItemStack(GOLDEN_WHEAT, 64),
                new ItemStack(Material.APPLE, 64)
        }).register(plugin);

        new SacrificialDagger(category, SACRIFICIAL_DAGGER, RecipeType.MAGIC_WORKBENCH, new ItemStack[] {
                null, SlimefunItems.SILVER_INGOT, SlimefunItems.SILVER_INGOT,
                null, SlimefunItems.SILVER_INGOT, null,
                null, new ItemStack(Material.STICK), null
        }).register(plugin);

        new InfusedVampireBlade(category, INFUSED_VAMPIRE_BLADE, BloodAltar.TYPE, new ItemStack[] {
                SlimefunItems.BLADE_OF_VAMPIRES,
                new ItemStack(Material.NETHERITE_INGOT, 4),
                new SlimefunItemStack(BLOOD_GEM, 4),
                new SlimefunItemStack(BLOOD, 64)
        }).register(plugin);

        new BloodTotem(category, BLOOD_TOTEM, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.TOTEM_OF_UNDYING),
                new SlimefunItemStack(BLOOD_GEM, 8),
                new SlimefunItemStack(BLOOD, 64)
        }).register(plugin);

        new SlimefunItem(category, BLOOD, SacrificialDagger.TYPE, new ItemStack[] {
                new CustomItem(Material.ZOMBIE_HEAD, "&cKill any mob")
        }).register(plugin);

        new SlimefunItem(category, BLOOD_GEM, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.DIAMOND, 64),
                new SlimefunItemStack(BLOOD, 64)
        });

        new BloodWolfRune(category, BLOOD_WOLF_RUNE, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.BONE, 16),
                new ItemStack(Material.BEEF, 16),
                new SlimefunItemStack(BLOOD, 16)
        });

        new GrowingShroom(category, BLOOD_SHROOM, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.RED_MUSHROOM, 32),
                new SlimefunItemStack(BLOOD, 32)
        }, new PotionEffect(PotionEffectType.REGENERATION, 400, 0));

        new GrowingShroom(category, DEATH_SHROOM, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.BROWN_MUSHROOM, 32),
                new ItemStack(Material.BONE, 32),
                new ItemStack(Material.WITHER_ROSE, 8),
                new SlimefunItemStack(BLOOD, 32)
        }, new PotionEffect(PotionEffectType.WITHER, 400, 0));

        new SlimefunItem(category, DEATH_POTION, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS_BOTTLE),
                new ItemStack(Material.GUNPOWDER, 8),
                new ItemStack(Material.SPIDER_EYE, 4),
                new ItemStack(Material.WITHER_ROSE, 4),
                new SlimefunItemStack(DEATH_SHROOM, 4)
        });

        new SlimefunItem(category, VAMPIRIC_REGENERATION_POTION, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS_BOTTLE),
                new SlimefunItemStack(BLOOD_SHROOM, 8),
                new ItemStack(Material.GHAST_TEAR, 8),
                new SlimefunItemStack(BLOOD, 32),
        });

        new SlimefunItem(category, VAMPIRIC_STRENGTH_POTION, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS_BOTTLE),
                new SlimefunItemStack(BLOOD_SHROOM, 8),
                new ItemStack(Material.BLAZE_ROD, 8),
                new ItemStack(Material.NETHERITE_SCRAP, 1),
                new SlimefunItemStack(BLOOD, 32),
        });

        new SlimefunItem(category, VAMPIRIC_SPEED_POTION, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.GLASS_BOTTLE),
                new ItemStack(Material.SUGAR, 16),
                new ItemStack(Material.GLOWSTONE, 16),
                new SlimefunItemStack(SlimefunItems.MAGIC_SUGAR, 16),
                new SlimefunItemStack(BLOOD, 8),
        });

        new SlimefunItem(category, GOLDEN_POTION, BloodAltar.TYPE, new ItemStack[] {
                new ItemStack(Material.HONEY_BOTTLE, 8),
                new ItemStack(Material.GOLDEN_APPLE, 4),
                new SlimefunItemStack(GOLDEN_WHEAT, 8),
                new SlimefunItemStack(BLOOD, 8),
        });
    }

}
