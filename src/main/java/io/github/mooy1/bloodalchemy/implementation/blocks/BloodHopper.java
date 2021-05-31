package io.github.mooy1.bloodalchemy.implementation.blocks;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.Items;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.VanillaInventoryDropHandler;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A hopper which collects blood from dying entities up to 2 blocks above
 */
public final class BloodHopper extends SlimefunItem implements Listener {

    private final int chance;

    public BloodHopper(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int chance) {
        super(category, item, recipeType, recipe);
        this.chance = chance;

        addItemHandler(new VanillaInventoryDropHandler<>(Hopper.class));

        BloodAlchemy.inst().registerListener(this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onDeath(@Nonnull EntityDeathEvent e) {
        if (ThreadLocalRandom.current().nextInt(100) < this.chance) {
            Block b = e.getEntity().getLocation().getBlock();

            // Check up to 3 blocks below for this block
            for (int i = 0 ; i < 3 ; i++) {
                if (BlockStorage.check(b = b.getRelative(0, -1, 0), getId())) {
                    addBlood(b);
                }
            }
        }
    }

    private void addBlood(@Nonnull Block b) {
        if (b.getType() == Material.HOPPER) {
            BlockState state = PaperLib.getBlockState(b, false).getState();
            if (state instanceof Hopper) {
                ((Hopper) state).getInventory().addItem(Items.BLOOD.clone());
            }
        }
    }

}
