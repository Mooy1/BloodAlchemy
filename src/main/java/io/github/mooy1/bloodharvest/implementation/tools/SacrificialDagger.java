package io.github.mooy1.bloodharvest.implementation.tools;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.Items;
import io.github.mooy1.bloodharvest.implementation.Tools;
import io.github.mooy1.bloodharvest.util.BloodUtil;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class SacrificialDagger extends SlimefunItem {

    public static final RecipeType TYPE = new RecipeType(BloodHarvest.inst().getKey("sacrificial_dagger"), Tools.SACRIFICIAL_DAGGER);

    public SacrificialDagger(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler((ItemUseHandler) e -> {

            e.setUseItem(Event.Result.DENY);
            e.setUseBlock(Event.Result.DENY);

            e.getPlayer().setHealth(Math.min(0, e.getPlayer().getHealth() - 4));
            e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), Items.BLOOD.clone());

            BloodUtil.spawnParticles(e.getPlayer().getLocation(), 20);
            BloodUtil.playSound(e.getPlayer().getLocation());

        }, (EntityKillHandler) (e, entity, killer, item1) -> {

            if (ThreadLocalRandom.current().nextBoolean()) {
                Location l = entity.getLocation();

                entity.getWorld().dropItemNaturally(l, Items.BLOOD.clone());

                BloodUtil.spawnParticles(l, 20);
                BloodUtil.playSound(l);
            }

        });
    }

}
