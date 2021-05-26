package io.github.mooy1.bloodalchemy.implementation.tools;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.Items;
import io.github.mooy1.bloodalchemy.implementation.Tools;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class SacrificialDagger extends SlimefunItem {

    public static final RecipeType TYPE = new RecipeType(BloodAlchemy.inst().getKey("sacrificial_dagger"), Tools.SACRIFICIAL_DAGGER);

    public SacrificialDagger(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(getUseHandler(), getKillHandler());

        // TODO hit handler
    }

    private ItemUseHandler getUseHandler() {
        return e -> {
            e.setUseItem(Event.Result.DENY);
            e.setUseBlock(Event.Result.DENY);

            Player p = e.getPlayer();
            Location l = p.getLocation();

            p.setHealth(Math.min(0, p.getHealth() - 4));
            p.getWorld().dropItemNaturally(l, Items.BLOOD.clone());

            BloodUtils.spawnParticles(l, 20, 2);
            BloodUtils.playSound(l);
        };
    }

    private EntityKillHandler getKillHandler() {
        return (e, entity, killer, item1) -> {
            if (ThreadLocalRandom.current().nextBoolean()) {
                Location l = entity.getLocation();

                entity.getWorld().dropItemNaturally(l, Items.BLOOD.clone());

                BloodUtils.spawnParticles(l, 20, 2);
                BloodUtils.playSound(l);
            }
        };
    }

}
