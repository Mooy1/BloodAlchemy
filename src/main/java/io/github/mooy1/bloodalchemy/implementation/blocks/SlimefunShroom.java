package io.github.mooy1.bloodalchemy.implementation.blocks;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * A mushroom that applies effects to nearby players
 */
public final class SlimefunShroom extends SlimefunItem implements Listener {

    private final PotionEffect effect;
    private final Particle particle;

    public SlimefunShroom(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe,
                          PotionEffect effect, Particle particle) {
        super(category, item, recipeType, recipe);
        this.effect = effect;
        this.particle = particle;

        addItemHandler(getTicker());

        BloodAlchemy.inst().registerListener(this);
    }

    private BlockTicker getTicker() {
        return new BlockTicker() {

            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                SlimefunShroom.this.tick(b);
            }

        };
    }

    private void tick(@Nonnull Block b) {
        // Once every 20ish sec by default
        if (ThreadLocalRandom.current().nextInt(30) == 0) {
            b.getWorld().spawnParticle(this.particle, b.getLocation(), 10, .25, .25, .25);

            // Apply effects
            Collection<Entity> entities = b.getWorld().getNearbyEntities(b.getLocation(), 4, 4, 4, e -> e instanceof LivingEntity);
            for (Entity entity : entities) {
                ((LivingEntity) entity).addPotionEffect(this.effect);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onMushroomSpread(@Nonnull BlockSpreadEvent e) {
        // Store this item on spread mushrooms
        if (BlockStorage.check(e.getSource(), getId())) {
            BlockStorage.store(e.getBlock(), getId());
        }
    }
    
}
