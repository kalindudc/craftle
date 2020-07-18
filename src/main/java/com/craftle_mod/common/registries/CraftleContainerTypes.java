package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.base.CraftleBlock;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.inventory.container.machine.GeneratorContainer;
import com.craftle_mod.common.inventory.container.machine.ProducerContainer;
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

    public static final RegistryObject<ContainerType<ProducerContainer>> CRUSHER_BASIC = registerProducerContainer(
        "crusher_", CraftleBlocks.CRUSHER_BASIC, CraftleBaseTier.BASIC);
    public static final RegistryObject<ContainerType<ProducerContainer>> CRUSHER_TIER_1 = registerProducerContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_1, CraftleBaseTier.TIER_1);
    public static final RegistryObject<ContainerType<ProducerContainer>> CRUSHER_TIER_2 = registerProducerContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<ContainerType<ProducerContainer>> CRUSHER_TIER_3 = registerProducerContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<ContainerType<ProducerContainer>> CRUSHER_TIER_4 = registerProducerContainer(
        "crusher_", CraftleBlocks.CRUSHER_TIER_4, CraftleBaseTier.TIER_4);

    public static final RegistryObject<ContainerType<GeneratorContainer>> COAL_GENERATOR = CONTAINER_TYPES
        .register("coal_generator", () -> IForgeContainerType.create(GeneratorContainer::new));

    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_TANK_BASIC = registerEnergyContainer(
        "energy_tank_", CraftleBlocks.ENERGY_TANK_BASIC, CraftleBaseTier.BASIC);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_TANK_TIER_1 = registerEnergyContainer(
        "energy_tank_", CraftleBlocks.ENERGY_TANK_TIER_1, CraftleBaseTier.TIER_1);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_TANK_TIER_2 = registerEnergyContainer(
        "energy_tank_", CraftleBlocks.ENERGY_TANK_TIER_2, CraftleBaseTier.TIER_2);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_TANK_TIER_3 = registerEnergyContainer(
        "energy_tank_", CraftleBlocks.ENERGY_TANK_TIER_3, CraftleBaseTier.TIER_3);
    public static final RegistryObject<ContainerType<EnergyContainer>> ENERGY_TANK_TIER_4 = registerEnergyContainer(
        "energy_tank_", CraftleBlocks.ENERGY_TANK_TIER_4, CraftleBaseTier.TIER_4);


    public static final RegistryObject<ContainerType<WorkBenchContainer>> WORKBENCH = CONTAINER_TYPES
        .register("workbench", () -> IForgeContainerType.create(WorkBenchContainer::new));

    private static RegistryObject<ContainerType<EnergyContainer>> registerEnergyContainer(
        String idPrefix, RegistryObject<Block> block, CraftleBaseTier tier) {
        return CONTAINER_TYPES.register(idPrefix + tier.getTier(), () -> IForgeContainerType.create(
            (id, player, data) -> new EnergyContainer(
                ((CraftleBlock) block.get()).getContainerType(), id, player, data)));
    }

    private static RegistryObject<ContainerType<ProducerContainer>> registerProducerContainer(
        String idPrefix, RegistryObject<Block> block, CraftleBaseTier tier) {
        return CONTAINER_TYPES.register(idPrefix + tier.getTier(), () -> IForgeContainerType.create(
            (id, player, data) -> new ProducerContainer(
                ((CraftleBlock) block.get()).getContainerType(), id, player, data)));
    }

}
