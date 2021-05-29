package io.github.mooy1.bloodalchemy.implementation.tools;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.weapons.VampireBlade;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class InfusedVampireBlade extends VampireBlade {

    public InfusedVampireBlade(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(getKillHandler(), getUseHandler());
    }

    @Override
    public int getChance() {
        return 100;
    }

    @Override
    public void heal(@Nonnull Player p) {
        super.heal(p);

        ItemStack item = p.getInventory().getItemInMainHand();

        ItemMeta meta = item.getItemMeta();

        int blood = BloodUtils.getStored(meta);

        if (blood < BloodUtils.MAX_STORED) {
            BloodUtils.setStored(meta, Math.min(BloodUtils.MAX_STORED, blood + 5));
            item.setItemMeta(meta);

            BloodUtils.playEffect(p.getLocation(), 10);
        }
    }

    private EntityKillHandler getKillHandler() {
        return (e, entity, killer, item) -> {
            ItemMeta meta = item.getItemMeta();

            int blood = BloodUtils.getStored(meta);

            if (blood < BloodUtils.MAX_STORED) {
                BloodUtils.setStored(meta, Math.min(BloodUtils.MAX_STORED, blood + 40));
                item.setItemMeta(meta);

                BloodUtils.playEffect(killer.getLocation(), 20);
            }
        };
    }

    private ItemUseHandler getUseHandler() {
        return e -> {
            Player p = e.getPlayer();

            ItemMeta meta = e.getItem().getItemMeta();

            int blood = BloodUtils.getStored(meta);

            // Teleport the player 8 blocks forward
            Location l = p.getLocation();
            l.add(l.getDirection().multiply(8));

            if (l.getBlock().isEmpty() && blood >= 20) {
                BloodUtils.setStored(meta, blood - 20);
                e.getItem().setItemMeta(meta);
                p.teleport(l);

                BloodUtils.playEffect(p.getLocation(), 20);
            }
        };
    }

}
