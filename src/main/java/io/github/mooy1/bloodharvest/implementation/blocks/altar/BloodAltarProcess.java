package io.github.mooy1.bloodharvest.implementation.blocks.altar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An {@link BloodAltarRecipe} being processed in the {@link BloodAltar}
 */
@Getter
@RequiredArgsConstructor
final class BloodAltarProcess {

    private final BloodAltarRecipe recipe;
    private int ticks;

    int increment() {
        return this.ticks++;
    }

}
