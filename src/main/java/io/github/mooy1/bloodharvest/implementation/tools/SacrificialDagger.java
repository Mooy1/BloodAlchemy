package io.github.mooy1.bloodharvest.implementation.tools;

import javax.annotation.Nonnull;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.Items;
import io.github.mooy1.bloodharvest.implementation.Tools;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class SacrificialDagger extends SimpleSlimefunItem<ItemUseHandler> {

    public static final RecipeType TYPE = new RecipeType(BloodHarvest.inst().getKey("sacrificial_dagger"), Tools.SACRIFICIAL_DAGGER);

    public SacrificialDagger(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public ItemUseHandler getItemHandler() {
        return e -> {
            e.setUseItem(Event.Result.DENY);
            e.setUseBlock(Event.Result.DENY);
            e.getPlayer().setHealth(e.getPlayer().getHealth() - 4);
            e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), Items.BLOOD.clone());
            // TODO particles and sounds
        };
    }


}
