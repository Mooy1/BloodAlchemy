package io.github.mooy1.bloodalchemy.implementation.blocks;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.thebusybiscuit.slimefun4.api.events.BlockPlacerPlaceEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public final class SlimefunSeed extends SlimefunItem {

    public static final RecipeType TYPE = new RecipeType(BloodAlchemy.inst().getKey("farming"),
            new CustomItem(Material.DIAMOND_HOE, "&eFarming"));

    private final SlimefunItemStack crop;

    public SlimefunSeed(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, SlimefunItemStack crop) {
        super(category, item, recipeType, recipe);
        this.crop = crop;

        addItemHandler(getPlaceHandler());
    }

    private BlockPlaceHandler getPlaceHandler() {
        return new BlockPlaceHandler(true) {

            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                BlockStorage.store(e.getBlock(), SlimefunSeed.this.crop.getItemId());
            }

            @Override
            public void onBlockPlacerPlace(@Nonnull BlockPlacerPlaceEvent e) {
                BlockStorage.store(e.getBlock(), SlimefunSeed.this.crop.getItemId());
            }

        };
    }

}
