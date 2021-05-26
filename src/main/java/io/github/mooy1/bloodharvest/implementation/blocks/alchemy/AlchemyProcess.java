package io.github.mooy1.bloodharvest.implementation.blocks.alchemy;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

/**
 * An {@link AlchemyRecipe} being processed in an {@link AbstractAlchemyAltar}
 */
final class AlchemyProcess implements Runnable {

    private static final int INTERVAL = 12;

    private final AbstractAlchemyAltar altar;
    private final AlchemyRecipe recipe;
    private final Location location;
    private int ticks;

    AlchemyProcess(@Nonnull AbstractAlchemyAltar altar, @Nonnull AlchemyRecipe recipe, @Nonnull Location location) {
        this.altar = altar;
        this.recipe = recipe;
        this.location = location;

        altar.onCraftStart(this.location);
        BloodHarvest.inst().runSync(this, INTERVAL);
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
            BloodHarvest.inst().runSync(this, INTERVAL);
        }
    }

}
