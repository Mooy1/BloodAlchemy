package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.items.StackUtils;

/**
 * An input to an {@link BloodAltar} to be used as a key in a recipe map for quick lookup
 */
public class AltarInput {

    private final Map<String, Integer> map = new HashMap<>(8);
    private final int hash;

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
