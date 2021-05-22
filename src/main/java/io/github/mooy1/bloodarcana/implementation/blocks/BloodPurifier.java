package io.github.mooy1.bloodarcana.implementation.blocks;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodarcana.BloodArcana;
import io.github.mooy1.bloodarcana.implementation.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class BloodPurifier extends SlimefunItem {

    public static final RecipeType TYPE = new RecipeType(BloodArcana.inst().getKey("blood_purifier"), Items.BLOOD_PURIFIER);

    public BloodPurifier(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

}
