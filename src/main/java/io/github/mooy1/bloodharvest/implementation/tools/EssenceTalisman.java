package io.github.mooy1.bloodharvest.implementation.tools;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.mooy1.bloodharvest.BloodHarvest;
import io.github.mooy1.bloodharvest.implementation.Items;
import io.github.mooy1.bloodharvest.implementation.Tools;
import io.github.mooy1.bloodharvest.util.Util;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class EssenceTalisman extends SlimefunItem implements Listener {

    public static final RecipeType TYPE = new RecipeType(BloodHarvest.inst().getKey("essence_talisman"), Tools.ESSENCE_TALISMAN);

    private static final double GROWTH_BASE = 100;
    private static final double GROWTH_INCREASE = 4;

    private final NamespacedKey growthKey = BloodHarvest.inst().getKey("essence_growth");

    public EssenceTalisman(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
        BloodHarvest.inst().registerListener(this);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onBreak(@Nonnull BlockBreakEvent e) {
        ItemStack talisman = getTalisman(e.getPlayer());

        if (talisman != null) {

            ItemStack essence = getEssence(e.getBlock().getType());

            if (essence != null && checkAndImproveChance(talisman)) {
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), essence);
            }
        }
    }

    private boolean checkAndImproveChance(@Nonnull ItemStack talisman) {
        if (!talisman.hasItemMeta()) {
            return false;
        }
        ItemMeta meta =  talisman.getItemMeta();

        if (!meta.hasLore()) {
            return false;
        }

        int growth = meta.getPersistentDataContainer().getOrDefault(this.growthKey, PersistentDataType.INTEGER, 0);

        meta.getPersistentDataContainer().set(this.growthKey, PersistentDataType.INTEGER, growth + 1);

        int chance = Util.getGrowthLevel(growth, GROWTH_BASE, GROWTH_INCREASE);

        List<String> lore = meta.getLore();

        lore.set(2, getChanceLore(chance, growth));

        meta.setLore(lore);

        return ThreadLocalRandom.current().nextInt(100) < chance;
    }

    @Nullable
    private ItemStack getTalisman(@Nonnull Player p) {
        for (ItemStack stack : p.getInventory().getStorageContents()) {
            if (stack == null || stack.getType() == Material.AIR) {
                continue;
            }
            if (SlimefunUtils.isItemSimilar(stack, getItem(), false, false)) {
                return stack;
            }
        }
        return null;
    }

    @Nonnull
    public static String getChanceLore(int chance, int growth) {
        int needed = Util.getNeededGrowth(chance, GROWTH_BASE, GROWTH_INCREASE);
        return "&aEssence Chance: " + chance + "% (" + growth + "/" + needed + ")";
    }

    @Nullable
    private static ItemStack getEssence(Material material) {
        if (SlimefunTag.STONE_VARIANTS.isTagged(material)) {
            return Items.DEEP_ESSENCE.clone();
        }
        if (SlimefunTag.ORES.isTagged(material)) {
            return new SlimefunItemStack(Items.DEEP_ESSENCE, 4);
        }
        if (SlimefunTag.LOGS.isTagged(material) || SlimefunTag.LEAVES.isTagged(material)) {
            return Items.GROWTH_ESSENCE.clone();
        }
        if (SlimefunTag.CROPS.isTagged(material)) {
            return Items.HARVEST_ESSENCE;
        }
        if (SlimefunTag.DIRT_VARIANTS.isTagged(material) || SlimefunTag.SAND.isTagged(material) || material == Material.GRAVEL) {
            return Items.TERRA_ESSENCE.clone();
        }
        return null;
    }

}
