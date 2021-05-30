package io.github.mooy1.bloodalchemy.implementation.tools;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A hoe which harvests and replant crops in a 5x5 area
 */
public final class HarvestScythe extends SlimefunItem implements NotPlaceable {

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
                case SUGAR_CANE:
                    breakTopCrops(e.getPlayer(), e.getItem(), b, b.getType());
                case PUMPKIN:
                case PUMPKIN_STEM:
                    breakCrops(e.getPlayer(), e.getItem(), b, Material.PUMPKIN);
                case MELON:
                case MELON_STEM:
                    breakCrops(e.getPlayer(), e.getItem(), b, Material.MELON);
                case CARROT:
                case POTATO:
                case WHEAT:
                case BEETROOTS:
                    breakGrowingCrops(e.getPlayer(), e.getItem(), b, b.getType());
            }
        };
    }

    private static void breakTopCrops(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block b, @Nonnull Material type) {
        Block below = b.getRelative(0, -1, 0);
        if (below.getType() == type) {
            if (below.getRelative(0, -1, 0).getType() != type) {
                breakCrops(p, item, b, type);
            }
        } else {
            breakCrops(p, item, b.getRelative(0, 1, 0), type);
        }
    }

    private static void breakCrops(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block b, @Nonnull Material type) {
        Block target;
        for (int x = -2 ; x < 3 ; x++) {
            for (int z = -2 ; z < 3 ; z++) {
                if ((target = b.getRelative(x, 0, z)).getType() == type) {
                    breakCrop(p, item, target);
                }
            }
        }
    }

    private static void breakCrop(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block target) {
        BlockBreakEvent breakEvent = new BlockBreakEvent(target, p);

        Bukkit.getPluginManager().callEvent(breakEvent);

        if (!breakEvent.isCancelled()) {
            target.setType(Material.AIR);

            if (breakEvent.isDropItems()) {
                for (ItemStack drop : target.getDrops(item, p)) {
                    target.getWorld().dropItemNaturally(target.getLocation(), drop);
                }
            }
        }
    }

    private static void breakGrowingCrops(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block b, @Nonnull Material type) {
        Block target;
        for (int x = -2 ; x < 3 ; x++) {
            for (int z = -2 ; z < 3 ; z++) {
                if ((target = b.getRelative(x, 0, z)).getType() == type) {
                    harvestAndReplant(p, item, target);
                }
            }
        }
    }

    private static void harvestAndReplant(@Nonnull Player p, @Nonnull ItemStack item, @Nonnull Block target) {
        BlockData data = target.getBlockData();

        if (!(data instanceof Ageable)) {
            return;
        }

        Ageable ageable = (Ageable) data;

        if (ageable.getAge() != ageable.getMaximumAge()) {
            return;
        }

        // First check if we can break the crop
        BlockBreakEvent breakEvent = new BlockBreakEvent(target, p);

        Bukkit.getPluginManager().callEvent(breakEvent);

        if (!breakEvent.isCancelled()) {
            ageable.setAge(0);

            target.getWorld().playSound(target.getLocation(), Sound.ITEM_CROP_PLANT, 2, 1);

            if (breakEvent.isDropItems()) {
                for (ItemStack drop : target.getDrops(item, p)) {
                    target.getWorld().dropItemNaturally(target.getLocation(), drop);
                }
            }
        }
    }

}
