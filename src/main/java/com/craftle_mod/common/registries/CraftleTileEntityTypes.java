package com.craftle_mod.common.registries;

import com.craftle_mod.common.registries.util.RegistriesUtils;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleTileEntityTypes {

    /**
     * UNUSED
     */
    private CraftleTileEntityTypes() {
    }

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = RegistriesUtils.createRegister(ForgeRegistries.TILE_ENTITIES);


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }

}
