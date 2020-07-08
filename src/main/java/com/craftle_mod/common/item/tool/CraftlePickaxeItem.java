package com.craftle_mod.common.item.tool;

import com.craftle_mod.common.CraftleCreativeTabs;
import com.craftle_mod.common.item.base.ICraftleToolItem;
import com.craftle_mod.common.tier.CraftleToolTier;
import javax.annotation.Nonnull;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class CraftlePickaxeItem extends PickaxeItem implements ICraftleToolItem {

    private final String resourceName;
    private final CraftleToolTier tier;

    public CraftlePickaxeItem(int attackDamage, float attackSpeed, CraftleToolTier tier) {
        super(tier, attackDamage, attackSpeed,
            new Item.Properties().group(CraftleCreativeTabs.CRAFTLE_ITEM_GROUP_TOOLS));
        this.resourceName = tier.getMaterialName() + "_pickaxe";
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
