package io.github.mooy1.bloodharvest.implementation.blocks;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;

/**
 * An upgrade to the {@link BloodAltar} which can be automated with cargo and is faster
 */
public final class InfusedBloodAltar extends AbstractContainer {

    public InfusedBloodAltar(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[0];
    }

    @Override
    protected void tick(@Nonnull Block block) {

    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {

    }

}
