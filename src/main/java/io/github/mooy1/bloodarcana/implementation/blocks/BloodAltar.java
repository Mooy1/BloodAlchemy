package io.github.mooy1.bloodarcana.implementation.blocks;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodarcana.BloodArcana;
import io.github.mooy1.bloodarcana.implementation.Items;
import io.github.mooy1.infinitylib.recipes.inputs.MultiInput;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class BloodAltar extends SimpleSlimefunItem<BlockUseHandler> {

    private static final Map<MultiInput, ItemStack> RECIPES = new HashMap<>();

    public static final RecipeType TYPE = new RecipeType(BloodArcana.inst().getKey("blood_altar"), Items.BLOOD_ALTAR,
            (itemStacks, itemStack) -> RECIPES.put(new MultiInput(itemStacks), itemStack));

    public BloodAltar(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public BlockUseHandler getItemHandler() {
        return e -> {
            e.setUseItem(Event.Result.DENY);
            e.setUseBlock(Event.Result.DENY);
        };
    }

}
