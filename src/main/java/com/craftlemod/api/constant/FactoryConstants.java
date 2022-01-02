package com.craftlemod.api.constant;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map;

public class FactoryConstants {

    public static final int FLUID_TYPE_DEFAULT = -1;
    public static final int FLUID_TYPE_WATER = 0;
    public static final int FLUID_TYPE_LAVA = 1;

    public static final Map<Integer, ItemStack> FLUID_TYPE_TO_BUCKET_DATA = new HashMap<>();

    static {
        FLUID_TYPE_TO_BUCKET_DATA.put(FLUID_TYPE_DEFAULT, ItemStack.EMPTY);
        FLUID_TYPE_TO_BUCKET_DATA.put(FLUID_TYPE_WATER, Items.WATER_BUCKET.getDefaultStack());
        FLUID_TYPE_TO_BUCKET_DATA.put(FLUID_TYPE_DEFAULT, Items.LAVA_BUCKET.getDefaultStack());
    }

}
