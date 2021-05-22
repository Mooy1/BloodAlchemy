package io.github.mooy1.bloodarcana.implementation.blocks.altar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodarcana.BloodArcana;
import io.github.mooy1.bloodarcana.implementation.Items;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class BloodAltar extends SlimefunItem {

    private static final Map<BloodInput, BloodRecipe> RECIPES = new HashMap<>();

    public static final RecipeType TYPE = new RecipeType(BloodArcana.inst().getKey("blood_altar"),
            Items.BLOOD_ALTAR, (itemStacks, itemStack) -> {
        BloodRecipe recipe = new BloodRecipe(itemStacks, itemStack);
        /*
         * We map the recipe to itself so that we can do fast lookup via an input
         * as well as access the original recipe via the output
         */
        RECIPES.put(recipe, recipe);
    });

    private static final int PROCESS_TICKS = 24;

    private final Map<Location, BloodProcess> processing = new HashMap<>();
    private final int speed;

    public BloodAltar(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int speed) {
        super(category, item, recipeType, recipe);
        this.speed = speed;
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            public boolean isSynchronized() {
                return false;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                BloodAltar.this.processing.computeIfPresent(b.getLocation(), (l, process) -> {
                    if (process.increment(BloodAltar.this.speed) == PROCESS_TICKS) {
                        b.getWorld().dropItemNaturally(b.getLocation(), process.getRecipe().getOutput());
                        // TODO particles/sounds?
                        return null;
                    } else {
                        // TODO particles/sounds?
                        return process;
                    }
                });
            }

        }, new BlockBreakHandler(false, false) {

            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                BloodProcess processing = BloodAltar.this.processing.remove(e.getBlock().getLocation());

                if (processing != null) {
                    e.getPlayer().sendMessage(ChatColor.RED + "Oops! Your recipe failed because you broke the altar!");

                    // drop the recipe's inputs
                    drops.addAll(processing.getRecipe().getInputs());
                }
            }

        }, (BlockUseHandler) e -> {
            if (e.getClickedBlock().isEmpty()) {
                return;
            }

            e.setUseBlock(Event.Result.DENY);
            e.setUseItem(Event.Result.DENY);

            Block b = e.getClickedBlock().get();

            BloodProcess process = BloodAltar.this.processing.get(b.getLocation());

            if (process != null) {
                double percent = 100 * (double) process.getTicks() / PROCESS_TICKS;
                e.getPlayer().sendMessage(ChatColor.GREEN + "Infusing... " + LorePreset.format(percent) + ")%");
            } else {
                start(b, e.getPlayer());
            }
        });
    }

    private void start(@Nonnull Block b, @Nonnull Player p) {

    }

}
