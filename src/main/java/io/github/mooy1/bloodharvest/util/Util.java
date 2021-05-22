package io.github.mooy1.bloodharvest.util;

import lombok.experimental.UtilityClass;

/**
 * Utility methods for this addon
 */
@UtilityClass
public final class Util {

    /**
     * @return A 'level' determined by the 'growth'
     */
    public static int getGrowthLevel(int growth, double base, double increase) {
        return 1 + (int) (Math.log(growth / base) / Math.log(increase));
    }

    /**
     * @return The needed 'growth' to reach the specified 'level'
     */
    public static int getNeededGrowth(int currentLevel, double base, double increase) {
        return (int) (base * Math.pow(increase, currentLevel));
    }

}
