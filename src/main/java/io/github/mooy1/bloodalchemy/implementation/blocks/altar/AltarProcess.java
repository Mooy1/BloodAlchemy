package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import lombok.RequiredArgsConstructor;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.infinitylib.recipes.RecipeOutput;
import io.github.mooy1.infinitylib.recipes.ShapelessRecipe;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

/**
 * An {@link ShapelessRecipe} being processed in an {@link BloodAltar}
 */
@RequiredArgsConstructor
final class AltarProcess implements Runnable {

    private static final int INTERVAL = SlimefunPlugin.getTickerTask().getTickRate();
    private static final int TICKS = 10;

    private final BloodAltar altar;
    private final RecipeOutput<ItemStack> output;
    private final Location location;
    private int remaining = TICKS;

    @Override
    public void run() {
        if (!BlockStorage.check(this.location, this.altar.getId())) {
            // Cancel, drop recipe
            World world = this.location.getWorld();
            if (world != null) {
                for (ItemStack item : this.output.getRecipeInput()) {
                    world.dropItemNaturally(this.location, item);
                }
            }

        } else if (--this.remaining == 0) {
            // Done
            this.altar.onCraftFinish(this.location);

            World world = this.location.getWorld();
            if (world != null) {
                world.dropItemNaturally(this.location, this.output.getOutput().clone());
            }

        } else {
            // Process
            this.altar.onCraftProcess(this.location);
            BloodAlchemy.inst().runSync(this, INTERVAL);
        }
    }

}
