package io.github.mooy1.bloodalchemy.implementation.blocks;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A mushroom that applies effects to nearby players and grows to nearby normal mushrooms
 */
public final class GrowingShroom extends SlimefunItem {

    private final BlockFace[] nearby = new BlockFace[] {
            BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH
    };

    private final PotionEffect effect;
    private final Particle particle;

    public GrowingShroom(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                         PotionEffect effect, Particle particle) {
        super(category, item, recipeType, recipe);

        this.effect = effect;
        this.particle = particle;

        addItemHandler(getTicker());
    }

    private BlockTicker getTicker() {
        return new BlockTicker() {

            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                if (ThreadLocalRandom.current().nextInt(30) == 0) {
                    GrowingShroom.this.growAndApplyEffects(b);
                }
            }

        };
    }

    private void growAndApplyEffects(@Nonnull Block b) {

        // Do particles
        b.getWorld().spawnParticle(this.particle, b.getLocation(), 5, .25, .25, .25);

        // Grow to nearby
        for (BlockFace face : this.nearby) {
            Block other = b.getRelative(face);

            if (other.getType() == b.getType() && BlockStorage.checkID(other) == null) {
                BlockStorage.store(other, getId());
                break;
            }
        }

        // Apply effects
        Collection<Entity> entities = b.getWorld().getNearbyEntities(b.getLocation(), 4, 4, 4, e -> e instanceof LivingEntity);

        for (Entity entity : entities) {
            ((LivingEntity) entity).addPotionEffect(this.effect);
        }
    }
    
}
