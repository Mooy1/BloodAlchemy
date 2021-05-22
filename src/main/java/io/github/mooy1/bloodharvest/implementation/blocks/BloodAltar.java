package io.github.mooy1.bloodharvest.implementation.blocks;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.Blocks;
import io.github.mooy1.bloodharvest.implementation.blocks.alchemy.AbstractAlchemyCrafter;
import io.github.mooy1.bloodharvest.implementation.blocks.alchemy.AlchemyInput;
import io.github.mooy1.bloodharvest.implementation.blocks.alchemy.AlchemyRecipe;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An altar at which players will sacrifice blood and items to create and infuse items.
 */
public final class BloodAltar extends AbstractAlchemyCrafter {

    static final Map<AlchemyInput, AlchemyRecipe> RECIPES = new HashMap<>();

    public static final RecipeType TYPE = new RecipeType(BloodHarvest.inst().getKey("blood_altar"),
            Blocks.BLOOD_ALTAR, createRecipeCallback(RECIPES));


    public BloodAltar(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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

}
