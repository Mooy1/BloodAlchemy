package io.github.mooy1.bloodharvest.core.blocks;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.Blocks;
import io.github.mooy1.bloodharvest.core.blocks.alchemy.AbstractAlchemyCrafter;
import io.github.mooy1.bloodharvest.core.blocks.alchemy.AlchemyInput;
import io.github.mooy1.bloodharvest.core.blocks.alchemy.AlchemyRecipe;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;

/**
 * A cauldron which is able to brew special potions
 */
public final class AlchemyCauldron extends AbstractAlchemyCrafter {

    private static final Map<AlchemyInput, AlchemyRecipe> RECIPES = new HashMap<>();

    public static final RecipeType TYPE = new RecipeType(BloodHarvest.inst().getKey("alchemy_cauldron"),
            Blocks.ALCHEMY_CAULDRON, createRecipeCallback(RECIPES));

    public AlchemyCauldron(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected Map<AlchemyInput, AlchemyRecipe> getRecipes() {
        return RECIPES;
    }

    @Override
    protected int getTicksPerCraft() {
        return 10;
    }

    @Override
    protected double getItemRadius() {
        return 1;
    }

    @Nonnull
    public static SlimefunItemStack createPotion(Color color, String name, PotionEffect... effects) {
        return new SlimefunItemStack(
                ChatColor.stripColor(ChatColors.color(name)).toUpperCase(Locale.ROOT) + "_POTION",
                Material.POTION,
                name + " Potion",
                meta -> {
                    PotionMeta potion = (PotionMeta) meta;
                    potion.setColor(color);
                    for (PotionEffect effect : effects) {
                        potion.addCustomEffect(effect, true);
                    }
                }
        );
    }

}
