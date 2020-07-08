package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.machine.Crusher;
import com.craftle_mod.common.block.storage.EnergyTank;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.TileEntityQuarry;
import com.craftle_mod.common.tile.TileEntityTestChest;
import com.craftle_mod.common.tile.base.GeneratorTileEntity;
import com.craftle_mod.common.tile.machine.CoalGeneratorTileEntity;
import com.craftle_mod.common.tile.machine.CrusherTileEntity;
import com.craftle_mod.common.tile.machine.WorkBenchTileEntity;
import com.craftle_mod.common.tile.storage.EnergyTankTileEntity;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import java.util.function.Supplier;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(
        ForgeRegistries.TILE_ENTITIES, Craftle.MODID);

    public static final RegistryObject<TileEntityType<TileEntityQuarry>> QUARRY = register("quarry",
        TileEntityQuarry::new, CraftleBlocks.QUARRY);

    public static final RegistryObject<TileEntityType<TileEntityTestChest>> TEST_CHEST = register(
        "test_chest", TileEntityTestChest::new, CraftleBlocks.TEST_CHEST);

    // CRUSHER
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_BASIC = registerCrusher(
        CraftleBlocks.CRUSHER_BASIC, CraftleBaseTier.BASIC);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_1 = registerCrusher(
        CraftleBlocks.CRUSHER_TIER_1, CraftleBaseTier.TIER_1);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_2 = registerCrusher(
        CraftleBlocks.CRUSHER_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_3 = registerCrusher(
        CraftleBlocks.CRUSHER_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<TileEntityType<CrusherTileEntity>> CRUSHER_TIER_4 = registerCrusher(
        CraftleBlocks.CRUSHER_TIER_4, CraftleBaseTier.TIER_4);

    // GENERATORS
    public static final RegistryObject<TileEntityType<GeneratorTileEntity>> COAL_GENERATOR = register(
        "coal_generator", CoalGeneratorTileEntity::new, CraftleBlocks.COAL_GENERATOR);

    // ENERGY MATRIX

    public static final RegistryObject<TileEntityType<EnergyTankTileEntity>> ENERGY_TANK_BASIC = registerEnergyTank(
        CraftleBlocks.ENERGY_TANK_BASIC, CraftleBaseTier.BASIC);
    public static final RegistryObject<TileEntityType<EnergyTankTileEntity>> ENERGY_TANK_TIER_1 = registerEnergyTank(
        CraftleBlocks.ENERGY_TANK_TIER_1, CraftleBaseTier.TIER_1);
    public static final RegistryObject<TileEntityType<EnergyTankTileEntity>> ENERGY_TANK_TIER_2 = registerEnergyTank(
        CraftleBlocks.ENERGY_TANK_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<TileEntityType<EnergyTankTileEntity>> ENERGY_TANK_TIER_3 = registerEnergyTank(
        CraftleBlocks.ENERGY_TANK_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<TileEntityType<EnergyTankTileEntity>> ENERGY_TANK_TIER_4 = registerEnergyTank(
        CraftleBlocks.ENERGY_TANK_TIER_4, CraftleBaseTier.TIER_4);

    // WORKBENCH
    public static final RegistryObject<TileEntityType<WorkBenchTileEntity>> WORKBENCH = register(
        "workbench", WorkBenchTileEntity::new, CraftleBlocks.WORKBENCH);


    private static RegistryObject<TileEntityType<CrusherTileEntity>> registerCrusher(
        RegistryObject<Block> block, CraftleBaseTier tier) {

        return register("crusher_" + tier.getTier(),
            () -> new CrusherTileEntity((Crusher) block.get(), tier), block);
    }

    private static RegistryObject<TileEntityType<EnergyTankTileEntity>> registerEnergyTank(
        RegistryObject<Block> block, CraftleBaseTier tier) {

        return register("energy_tank_" + tier.getTier(),
            () -> new EnergyTankTileEntity((EnergyTank) block.get(), tier), block);
    }

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String key,
        Supplier<T> entitySupplier, RegistryObject<Block> block) {
        Type<?> type = null;

        try {
            type = DataFixesManager.getDataFixer()
                .getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion()))
                .getChoiceType(TypeReferences.BLOCK_ENTITY, key);
        } catch (IllegalArgumentException illegalargumentexception) {
            Craftle.LOGGER.error("No data fixer registered for block entity {}", key);
            if (SharedConstants.developmentMode) {
                throw illegalargumentexception;
            }
        }

        final Type<?> finalType = type;
        return TILE_ENTITY_TYPES.register(key,
            () -> TileEntityType.Builder.create(entitySupplier, block.get()).build(finalType));
    }
}
