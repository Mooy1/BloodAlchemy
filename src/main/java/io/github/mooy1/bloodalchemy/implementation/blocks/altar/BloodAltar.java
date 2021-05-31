package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import java.util.Collection;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.Items;
import io.github.mooy1.infinitylib.players.CoolDownMap;
import io.github.mooy1.infinitylib.recipes.RecipeMap;
import io.github.mooy1.infinitylib.recipes.RecipeOutput;
import io.github.mooy1.infinitylib.recipes.ShapelessRecipe;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An abstract item which crafts from items dropped in the world
 */
public final class BloodAltar extends SlimefunItem {

    private static final RecipeMap<ItemStack> RECIPES = new RecipeMap<>(ShapelessRecipe::new);

    public static final RecipeType TYPE = new RecipeType(BloodAlchemy.inst().getKey("blood_altar"), Items.BLOOD_ALTAR, RECIPES::put);

    private final CoolDownMap coolDowns = new CoolDownMap(200);

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

            if (this.coolDowns.checkAndReset(e.getPlayer().getUniqueId())) {
                findRecipe(e.getClickedBlock().get().getLocation(), e.getPlayer());
            }
        };
    }

    /**
     * Finds a recipe from items on the ground and starts the process
     */
    private void findRecipe(@Nonnull Location l, @Nonnull Player p) {
        World w = p.getWorld();

        Collection<Entity> items = w.getNearbyEntities(l, 2, 2, 2, e -> e instanceof Item);
        if (items.size() == 0) {
            p.sendMessage(ChatColor.RED + "Drop items near the alter!");
            return;
        }

        ItemStack[] input = new ItemStack[items.size()];
        int i = 0;
        for (Entity e : items) {
            input[i++] = ((Item) e).getItemStack();
        }

        RecipeOutput<ItemStack> output = RECIPES.get(input);
        if (output == null) {
            p.sendMessage(ChatColor.RED + "Invalid Recipe!");
            return;
        }

        output.consumeInput();

        w.playSound(l, Sound.BLOCK_END_PORTAL_SPAWN, 1, 1);
        w.spawnParticle(Particle.PORTAL, l, 50);

        // Start processing the recipe
        BloodAlchemy.inst().runSync(new AltarProcess(this, output, l));
    }

}
