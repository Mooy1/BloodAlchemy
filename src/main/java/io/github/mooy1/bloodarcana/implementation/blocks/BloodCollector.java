package io.github.mooy1.bloodarcana.implementation.blocks;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodarcana.BloodArcana;
import io.github.mooy1.bloodarcana.implementation.Items;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class BloodCollector extends SimpleSlimefunItem<BlockUseHandler> {

    public static final RecipeType TYPE = new RecipeType(BloodArcana.inst().getKey("blood_collector"), Items.BLOOD_COLLECTOR);

    public BloodCollector(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public BlockUseHandler getItemHandler() {
        return e -> {
            e.setUseBlock(Event.Result.DENY);
            e.setUseItem(Event.Result.DENY);
        };
    }
}
