package io.github.mooy1.bloodalchemy;

import javax.annotation.Nonnull;

import io.github.mooy1.bloodalchemy.implementation.Items;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;

public final class BloodAlchemy extends AbstractAddon {
    
    private static BloodAlchemy instance;
    
    public static BloodAlchemy inst() {
        return instance;
    }
    
    @Override
    protected void onAddonEnable() {
        // All of the config and auto update stuff is taken care of in AbstractAddon#onEnable
        Items.setup(instance = this);
    }

    @Override
    protected void onAddonDisable() {
        instance = null;
    }

    @Override
    protected Metrics setupMetrics() {
        return new Metrics(this, 11483);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Mooy1/BloodAlchemy/master";
    }
    
}
