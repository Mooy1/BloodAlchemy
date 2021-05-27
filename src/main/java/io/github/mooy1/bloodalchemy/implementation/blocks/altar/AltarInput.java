package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;

import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Multimap;
import io.github.mooy1.infinitylib.items.StackUtils;

/**
 * An input to an {@link BloodAltar} to be used as a key in a recipe map for quick lookup
 */
@AllArgsConstructor
class AltarInput {

    private final Map<String, Integer> map = new HashMap<>(8);
    private final int hash;

    AltarInput(@Nonnull Multimap<String, ItemStack> input) {
        int hash = 0;

        for (Map.Entry<String, Collection<ItemStack>> entry : input.asMap().entrySet()) {
            hash += entry.getKey().hashCode();
            int amt = 0;
            for (ItemStack item : entry.getValue()) {
                amt += item.getAmount();
            }
            this.map.put(entry.getKey(), amt);
        }

        this.hash = hash;
    }

    AltarInput(@Nonnull ItemStack[] input) {
        int hash = 0;

        for (ItemStack item : input) {
            if (item != null) {
                String id = StackUtils.getIDorType(item);
                hash += id.hashCode();
                this.map.compute(id, (k, v) -> v == null ? item.getAmount() : v + item.getAmount());
            }
        }

        this.hash = hash;
    }

    @Nonnull
    public Set<Map.Entry<String, Integer>> getEntries() {
        return this.map.entrySet();
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AltarInput)) {
            return false;
        }
        for (Map.Entry<String, Integer> entry : ((AltarInput) obj).getEntries()) {
            if (this.map.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

}
