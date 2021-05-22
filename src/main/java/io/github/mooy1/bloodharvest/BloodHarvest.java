package io.github.mooy1.bloodharvest;

import javax.annotation.Nonnull;

import io.github.mooy1.bloodharvest.implementation.Items;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;

public final class BloodHarvest extends AbstractAddon {
    
    private static BloodHarvest instance;
    
    public static BloodHarvest inst() {
        return instance;
    }
    
    @Override
    public void onEnable() {
        instance = this;

        super.onEnable();

        Items.setup(this);
    }

    @Override
    protected Metrics setupMetrics() {
        return null;
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Mooy1/BloodArcana/master";
    }

    @Override
    public void onDisable() {
        instance = null;
    }
    
}
