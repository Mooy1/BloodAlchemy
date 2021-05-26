package io.github.mooy1.bloodalchemy.implementation.blocks;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.Blocks;
import io.github.mooy1.bloodalchemy.implementation.blocks.alchemy.AbstractAlchemyAltar;
import io.github.mooy1.bloodalchemy.implementation.blocks.alchemy.AlchemyInput;
import io.github.mooy1.bloodalchemy.implementation.blocks.alchemy.AlchemyRecipe;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An altar at which players will sacrifice blood and items to create and infuse more powerful items.
 */
public final class BloodAltar extends AbstractAlchemyAltar {

    static final Map<AlchemyInput, AlchemyRecipe> RECIPES = new HashMap<>();

    public static final RecipeType TYPE = new RecipeType(BloodAlchemy.inst().getKey("blood_altar"),
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
    protected double getItemRadius() {
        return 2;
    }

    @Override
    protected void onCraftStart(Location l) {

    }

    @Override
    protected void onCraftProcess(Location l) {

    }

    @Override
    protected void onCraftFinish(Location l) {

    }

}
