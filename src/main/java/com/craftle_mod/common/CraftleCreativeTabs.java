package com.craftle_mod.common;

import com.craftle_mod.common.registries.CraftleItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class CraftleCreativeTabs {

    public static final ItemGroup CRAFTLE_ITEM_GROUP_MACHINES  =
            new CraftleItemGroup(Craftle.MODID + "_machines",
                                 () -> new ItemStack(
                                         CraftleItems.SPECIAL_ITEM.get()));
    public static final ItemGroup CRAFTLE_ITEM_GROUP_RESOURCES =
            new CraftleItemGroup(Craftle.MODID + "_resources",
                                 () -> new ItemStack(
                                         CraftleItems.SPECIAL_ITEM.get()));
    public static final ItemGroup CRAFTLE_ITEM_GROUP_MISC      =
            new CraftleItemGroup(Craftle.MODID + "_misc", () -> new ItemStack(
                    CraftleItems.SPECIAL_ITEM.get()));
    public static final ItemGroup CRAFTLE_ITEM_GROUP_TOOLS     =
            new CraftleItemGroup(Craftle.MODID + "_tools", () -> new ItemStack(
                    CraftleItems.SPECIAL_ITEM.get()));

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
