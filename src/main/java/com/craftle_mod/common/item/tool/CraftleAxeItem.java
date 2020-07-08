package com.craftle_mod.common.item.tool;

import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.base.ICraftleToolItem;
import com.craftle_mod.common.tier.CraftleToolTier;
import javax.annotation.Nonnull;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;

public class CraftleAxeItem extends AxeItem implements ICraftleToolItem {

    private final String resourceName;
    private final CraftleToolTier tier;

    public CraftleAxeItem(float attackDamage, float attackSpeed, CraftleToolTier tier) {
        super(tier, attackDamage, attackSpeed,
            new Item.Properties().group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_TOOLS));
        this.resourceName = tier.getMaterialName() + "_axe";
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
