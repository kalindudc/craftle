package com.craftlemod.api.constant;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.Items;

public class FactoryConstants {

    public static final int FLUID_TYPE_DEFAULT = -1;
    public static final int FLUID_TYPE_WATER = 0;
    public static final int FLUID_TYPE_LAVA = 1;

    public static final Map<String, Integer> BUCKET_TYPE_TO_FLUID_TYPE = new HashMap<>();

    static {
        BUCKET_TYPE_TO_FLUID_TYPE.put(Items.BUCKET.getTranslationKey(), FLUID_TYPE_DEFAULT);
        BUCKET_TYPE_TO_FLUID_TYPE.put(Items.WATER_BUCKET.getTranslationKey(), FLUID_TYPE_WATER);
        BUCKET_TYPE_TO_FLUID_TYPE.put(Items.LAVA_BUCKET.getTranslationKey(), FLUID_TYPE_LAVA);
    }

}
