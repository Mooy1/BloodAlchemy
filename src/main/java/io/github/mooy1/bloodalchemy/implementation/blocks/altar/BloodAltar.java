package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.Blocks;
import io.github.mooy1.infinitylib.items.CachedItemStack;
import io.github.mooy1.infinitylib.players.CoolDownMap;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An abstract item which crafts from items dropped in the world
 */
public final class BloodAltar extends SlimefunItem {

    private static final Map<AltarInput, AltarRecipe> RECIPES = new HashMap<>();

    public static final RecipeType TYPE = new RecipeType(BloodAlchemy.inst().getKey("blood_altar"),
            Blocks.BLOOD_ALTAR, (itemStacks, itemStack) -> {
        AltarRecipe recipe = new AltarRecipe(itemStacks, itemStack);
        /*
         * The recipe acts as a way to match inputs as well
         * as storing the outputs so we map it to itself.
         */
        RECIPES.put(recipe, recipe);
    });

    private final CoolDownMap coolDowns = new CoolDownMap();

    public BloodAltar(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
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
        Entity[] nearby = p.getWorld().getNearbyEntities(l, 2, 2, 2,
                e -> e instanceof Item).toArray(new Entity[0]);

        CachedItemStack[] arr = new CachedItemStack[nearby.length];

        for (int i = 0 ; i < arr.length ; i++) {
            arr[i] = new CachedItemStack(((Item) nearby[i]).getItemStack());
        }

        AltarRecipe recipe = RECIPES.get(new AltarInput(arr));

        if (recipe != null) {
            consumeRecipe(arr, nearby, recipe);
            new AltarProcess(this, recipe, l);
        } else {
            p.sendMessage(ChatColor.RED + "Invalid Recipe!");
        }
    }

    /**
     * Called when an {@link AltarProcess} starts
     */
    void onCraftStart(@Nonnull Location l) {
        // TODO implement
    }

    /**
     * Called when an {@link AltarProcess} processes
     */
    void onCraftProcess(@Nonnull Location l) {
        // TODO implement
    }

    /**
     * Called when an {@link AltarProcess} finishes
     */
    void onCraftFinish(@Nonnull Location l) {
        // TODO implement
    }

    /**
     * Consumes an {@link AltarRecipe} from an array of {@link Item}s
     */
    private static void consumeRecipe(CachedItemStack[] inputs, @Nonnull Entity[] items, @Nonnull AltarRecipe recipe) {
        for (Map.Entry<String, Integer> entry : recipe.getEntries()) {

            int rem = entry.getValue();

            for (int i = 0 ; i < inputs.length ; i++) {

                CachedItemStack input = inputs[i];

                if (entry.getKey().equals(input.getIDorType())) {

                    int amt = input.getAmount();

                    if (amt > rem) {
                        input.setAmount(amt - rem);
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