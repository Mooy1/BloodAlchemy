package io.github.mooy1.bloodalchemy.implementation.blocks;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.thebusybiscuit.slimefun4.api.events.AndroidFarmEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class GoldenWheat extends SlimefunItem implements Listener {

    private final SlimefunItemStack seed;

    public GoldenWheat(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, SlimefunItemStack seed) {
        super(category, item, recipeType, recipe);
        this.seed = seed;

        addItemHandler(getBreakHandler());

        BloodAlchemy.inst().registerListener(this);
    }

    @EventHandler
    private void onAndroidFarm(@Nonnull AndroidFarmEvent e) {
        if (BlockStorage.check(e.getBlock(), getId())) {
            e.setDrop(getItem().clone());
        }
    }

    private BlockBreakHandler getBreakHandler() {
        return new BlockBreakHandler(true, true) {

            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                BlockData data = e.getBlock().getBlockData();
                if (data instanceof Ageable) {
                    Ageable ageable = (Ageable) data;
                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        drops.add(getItem().clone());
                        drops.add(GoldenWheat.this.seed.clone());
                    }
                }
            }

        };
    }

    @Nonnull
    @Override
    public Collection<ItemStack> getDrops() {
        return Collections.singletonList(GoldenWheat.this.seed.clone());
    }

}
