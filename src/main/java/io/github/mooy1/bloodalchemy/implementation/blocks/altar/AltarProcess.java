package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import lombok.RequiredArgsConstructor;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
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

        } else if (--this.remaining <= 0) {
            // Done
            World world = this.location.getWorld();
            if (world != null) {
                world.playSound(this.location, Sound.ITEM_TOTEM_USE, 1, 1);
                world.spawnParticle(Particle.REVERSE_PORTAL, this.location, 50);
                world.dropItemNaturally(this.location, this.output.getOutput().clone());
            }

        } else {
            // Process
            BloodUtils.playEffect(this.location, 10);
            BloodAlchemy.inst().runSync(this, INTERVAL);
        }
    }

}
