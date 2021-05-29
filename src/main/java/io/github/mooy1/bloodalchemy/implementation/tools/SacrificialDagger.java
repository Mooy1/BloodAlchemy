package io.github.mooy1.bloodalchemy.implementation.tools;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.implementation.Items;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.weapons.VampireBlade;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class SacrificialDagger extends VampireBlade {

    public static final RecipeType TYPE = new RecipeType(BloodAlchemy.inst().getKey("sacrificial_dagger"), Items.SACRIFICIAL_DAGGER);

    public SacrificialDagger(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(getUseHandler(), getKillHandler());
    }

    @Override
    public int getChance() {
        return 50;
    }

    @Override
    public void heal(@Nonnull Player p) {
        Location l = p.getLocation();

        BloodUtils.dropBlood(l, 1);
        BloodUtils.playEffect(l, 10);
    }

    private ItemUseHandler getUseHandler() {
        return e -> {
            e.setUseItem(Event.Result.DENY);
            e.setUseBlock(Event.Result.DENY);

            Player p = e.getPlayer();
            Location l = p.getLocation();

            p.setHealth(Math.max(0, p.getHealth() - 4));

            BloodUtils.dropBlood(l, 1);
            BloodUtils.playEffect(l, 20);
        };
    }

    private EntityKillHandler getKillHandler() {
        return (e, entity, killer, item1) -> {
            Location l = entity.getLocation();

            BloodUtils.dropBlood(l, 2);
            BloodUtils.playEffect(l, 20);
        };
    }

}
