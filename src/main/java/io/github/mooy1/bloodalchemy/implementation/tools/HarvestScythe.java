package io.github.mooy1.bloodalchemy.implementation.tools;

import javax.annotation.Nonnull;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ToolUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A hoe which harvests and replant crops in a 5x5 area
 */
public class HarvestScythe extends SlimefunItem implements NotPlaceable {

    public HarvestScythe(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(getBreakHandler());
    }

    @Nonnull
    private ToolUseHandler getBreakHandler() {
        return (e, tool, fortune, drops) -> {
            
        };
    }

}
