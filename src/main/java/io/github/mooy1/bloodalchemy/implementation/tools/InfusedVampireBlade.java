package io.github.mooy1.bloodalchemy.implementation.tools;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.mooy1.bloodalchemy.BloodAlchemy;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.weapons.VampireBlade;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

/**
 * An upgrade to the {@link VampireBlade} which allows the player to teleport short distances
 */
public final class InfusedVampireBlade extends SlimefunItem implements NotPlaceable, Listener {

    public InfusedVampireBlade(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        BloodAlchemy.inst().registerListener(this);

        addItemHandler(getKillHandler(), getUseHandler());
    }

    private EntityKillHandler getKillHandler() {
        return (e, entity, killer, item) -> {

            ItemMeta meta = item.getItemMeta();
            int blood = BloodUtils.getStored(meta);

            if (blood < BloodUtils.MAX_STORED) {
                // Store 40 blood
                BloodUtils.setStored(meta, Math.min(BloodUtils.MAX_STORED, blood + 40));
                BloodUtils.playEffect(killer.getLocation(), 20);
                item.setItemMeta(meta);
            }
        };
    }

    private ItemUseHandler getUseHandler() {
        return e -> {
            Player p = e.getPlayer();

            ItemMeta meta = e.getItem().getItemMeta();
            int blood = BloodUtils.getStored(meta);

            // Get the block 8 meters from where the player faces
            Location l = p.getLocation();
            l.add(l.getDirection().multiply(8));

            if (!l.getBlock().isEmpty()) {
                p.sendMessage(ChatColor.RED + "You cannot teleport into a solid block!");
                return;
            }

            if (blood >= 20) {
                // Take 20 blood and teleport
                BloodUtils.setStored(meta, blood - 20);
                BloodUtils.playEffect(p.getLocation(), 20);
                e.getItem().setItemMeta(meta);
                p.teleport(l);

            } else {
                p.sendMessage(ChatColor.RED + "You need at least 20 blood to teleport, " + BloodUtils.getStoredString(blood));
            }
        };
    }

    // Replace with WeaponUseHandler once merged
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    private void onAttack(@Nonnull EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) {
            return;
        }

        Player p = (Player) e.getDamager();
        ItemStack item = p.getInventory().getItemInMainHand();

        if (!item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (getId().equals(StackUtils.getID(meta)) && canUse(p, true)) {

            int blood = BloodUtils.getStored(meta);

            if (blood < BloodUtils.MAX_STORED) {
                // Store 5 blood
                BloodUtils.setStored(meta, Math.min(BloodUtils.MAX_STORED, blood + 5));
                BloodUtils.playEffect(p.getLocation(), 10);
                item.setItemMeta(meta);
            }
        }
    }

}
