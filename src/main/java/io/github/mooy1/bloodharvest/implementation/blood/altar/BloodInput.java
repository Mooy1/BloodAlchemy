package io.github.mooy1.bloodharvest.implementation.blood.altar;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import io.github.mooy1.infinitylib.items.StackUtils;

class BloodInput {

    private final Map<String, Integer> map = new HashMap<>();
    private final int hash;

    BloodInput(ItemStack[] recipe) {
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
        if (!(obj instanceof BloodInput)) {
            return false;
        }
        for (Map.Entry<String, Integer> entry : ((BloodInput) obj).map.entrySet()) {
            if (this.map.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

}
