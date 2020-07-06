package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.base.CraftleBlock;
import com.craftle_mod.common.inventory.container.CraftleChestContainer;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.inventory.container.machine.CoalGeneratorContainer;
import com.craftle_mod.common.inventory.container.machine.WorkBenchContainer;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(
        ForgeRegistries.CONTAINERS, Craftle.MODID);

    public static final RegistryObject<ContainerType<CraftleChestContainer>> TEST_CHEST = CONTAINER_TYPES
        .register("test_chest", () -> IForgeContainerType.create(CraftleChestContainer::new));

    public static final RegistryObject<ContainerType<EnergyContainer>> CRUSHER_BASIC = registerEnergyContainer(
        "crusher_", CraftleBlocks.CRUSHER_BASIC, CraftleBaseTier.BASIC);
    public static final RegistryObject<ContainerType<EnergyContainer>> CRUSHER_TIER_1 = registerEnergyContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_1, CraftleBaseTier.TIER_1);
    public static final RegistryObject<ContainerType<EnergyContainer>> CRUSHER_TIER_2 = registerEnergyContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<ContainerType<EnergyContainer>> CRUSHER_TIER_3 = registerEnergyContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<ContainerType<EnergyContainer>> CRUSHER_TIER_4 = registerEnergyContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_4, CraftleBaseTier.TIER_4);

    public static final RegistryObject<ContainerType<CoalGeneratorContainer>> COAL_GENERATOR = CONTAINER_TYPES
        .register("coal_generator", () -> IForgeContainerType.create(CoalGeneratorContainer::new));

    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_MATRIX_BASIC = registerEnergyContainer(
        "energy_matrix_", CraftleBlocks.ENERGY_MATRIX_BASIC, CraftleBaseTier.BASIC);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_MATRIX_TIER_1 = registerEnergyContainer(
        "energy_matrix_", CraftleBlocks.ENERGY_MATRIX_TIER_1, CraftleBaseTier.TIER_1);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_MATRIX_TIER_2 = registerEnergyContainer(
        "energy_matrix_", CraftleBlocks.ENERGY_MATRIX_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_MATRIX_TIER_3 = registerEnergyContainer(
        "energy_matrix_", CraftleBlocks.ENERGY_MATRIX_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_MATRIX_TIER_4 = registerEnergyContainer(
        "energy_matrix_", CraftleBlocks.ENERGY_MATRIX_TIER_4, CraftleBaseTier.TIER_4);

    public static final RegistryObject<ContainerType<WorkBenchContainer>> WORKBENCH = CONTAINER_TYPES
        .register("workbench", () -> IForgeContainerType.create(WorkBenchContainer::new));

    private static RegistryObject<ContainerType<EnergyContainer>> registerEnergyContainer(
        String idPrefix, RegistryObject<Block> block, CraftleBaseTier tier) {
        return CONTAINER_TYPES.register(idPrefix + tier.getTier(), () -> IForgeContainerType.create(
            (id, player, data) -> new EnergyContainer(
                ((CraftleBlock) block.get()).getContainerType(), id, player, data)));
    }

}
