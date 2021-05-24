package io.github.mooy1.bloodharvest.implementation.blocks.alchemy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import io.github.mooy1.bloodharvest.implementation.blocks.BloodAltar;

/**
 * An {@link AlchemyRecipe} being processed in the {@link BloodAltar}
 */
@Getter
@RequiredArgsConstructor
final class AlchemyProcess {

    private final AlchemyRecipe recipe;
    private int ticks;

    int increment() {
        return this.ticks++;
    }

}