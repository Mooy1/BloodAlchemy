package io.github.mooy1.bloodharvest.implementation.blocks.altar;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

/**
 * A recipe in the {@link BloodAltar} with an output
 */
@Getter
final class BloodAltarRecipe extends BloodAltarInput {

    private final List<ItemStack> inputs = new ArrayList<>();
    private final ItemStack output;

    BloodAltarRecipe(ItemStack[] recipe, ItemStack output) {
        super(recipe);
        for (ItemStack item : recipe) {
            if (item != null) {
                this.inputs.add(item);
            }
        }
        this.output = output;
    }

}
