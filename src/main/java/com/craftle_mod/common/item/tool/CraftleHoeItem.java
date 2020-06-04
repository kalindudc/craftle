package com.craftle_mod.common.item.tool;

import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.base.ICraftleToolItem;
import com.craftle_mod.common.tier.CraftleItemTier;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;

public class CraftleHoeItem extends HoeItem implements ICraftleToolItem {

    private final String          resourceName;
    private final CraftleItemTier tier;

    public CraftleHoeItem(float attackSpeed, CraftleItemTier tier) {

        super(tier, attackSpeed, new Item.Properties()
                .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_TOOLS));
        this.resourceName = tier.getMaterialName() + "_hoe";
        this.tier         = tier;
    }

    @Override
    public String getCraftleRegistryName() {
        return resourceName;
    }

    @Override
    public CraftleItemTier getTier() {
        return tier;
    }
}
