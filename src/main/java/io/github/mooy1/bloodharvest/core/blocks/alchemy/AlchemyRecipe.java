package io.github.mooy1.bloodharvest.core.blocks.alchemy;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

/**
 * A recipe in the {@link AbstractAlchemyCrafter} with an output
 */
@Getter
public final class AlchemyRecipe extends AlchemyInput {

    private final List<ItemStack> inputs = new ArrayList<>();
    private final ItemStack output;

    public AlchemyRecipe(ItemStack[] recipe, ItemStack output) {
        super(recipe);
        for (ItemStack item : recipe) {
            if (item != null) {
                this.inputs.add(item);
            }
        }
        this.output = output;
    }

}
