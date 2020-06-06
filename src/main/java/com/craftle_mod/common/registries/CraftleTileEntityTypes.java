package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.TileEntityQuarry;
import com.craftle_mod.common.tile.TileEntityTestChest;
import com.craftle_mod.common.tile.machine.CrusherTileEntity;
import com.craftle_mod.common.tile.machine.CrusherTileEntityFactory;
import net.minecraft.block.Block;
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

    public static final RegistryObject<TileEntityType<TileEntityTestChest>>
            TEST_CHEST = TILE_ENTITY_TYPES.register("test_chest",
                                                    () -> TileEntityType.Builder
                                                            .create(TileEntityTestChest::new,
                                                                    CraftleBlocks.TEST_CHEST
                                                                            .get())
                                                            .build(null));

    // CRUSHER
    public static final RegistryObject<TileEntityType<CrusherTileEntity>>
            CRUSHER_BASIC  =
            registerCrusher(CraftleBlocks.CRUSHER_BASIC, CraftleBaseTier.BASIC);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>>
            CRUSHER_TIER_1 = registerCrusher(CraftleBlocks.CRUSHER_TIER_1,
                                             CraftleBaseTier.TIER_1);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>>
            CRUSHER_TIER_2 = registerCrusher(CraftleBlocks.CRUSHER_TIER_2,
                                             CraftleBaseTier.TIER_2);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>>
            CRUSHER_TIER_3 = registerCrusher(CraftleBlocks.CRUSHER_TIER_3,
                                             CraftleBaseTier.TIER_3);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>>
            CRUSHER_TIER_4 = registerCrusher(CraftleBlocks.CRUSHER_TIER_4,
                                             CraftleBaseTier.TIER_4);

    private static RegistryObject<TileEntityType<CrusherTileEntity>> registerCrusher(
            RegistryObject<Block> block, CraftleBaseTier tier) {

        return (TILE_ENTITY_TYPES.register("crusher_" + tier.getTier(),
                                           () -> TileEntityType.Builder
                                                   .create(new CrusherTileEntityFactory(
                                                                   tier).buildSupplier(),
                                                           block.get())
                                                   .build(null)));
    }

}
