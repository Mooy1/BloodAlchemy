package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.infinitylib.recipes.RecipeOutput;
import io.github.mooy1.infinitylib.recipes.ShapelessRecipe;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

/**
 * An {@link ShapelessRecipe} being processed in an {@link BloodAltar}
 */
final class AltarProcess implements Runnable {

    private static final int INTERVAL = 12;

    private final BloodAltar altar;
    private final RecipeOutput<ItemStack> output;
    private final Location location;
    private int remaining;

    AltarProcess(@Nonnull BloodAltar altar, @Nonnull RecipeOutput<ItemStack> output, @Nonnull Location location) {
        this.altar = altar;
        this.output = output;
        this.location = location;
        this.remaining = output.getRecipeInput().length * 3;
    }

    @Override
    public void run() {
        if (!BlockStorage.check(this.location, this.altar.getId())) {
            // Cancel
            World world = this.location.getWorld();
            for (ItemStack item : this.output.getRecipeInput()) {
                world.dropItemNaturally(this.location, item);
            }

        } else if (--this.remaining == 0) {
            // Done
            this.altar.onCraftFinish(this.location);
            this.location.getWorld().dropItemNaturally(this.location, this.output.getOutput().clone());

        } else {
            // Process
            this.altar.onCraftProcess(this.location);
            BloodAlchemy.inst().runSync(this, INTERVAL);
        }
    }

}
