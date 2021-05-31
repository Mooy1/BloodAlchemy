package io.github.mooy1.bloodalchemy.implementation.tools;

import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class BloodTotem extends SlimefunItem implements Listener {

    public BloodTotem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        BloodAlchemy.inst().registerListener(this);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onKill(@Nonnull EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();

        if (p == null) {
            return;
        }

        checkTotem(p, (totem, totemMeta) -> {
            int blood = BloodUtils.getStored(totemMeta);

            if (blood < BloodUtils.MAX_STORED) {
                // Store 20 blood
                BloodUtils.setStored(totemMeta, Math.min(BloodUtils.MAX_STORED, blood + 20));
                BloodUtils.playEffect(e.getEntity().getLocation(), 20);
                totem.setItemMeta(totemMeta);
            }
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onHit(@Nonnull EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();

        checkTotem(p, (totem, totemMeta) -> {
            int blood = BloodUtils.getStored(totemMeta);

            if (blood < BloodUtils.MAX_STORED) {
                // Store 4 blood
                BloodUtils.setStored(totemMeta, Math.min(BloodUtils.MAX_STORED, blood + 4));
                BloodUtils.playEffect(e.getEntity().getLocation(), 20);
                totem.setItemMeta(totemMeta);

                if (blood > 95) {
                    p.sendMessage(ChatColor.GREEN + "Your blood totem is ready!");
                }
            }
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onTotem(@Nonnull EntityResurrectEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            checkTotem(p, (totem, totemMeta) -> {

                int blood = BloodUtils.getStored(totemMeta);

                if (blood == BloodUtils.MAX_STORED) {
                    // Revive and reset blood
                    BloodUtils.setStored(totemMeta, 0);
                    BloodUtils.playEffect(p.getLocation(), 100);
                    totem.setItemMeta(totemMeta);

                    // Give back totem after they revive
                    BloodAlchemy.inst().runSync(() -> {
                        totem.setAmount(1);
                        p.getInventory().setItemInOffHand(totem);
                    });
                } else {
                    // Don't revive
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "You need 100 blood to revive, " + BloodUtils.getStoredString(blood));
                }
            });
        }
    }

    private void checkTotem(@Nonnull Player p, @Nonnull BiConsumer<ItemStack, ItemMeta> consumer) {
        ItemStack totem = p.getInventory().getItemInOffHand();

        // No meta, not a totem
        if (totem.hasItemMeta()) {
            ItemMeta totemMeta = totem.getItemMeta();

            // Make sure its a totem
            if (getId().equals(StackUtils.getID(totemMeta))) {
                consumer.accept(totem, totemMeta);
            }
        }
    }

}
