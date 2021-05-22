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

        // All the stuff every addon does is taken care of in AbstractAddon#onEnable
        super.onEnable();

        Category category = new Category(getKey("blood_harvest"),
                new CustomItem(Material.NETHER_WART_BLOCK, "&4Blood Harvest"));

        Items.setup(this, category);
        Blocks.setup(this, category);
        Tools.setup(this, category);
    }

    @Override
    protected Metrics setupMetrics() {
        return null;
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Mooy1/BloodHarvest/master";
    }

    @Override
    public void onDisable() {
        instance = null;
    }
    
}
