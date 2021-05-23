package io.github.mooy1.bloodharvest.implementation.tools;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.mooy1.bloodharvest.BloodHarvest;
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
    private void onTotem(EntityResurrectEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getEntity();
        PlayerInventory inv = p.getInventory();

        ItemStack totem = inv.getItemInMainHand();
        int slot = inv.getHeldItemSlot();

        // Check and get the totem
        if (!totem.hasItemMeta() || !getId().equals(StackUtils.getID(totem))) {

            slot = -1;
            totem = inv.getItemInOffHand();

            if (!totem.hasItemMeta() || !getId().equals(StackUtils.getID(totem))) {
                return;
            }
        }

        e.setCancelled(true);

        // Re add the totem after they revive
        int finalSlot = slot;
        ItemStack finalTotem = totem;
        BloodHarvest.inst().runSync(() -> {
            if (finalSlot == -1) {
                BloodHarvest.inst().runSync(() -> inv.setItemInOffHand(finalTotem));
            } else {
                BloodHarvest.inst().runSync(() -> inv.setItem(finalSlot, finalTotem));
            }
        });
    }

}
