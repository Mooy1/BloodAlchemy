package io.github.mooy1.bloodharvest.implementation.tools;


import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.utils.BloodUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class BloodTotem extends SlimefunItem implements Listener {

    public BloodTotem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        BloodHarvest.inst().registerListener(this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onHit(@Nonnull EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();

        ItemStack totem = p.getInventory().getItemInOffHand();

        // No meta, not a totem
        if (!totem.hasItemMeta()) {
            return;
        }

        ItemMeta totemMeta = totem.getItemMeta();

        // Make sure it his a totem
        if (!getId().equals(StackUtils.getID(totemMeta))) {
            return;
        }

        int blood = BloodUtils.getStored(totemMeta);

        if (blood < BloodUtils.MAX_STORED) {
            Location l = e.getEntity().getLocation();

            BloodUtils.spawnParticles(l, 20, 2);
            BloodUtils.playSound(l);
            BloodUtils.setStored(totemMeta, Math.min(BloodUtils.MAX_STORED, blood + 4));

            totem.setItemMeta(totemMeta);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onTotem(@Nonnull EntityResurrectEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();

        ItemStack totem = p.getInventory().getItemInOffHand();

        // No meta, not a totem
        if (!totem.hasItemMeta()) {
            return;
        }

        ItemMeta totemMeta = totem.getItemMeta();

        // Make sure it his a totem
        if (!getId().equals(StackUtils.getID(totemMeta))) {
            return;
        }

        int blood = BloodUtils.getStored(totemMeta);

        if (blood < BloodUtils.MAX_STORED) {
            // Don't revive
            BloodUtils.spawnParticles(p.getLocation(), 100, 2);
            BloodUtils.playSound(p.getLocation());

            e.setCancelled(true);
        } else {
            BloodUtils.setStored(totemMeta, 0);
            BloodUtils.spawnParticles(p.getLocation(), 100, 2);

            // Update the meta
            totem.setItemMeta(totemMeta);

            // Re add the totem after they revive
            BloodHarvest.inst().runSync(() -> p.getInventory().setItemInOffHand(totem));
        }
    }

}
