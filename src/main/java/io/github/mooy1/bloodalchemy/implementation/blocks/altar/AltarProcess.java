package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

/**
 * An {@link AltarRecipe} being processed in an {@link BloodAltar}
 */
final class AltarProcess implements Runnable {

    private static final int INTERVAL = 12;

    private final BloodAltar altar;
    private final AltarRecipe recipe;
    private final Location location;
    private int ticks;

    AltarProcess(@Nonnull BloodAltar altar, @Nonnull AltarRecipe recipe, @Nonnull Location location) {
        this.altar = altar;
        this.recipe = recipe;
        this.location = location;

        altar.onCraftStart(this.location);
        BloodAlchemy.inst().runSync(this, INTERVAL);
    }

    @Override
    public void run() {
        if (!BlockStorage.check(this.location, this.altar.getId())) {
            // Cancel
            World world = this.location.getWorld();
            for (ItemStack item : this.recipe.getInputs()) {
                world.dropItemNaturally(this.location, item);
            }

        } else if (++this.ticks == this.recipe.size()) {
            // Done
            this.altar.onCraftFinish(this.location);
            this.location.getWorld().dropItemNaturally(this.location, this.recipe.getOutput());

        } else {
            // Process
            this.altar.onCraftProcess(this.location);
            BloodAlchemy.inst().runSync(this, INTERVAL);
        }
    }

}
