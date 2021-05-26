package io.github.mooy1.bloodalchemy.implementation.tools;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.mooy1.bloodalchemy.implementation.Items;
import io.github.mooy1.bloodalchemy.utils.BloodUtils;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityKillHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class VampireBlade extends SlimefunItem {

    public VampireBlade(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(getKillHandler(), getUseHandler());

        // TODO hit handler
    }

    private EntityKillHandler getKillHandler() {
        return (e, entity, killer, item) -> {
            ItemMeta meta = item.getItemMeta();

            int blood = BloodUtils.getStored(meta);

            if (blood < BloodUtils.MAX_STORED) {
                BloodUtils.setStored(meta, Math.min(BloodUtils.MAX_STORED, blood + 40));
                item.setItemMeta(meta);

                // TODO add sound
            }
        };
    }

    private ItemUseHandler getUseHandler() {
        return e -> {
            Player p = e.getPlayer();

            ItemMeta meta = e.getItem().getItemMeta();

            int blood = BloodUtils.getStored(meta);

            if (p.isSneaking()) {
                // Withdraw blood into item form
                if (blood > 9) {
                    int items = blood / 10;

                    ItemStack remaining = p.getInventory().addItem(new SlimefunItemStack(Items.BLOOD, items)).get(0);
                    if (remaining != null) {
                        items -= remaining.getAmount();
                    }

                    if (items != 0) {
                        BloodUtils.setStored(meta, blood - 10 * items);
                        e.getItem().setItemMeta(meta);
                    }

                    // TODO add sound
                }
            } else {
                // Teleport the player 8 blocks forward
                Location l = p.getLocation();
                l.add(l.getDirection().multiply(8));

                if (l.getBlock().isEmpty() && blood >= 20) {
                    BloodUtils.setStored(meta, blood - 20);
                    e.getItem().setItemMeta(meta);
                    p.teleport(l);

                    // TODO add sound
                }
            }
        };
    }

}
