package com.craftle_mod.common;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CraftleCreativeTab extends ItemGroup {

    public CraftleCreativeTab(String label) {
        super(Craftle.MODID);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return Items.DIAMOND.getDefaultInstance();
    }
}
