package io.github.mooy1.bloodharvest.implementation.blocks;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.Blocks;
import io.github.mooy1.bloodharvest.implementation.blocks.alchemy.AbstractAlchemyAltar;
import io.github.mooy1.bloodharvest.implementation.blocks.alchemy.AlchemyInput;
import io.github.mooy1.bloodharvest.implementation.blocks.alchemy.AlchemyRecipe;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A cauldron which is able to brew special potions
 */
public final class AlchemyCauldron extends AbstractAlchemyAltar {

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

    @Override
    protected void onCraftProcess(Location l) {

    }

    @Override
    protected void onCraftFinish(Location l) {

    }

}
