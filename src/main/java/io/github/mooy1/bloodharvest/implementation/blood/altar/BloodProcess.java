package io.github.mooy1.bloodharvest.implementation.blood.altar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * An {@link BloodRecipe} being processed in the {@link BloodAltar}
 */
@Getter
@RequiredArgsConstructor
final class BloodProcess {

    private final BloodRecipe recipe;
    private int ticks;

    int increment(int speed) {
        return this.ticks += speed;
    }

}
