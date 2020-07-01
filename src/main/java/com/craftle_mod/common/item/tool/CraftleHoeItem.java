package com.craftle_mod.common.item.tool;

import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.base.ICraftleToolItem;
import com.craftle_mod.common.tier.CraftleToolTier;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class CraftleHoeItem extends HoeItem implements ICraftleToolItem {

    private final String resourceName;
    private final CraftleToolTier tier;

    public CraftleHoeItem(float attackSpeed, CraftleToolTier tier) {

        super(tier, attackSpeed, new Item.Properties()
                .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_TOOLS));
        this.resourceName = tier.getMaterialName() + "_hoe";
        this.tier = tier;
    }

    @Override
    public String getCraftleRegistryName() {
        return resourceName;
    }

    @Nonnull
    @Override
    public CraftleToolTier getTier() {
        return tier;
    }
}
