package com.craftle_mod.common.item.tool;

import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.base.ICraftleToolItem;
import com.craftle_mod.common.tier.CraftleItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;

public class CraftleShovelItem extends ShovelItem implements ICraftleToolItem {

    private final String          resourceName;
    private final CraftleItemTier tier;

    public CraftleShovelItem(float attackDamage, float attackSpeed,
                             CraftleItemTier tier) {
        super(tier, attackDamage, attackSpeed, new Item.Properties()
                .group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_TOOLS));
        this.resourceName = tier.getMaterialName() + "_shovel";
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
