package io.github.mooy1.bloodalchemy.implementation.blocks;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.Items;
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

        BloodAlchemy.inst().registerListener(this);
    }

    @EventHandler
    private void onDeath(@Nonnull EntityDeathEvent e) {
        if (ThreadLocalRandom.current().nextInt(100) < this.chance) {

            Block b = e.getEntity().getLocation().getBlock();

            // Check the 2 blocks below for collectors
            if (BlockStorage.check(b = b.getRelative(BlockFace.DOWN), getId())
                    || BlockStorage.check(b = b.getRelative(BlockFace.DOWN), getId())) {
                addBlood(b);
            }
        }
    }

    private void addBlood(@Nonnull Block b) {
        if (b.getType() == Material.HOPPER) {
            BlockState state = PaperLib.getBlockState(b, false).getState();
            if (state instanceof InventoryHolder) {
                ((InventoryHolder) state).getInventory().addItem(Items.BLOOD.clone());
            }
        }
    }

}
