package io.github.mooy1.bloodharvest.implementation.blocks.alchemy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An abstract item which crafts from items dropped in the world
 */
public abstract class AbstractAlchemyAltar extends SlimefunItem {

    private final Map<Location, AlchemyProcess> processing = new HashMap<>();

    public AbstractAlchemyAltar(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
                AbstractAlchemyAltar.this.processing.computeIfPresent(b.getLocation(), (l, process) -> {
                    if (process.increment() >= getTicksPerCraft()) {
                        l.getWorld().dropItemNaturally(l, process.getRecipe().getOutput());
                        onCraftProcess(l);
                        return null;
                    } else {
                        onCraftFinish(l);
                        return process;
                    }
                });
            }

        }, new BlockBreakHandler(false, false) {

            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack item, @Nonnull List<ItemStack> drops) {
                AlchemyProcess processing = AbstractAlchemyAltar.this.processing.remove(e.getBlock().getLocation());
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
            Player p = e.getPlayer();

            AbstractAlchemyAltar.this.processing.compute(b.getLocation(), (l, process) -> {
                if (process != null) {
                    String percent = LorePreset.format(100 * (double) process.getTicks() / getTicksPerCraft());
                    p.sendMessage(ChatColor.GREEN + "Infusing... (" + percent + "%)");
                    return process;
                }

                double radius = getItemRadius();
                Entity[] nearby = p.getWorld().getNearbyEntities(l, radius, radius, radius,
                        en -> en instanceof Item).toArray(new Entity[0]);

                ItemStackWrapper[] inputs = new ItemStackWrapper[nearby.length];

                int i = 0;
                for (Entity entity : nearby) {
                    inputs[i++] = new ItemStackWrapper(((Item) entity).getItemStack());
                }

                AlchemyRecipe recipe = getRecipes().get(new AlchemyInput(inputs));

                if (recipe != null) {
                    consumeRecipe(inputs, nearby, recipe);
                    onCraftStart(b.getLocation());
                    return new AlchemyProcess(recipe);
                }

                return null;
            });
        });
    }

    private static void consumeRecipe(ItemStackWrapper[] inputs, @Nonnull Entity[] entities, @Nonnull AlchemyRecipe recipe) {
        for (Map.Entry<String, Integer> entry : recipe.getEntries()) {

            int rem = entry.getValue();

            for (int i = 0 ; i < inputs.length ; i++) {

                ItemStackWrapper consume = inputs[i];

                if (entry.getKey().equals(StackUtils.getIDorType(consume))) {
                    int amt = consume.getAmount();

                    if (amt > rem) {
                        ((Item) entities[i]).getItemStack().setAmount(amt - rem);
                        break;
                    }

                    entities[i].remove();

                    if ((rem -= amt) == 0) {
                        break;
                    }
                }
            }
        }
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

    protected abstract void onCraftStart(Location l);

    protected abstract void onCraftProcess(Location l);

    protected abstract void onCraftFinish(Location l);

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
