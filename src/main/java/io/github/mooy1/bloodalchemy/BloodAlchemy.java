package io.github.mooy1.bloodalchemy;

import javax.annotation.Nonnull;

import org.bukkit.Material;

import io.github.mooy1.bloodalchemy.implementation.Blocks;
import io.github.mooy1.bloodalchemy.implementation.Items;
import io.github.mooy1.bloodalchemy.implementation.Tools;
import io.github.mooy1.infinitylib.AbstractAddon;
import io.github.mooy1.infinitylib.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public final class BloodAlchemy extends AbstractAddon {
    
    private static BloodAlchemy instance;
    
    public static BloodAlchemy inst() {
        return instance;
    }
    
    @Override
    public void onEnable() {
        instance = this;

        // All of the config and auto update stuff is taken care of in AbstractAddon#onEnable
        super.onEnable();

        Category category = new Category(getKey("blood_alchemy"),
                new CustomItem(Material.NETHER_WART_BLOCK, "&4Blood Alchemy"));

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
        return new Metrics(this, 11483);
    }

    @Nonnull
    @Override
    protected String getGithubPath() {
        return "Mooy1/BloodAlchemy/master";
    }
    
}
