package com.craftle_mod.common;

import com.craftle_mod.common.registries.CraftleItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class CraftleCreativeTabs {

    public static final ItemGroup CRAFTLE_ITEM_GROUP =
            new CraftleItemGroup(Craftle.MODID, () -> new ItemStack(
                    CraftleItems.TEST_INGOT.get()));

    private static class CraftleItemGroup extends ItemGroup {

        private final Supplier<ItemStack> iconSupplier;

        public CraftleItemGroup(final String name,
                                final Supplier<ItemStack> iconSupplier) {
            super(name);
            this.iconSupplier = iconSupplier;
        }

        @Override
        public ItemStack createIcon() {
            return iconSupplier.get();
        }
    }
}
