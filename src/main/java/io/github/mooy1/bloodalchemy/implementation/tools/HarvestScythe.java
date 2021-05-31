package io.github.mooy1.bloodalchemy.implementation.tools;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.attributes.DamageableItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A hoe which harvests and replant crops in a 5x5 area
 */
public final class HarvestScythe extends SlimefunItem implements DamageableItem, NotPlaceable {

    public HarvestScythe(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(getUseHandler());
    }

    @Nonnull
    private ItemUseHandler getUseHandler() {
        return e -> {
            if (e.getClickedBlock().isEmpty()) {
                return;
            }

            Block b = e.getClickedBlock().get();

            switch (b.getType()) {
                case CACTUS:
                case SUGAR_CANE:
                    breakVerticalCrops(e.getPlayer(), e.getItem(), b, b.getType());
                    break;
                case PUMPKIN:
                case PUMPKIN_STEM:
                case ATTACHED_PUMPKIN_STEM:
                    breakCrops(e.getPlayer(), e.getItem(), b, Material.PUMPKIN);
                    break;
                case MELON:
                case MELON_STEM:
                case ATTACHED_MELON_STEM:
                    breakCrops(e.getPlayer(), e.getItem(), b, Material.MELON);
                    break;
                case WHEAT:
                case CARROTS:
                case POTATOES:
                case BEETROOTS:
                    breakGrowingCrops(e.getPlayer(), e.getItem(), b, b.getType());
                    break;
            }
        };
    }

    private void breakVerticalCrops(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block b, @Nonnull Material type) {
        do {
            b = b.getRelative(0, -1, 0);

            // Don't go below world, Change after 1.17
            if (b.getY() < 0) {
                return;
            }
        } while (b.getType() == type);

        //  Don't go above world
        if (b.getY() + 2 > b.getWorld().getMaxHeight()) {
            return;
        }

        breakCrops(p, item, b.getRelative(0, 2, 0), type);
    }

    private void breakCrops(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block b, @Nonnull Material type) {
        Block target;
        for (int x = -2 ; x < 3 ; x++) {
            for (int z = -2 ; z < 3 ; z++) {
                if ((target = b.getRelative(x, 0, z)).getType() == type) {
                    breakCrop(p, item, target);
                }
            }
        }
    }

    private void breakCrop(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block target) {
        BlockBreakEvent breakEvent = new BlockBreakEvent(target, p);

        Bukkit.getPluginManager().callEvent(breakEvent);

        if (!breakEvent.isCancelled()) {
            target.getWorld().playEffect(target.getLocation(), Effect.STEP_SOUND, target.getType());

            if (breakEvent.isDropItems()) {
                // Break naturally so that vertical crops are handled correctly
                target.breakNaturally(item);
            } else {
                target.setType(Material.AIR);
            }

            if (ThreadLocalRandom.current().nextBoolean()) {
                damageItem(p, item);
            }
        }
    }

    private void breakGrowingCrops(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block b, @Nonnull Material type) {
        Block target;
        for (int x = -2 ; x < 3 ; x++) {
            for (int z = -2 ; z < 3 ; z++) {
                if ((target = b.getRelative(x, 0, z)).getType() == type) {
                    harvestAndReplant(p, item, target);
                }
            }
        }
    }

    private void harvestAndReplant(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block target) {
        BlockData data = target.getBlockData();

        if (!(data instanceof Ageable)) {
            return;
        }

        Ageable ageable = (Ageable) data;

        if (ageable.getAge() != ageable.getMaximumAge()) {
            return;
        }

        String id = BlockStorage.checkID(target);

        BlockBreakEvent breakEvent = new BlockBreakEvent(target, p);
        Bukkit.getPluginManager().callEvent(breakEvent);

        if (!breakEvent.isCancelled()) {

            // Do effects
            World w = target.getWorld();
            w.playEffect(target.getLocation(), Effect.STEP_SOUND, target.getType());
            w.playSound(target.getLocation(), Sound.ITEM_CROP_PLANT, 2, 1);

            if (breakEvent.isDropItems()) {
                // Break naturally then replant
                target.breakNaturally(item);
                target.setType(ageable.getMaterial());
            }

            if (id != null) {
                // If it was a sf item, re-add the id
                BlockStorage.store(target, id);
            }

            // Reset age
            ageable.setAge(0);
            target.setBlockData(ageable);

            if (ThreadLocalRandom.current().nextBoolean()) {
                damageItem(p, item);
            }
        }
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

}
