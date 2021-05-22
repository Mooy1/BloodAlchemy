package io.github.mooy1.bloodharvest.implementation.blocks.alchemy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An abstract item which crafts from items dropped in the world
 */
public abstract class AbstractAlchemyCrafter extends SlimefunItem {

    private final Map<Location, AlchemyProcess> processing = new HashMap<>();

    public AbstractAlchemyCrafter(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {

            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                AbstractAlchemyCrafter.this.processing.computeIfPresent(b.getLocation(), (l, process) -> {
                    if (process.increment() >= getTicksPerCraft()) {
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
                AlchemyProcess processing = AbstractAlchemyCrafter.this.processing.remove(e.getBlock().getLocation());
                if (processing != null) {
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

            AlchemyProcess process = AbstractAlchemyCrafter.this.processing.get(b.getLocation());

            if (process != null) {
                double percent = 100 * (double) process.getTicks() / getTicksPerCraft();
                e.getPlayer().sendMessage(ChatColor.GREEN + "Infusing... " + LorePreset.format(percent) + ")%");
            } else {
                // TODO START CRAFTING
            }
        });
    }

    /**
     * @return The recipes that are populated by the {@link RecipeType}
     */
    @Nonnull
    protected abstract Map<AlchemyInput, AlchemyRecipe> getRecipes();

    /**
     * @return The number of ticks to craft an recipe
     */
    protected abstract int getTicksPerCraft();

    /**
     * @return The radius to check for items in
     */
    protected abstract double getItemRadius();

    /**
     * Creates a callback for sub class's {@link RecipeType}
     */
    protected static BiConsumer<ItemStack[], ItemStack> createRecipeCallback(Map<AlchemyInput, AlchemyRecipe> recipes) {
        return (itemStacks, itemStack) -> {
            AlchemyRecipe recipe = new AlchemyRecipe(itemStacks, itemStack);
            /*
             * We map the recipe to itself so that we can do fast lookup
             * via an input that isn't completely equal to the recipe
             * as well as access the original recipe via the output
             */
            recipes.put(recipe, recipe);
        };
    }

}
