package io.github.mooy1.bloodarcana.implementation.blocks;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodarcana.BloodArcana;
import io.github.mooy1.bloodarcana.implementation.Items;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public final class BloodCollector extends SlimefunItem implements Listener {

    public static final RecipeType TYPE = new RecipeType(BloodArcana.inst().getKey("blood_collector"), Items.BLOOD_COLLECTOR);

    private final int speed;

    public BloodCollector(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int speed) {
        super(category, item, recipeType, recipe);
        this.speed = speed;
    }

    @Override
    public void preRegister() {
        BloodArcana.inst().registerListener(this);
    }

    @EventHandler
    private void onDeath(@Nonnull EntityDeathEvent e) {
        Block b = e.getEntity().getLocation().getBlock();

        // Check the 2 blocks below for collectors
        if (BlockStorage.check(b = b.getRelative(BlockFace.DOWN), getId())
                || BlockStorage.check(b = b.getRelative(BlockFace.DOWN), getId())) {
            addBlood(b.getRelative(BlockFace.DOWN));
        }
    }

    private void addBlood(Block b) {
        // TODO particles and sounds?

        switch (b.getType()) {
            case CHEST:
            case TRAPPED_CHEST:
            case BARREL:
            case HOPPER:
            case DISPENSER:
            case DROPPER:
                break;
            default:
                if (!SlimefunTag.SHULKER_BOXES.isTagged(b.getType())) {
                    return;
                }
        }

        BlockState state = PaperLib.getBlockState(b, false).getState();

        if (state instanceof InventoryHolder) {
            ((InventoryHolder) state).getInventory().addItem(new CustomItem(Items.IMPURE_BLOOD, this.speed));
        }
    }

}
