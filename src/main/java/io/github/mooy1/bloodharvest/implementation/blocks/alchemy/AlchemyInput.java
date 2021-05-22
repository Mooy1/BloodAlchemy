package io.github.mooy1.bloodharvest.implementation.blocks.alchemy;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.items.StackUtils;

/**
 * An input to an {@link AbstractAlchemyCrafter} to be used as a key in a map
 */
public class AlchemyInput {

    private final Map<String, Integer> map = new HashMap<>();
    private final int hash;

    AlchemyInput(ItemStack[] recipe) {
        int hash = 0;
        for (ItemStack item : recipe) {
            if (item != null) {
                String id = StackUtils.getIDorType(item);
                hash += id.hashCode();
                this.map.compute(id, (k, v) -> v == null ? item.getAmount() : v + item.getAmount());
            }
        }
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AlchemyInput)) {
            return false;
        }
        for (Map.Entry<String, Integer> entry : ((AlchemyInput) obj).map.entrySet()) {
            if (this.map.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

}
