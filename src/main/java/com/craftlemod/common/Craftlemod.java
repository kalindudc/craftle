package com.craftlemod.common;

import com.craftlemod.common.registries.CraftleItems;
import net.fabricmc.api.ModInitializer;

public class Craftlemod implements ModInitializer {

    public static final String MODID = "craftle";
    public static final String MOD_VERSION = "0.0.1.0";

    @Override
    public void onInitialize() {
        CraftleItems.registerAll();
    }
}
