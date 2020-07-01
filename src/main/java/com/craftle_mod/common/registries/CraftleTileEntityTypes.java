package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.TileEntityQuarry;
import com.craftle_mod.common.tile.TileEntityTestChest;
import com.craftle_mod.common.tile.machine.CoalGeneratorTileEntity;
import com.craftle_mod.common.tile.machine.WorkBenchTileEntity;
import com.craftle_mod.common.tile.machine.crusher.CrusherTileEntity;
import com.craftle_mod.common.tile.machine.crusher.CrusherTileEntityFactory;
import com.craftle_mod.common.tile.storage.energy_matrix.EnergyMatrixTileEntity;
import com.craftle_mod.common.tile.storage.energy_matrix.EnergyMatrixTileEntityFactory;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CraftleTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES =
            new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Craftle.MODID);

    public static final RegistryObject<TileEntityType<TileEntityQuarry>> QUARRY =
            register("quarry", TileEntityQuarry::new, CraftleBlocks.QUARRY);

    public static final RegistryObject<TileEntityType<TileEntityTestChest>> TEST_CHEST =
            register("test_chest", TileEntityTestChest::new, CraftleBlocks.TEST_CHEST);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_4 =
            registerCrusher(CraftleBlocks.CRUSHER_TIER_4, CraftleBaseTier.TIER_4);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_3 =
            registerCrusher(CraftleBlocks.CRUSHER_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_2 =
            registerCrusher(CraftleBlocks.CRUSHER_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_1 =
            registerCrusher(CraftleBlocks.CRUSHER_TIER_1, CraftleBaseTier.TIER_1);
    // CRUSHER
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_BASIC =
            registerCrusher(CraftleBlocks.CRUSHER_BASIC, CraftleBaseTier.BASIC);
    // GENERATORS
    public static final RegistryObject<TileEntityType<CoalGeneratorTileEntity>> COAL_GENERATOR =
            register("coal_generator", CoalGeneratorTileEntity::new, CraftleBlocks.COAL_GENERATOR);

    // ENERGY MATRIX
    public static final RegistryObject<TileEntityType<EnergyMatrixTileEntity>>
            ENERGY_MATRIX_TIER_4 =
            registerEnergyMatrix(CraftleBlocks.ENERGY_MATRIX_TIER_4, CraftleBaseTier.TIER_4);
    public static final RegistryObject<TileEntityType<EnergyMatrixTileEntity>>
            ENERGY_MATRIX_TIER_3 =
            registerEnergyMatrix(CraftleBlocks.ENERGY_MATRIX_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<TileEntityType<EnergyMatrixTileEntity>>
            ENERGY_MATRIX_TIER_2 =
            registerEnergyMatrix(CraftleBlocks.ENERGY_MATRIX_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<TileEntityType<EnergyMatrixTileEntity>>
            ENERGY_MATRIX_TIER_1 =
            registerEnergyMatrix(CraftleBlocks.ENERGY_MATRIX_TIER_1, CraftleBaseTier.TIER_1);
    public static final RegistryObject<TileEntityType<EnergyMatrixTileEntity>> ENERGY_MATRIX_BASIC =
            registerEnergyMatrix(CraftleBlocks.ENERGY_MATRIX_BASIC, CraftleBaseTier.BASIC);
    // WORKBENCH
    public static final RegistryObject<TileEntityType<WorkBenchTileEntity>> WORKBENCH =
            register("workbench", WorkBenchTileEntity::new, CraftleBlocks.WORKBENCH);


    private static RegistryObject<TileEntityType<CrusherTileEntity>> registerCrusher(
            RegistryObject<Block> block, CraftleBaseTier tier) {

        return register("crusher_" + tier.getTier(),
                new CrusherTileEntityFactory().buildSupplier(tier), block);
    }

    private static RegistryObject<TileEntityType<EnergyMatrixTileEntity>> registerEnergyMatrix(
            RegistryObject<Block> block, CraftleBaseTier tier) {

        return register("energy_matrix_" + tier.getTier(), new EnergyMatrixTileEntityFactory()
                        .buildSupplier(tier),
                block);
    }

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String key,
                                                                                     Supplier<T> entitySupplier, RegistryObject<Block> block) {
        Type<?> type = null;

        try {
            type = DataFixesManager
                    .getDataFixer()
                    .getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion()))
                    .getChoiceType(
                            TypeReferences.BLOCK_ENTITY, key);
        } catch (IllegalArgumentException illegalargumentexception) {
            Craftle.LOGGER.error("No data fixer registered for block entity {}", key);
            if (SharedConstants.developmentMode) {
                throw illegalargumentexception;
            }
        }

        final Type<?> finalType = type;
        return TILE_ENTITY_TYPES.register(key, () -> TileEntityType.Builder
                .create(entitySupplier, block.get()).build(finalType));
    }
}
