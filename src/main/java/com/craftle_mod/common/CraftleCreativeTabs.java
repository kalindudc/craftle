package com.craftle_mod.common;

import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleItems;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CraftleCreativeTabs {

    public static final ItemGroup CRAFTLE_ITEM_GROUP_MACHINES =
        new CraftleItemGroup(Craftle.MODID + "_machines",
            () -> new ItemStack(CraftleBlocks.COAL_GENERATOR.get()));
    public static final ItemGroup CRAFTLE_ITEM_GROUP_RESOURCES =
        new CraftleItemGroup(Craftle.MODID + "_resources",
            () -> new ItemStack(CraftleItems.PLATINUM_INGOT.get()));
    public static final ItemGroup CRAFTLE_ITEM_GROUP_MISC =
        new CraftleItemGroup(Craftle.MODID + "_misc",
            () -> new ItemStack(CraftleItems.TEST_FOOD.get()));
    public static final ItemGroup CRAFTLE_ITEM_GROUP_TOOLS =
        new CraftleItemGroup(Craftle.MODID + "_tools",
            () -> new ItemStack(CraftleItems.RUBY_PICKAXE.get()));

    private static class CraftleItemGroup extends ItemGroup {

        private final Supplier<ItemStack> iconSupplier;

        public CraftleItemGroup(final String name, final Supplier<ItemStack> iconSupplier) {
            super(name);
            this.iconSupplier = iconSupplier;
        }

        @Nonnull
        @Override
        public ItemStack createIcon() {
            return iconSupplier.get();
        }
    }
}
