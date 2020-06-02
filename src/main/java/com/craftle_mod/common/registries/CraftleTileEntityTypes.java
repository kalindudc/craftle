package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.tile.TileEntityQuarry;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES,
                                   Craftle.MODID);

    public static final RegistryObject<TileEntityType<TileEntityQuarry>>
            QUARRY = TILE_ENTITY_TYPES.register("quarry",
                                                () -> TileEntityType.Builder
                                                        .create(TileEntityQuarry::new,
                                                                CraftleBlocks.QUARRY
                                                                        .get())
                                                        .build(null));
}
