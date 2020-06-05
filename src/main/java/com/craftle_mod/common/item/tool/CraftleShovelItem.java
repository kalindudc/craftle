package com.craftle_mod.common.item.tool;

import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.base.ICraftleToolItem;
import com.craftle_mod.common.tier.CraftleToolTier;
import net.minecraft.item.Item;
import net.minecraft.item.ShovelItem;

public class CraftleShovelItem extends ShovelItem implements ICraftleToolItem {

    private final String          resourceName;
    private final CraftleToolTier tier;

    public CraftleShovelItem(float attackDamage, float attackSpeed,
                             CraftleToolTier tier) {
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
    public CraftleToolTier getTier() {
        return tier;
    }
}
