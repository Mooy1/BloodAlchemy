package io.github.mooy1.bloodalchemy.implementation.blocks.alchemy;

import java.util.Map;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.players.CoolDownMap;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An abstract item which crafts from items dropped in the world
 */
public abstract class AbstractAlchemyAltar extends SlimefunItem {

    private final CoolDownMap coolDowns = new CoolDownMap();

    public AbstractAlchemyAltar(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(getUseHandler());
    }

    private BlockUseHandler getUseHandler() {
        return e -> {
            if (e.getClickedBlock().isEmpty()) {
                return;
            }

            e.setUseBlock(Event.Result.DENY);
            e.setUseItem(Event.Result.DENY);

            if (this.coolDowns.checkAndReset(e.getPlayer().getUniqueId(), 200)) {
                findRecipe(e.getClickedBlock().get().getLocation(), e.getPlayer());
            }
        };
    }

    /**
     * Finds a recipe from items on the ground and starts the process
     */
    private void findRecipe(@Nonnull Location l, @Nonnull Player p) {
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
            onCraftStart(l);
            new AlchemyProcess(this, recipe, l);
        }
    }

    /**
     * @return The recipes that are populated by the {@link RecipeType}
     */
    @Nonnull
    protected abstract Map<AlchemyInput, AlchemyRecipe> getRecipes();

    /**
     * @return The radius to check for items in
     */
    protected abstract double getItemRadius();

    /**
     * Called when a crafting process starts
     */
    protected abstract void onCraftStart(Location l);

    /**
     * Called when a crafting process ticks
     */
    protected abstract void onCraftProcess(Location l);

    /**
     * Called when a crafting process finishes
     */
    protected abstract void onCraftFinish(Location l);

    /**
     * @return A callback for sub class's {@link RecipeType}
     */
    protected static BiConsumer<ItemStack[], ItemStack> createRecipeCallback(Map<AlchemyInput, AlchemyRecipe> recipes) {
        return (itemStacks, itemStack) -> {
            AlchemyRecipe recipe = new AlchemyRecipe(itemStacks, itemStack);
            /*
             * The recipe acts as a way to match inputs as well
             * as storing the outputs so we map it to itself.
             */
            recipes.put(recipe, recipe);
        };
    }

    /**
     * Consumes an {@link AlchemyRecipe} from an array of {@link Item}s
     */
    private static void consumeRecipe(ItemStackWrapper[] inputs, @Nonnull Entity[] items, @Nonnull AlchemyRecipe recipe) {
        for (Map.Entry<String, Integer> entry : recipe.getEntries()) {

            int rem = entry.getValue();

            for (int i = 0 ; i < inputs.length ; i++) {

                ItemStackWrapper consume = inputs[i];

                if (entry.getKey().equals(StackUtils.getIDorType(consume))) {
                    int amt = consume.getAmount();

                    if (amt > rem) {
                        ((Item) items[i]).getItemStack().setAmount(amt - rem);
                        break;
                    }

                    items[i].remove();

                    if ((rem -= amt) == 0) {
                        break;
                    }
                }
            }
        }
    }

}
