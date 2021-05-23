package io.github.mooy1.bloodharvest;

import javax.annotation.Nonnull;

import org.bukkit.Material;

import io.github.mooy1.bloodharvest.implementation.Blocks;
import io.github.mooy1.bloodharvest.implementation.Items;
import io.github.mooy1.bloodharvest.implementation.Tools;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public final class BloodHarvest extends AbstractAddon {
    
    private static BloodHarvest instance;
    
    public static BloodHarvest inst() {
        return instance;
    }
    
    @Override
    public void onEnable() {
        instance = this;

        // All of the config and auto update stuff is taken care of in AbstractAddon#onEnable
        super.onEnable();

        Category category = new Category(getKey("blood_harvest"),
                new CustomItem(Material.NETHER_WART_BLOCK, "&4Blood Harvest"));

        Blocks.setup(this, category);
        Tools.setup(this, category);
        Items.setup(this, category);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    protected Metrics setupMetrics() {
        return new Metrics(this, 11453);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Mooy1/BloodHarvest/master";
    }
    
}
