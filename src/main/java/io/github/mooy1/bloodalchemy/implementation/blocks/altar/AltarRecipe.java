package io.github.mooy1.bloodalchemy.implementation.blocks.altar;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

/**
 * A recipe in the {@link BloodAltar} with an output
 */
@Getter
final class AltarRecipe extends AltarInput {

    private final List<ItemStack> inputs = new ArrayList<>();
    private final ItemStack output;

    AltarRecipe(ItemStack[] recipe, ItemStack output) {
        super(recipe);
        for (ItemStack item : recipe) {
            if (item != null) {
                this.inputs.add(item);
            }
        }
        this.output = output;
    }

    int size() {
        return this.inputs.size();
    }

}
