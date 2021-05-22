package io.github.mooy1.bloodharvest.implementation.blood.altar;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

/**
 * A recipe in the {@link BloodAltar} with an output
 */
@Getter
final class BloodRecipe extends BloodInput {

    private final List<ItemStack> inputs = new ArrayList<>();
    private final ItemStack output;

    BloodRecipe(ItemStack[] recipe, ItemStack output) {
        super(recipe);
        for (ItemStack item : recipe) {
            if (item != null) {
                this.inputs.add(item);
            }
        }
        this.output = output;
    }

}
