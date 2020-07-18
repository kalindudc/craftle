package com.craftle_mod.common.registries;

import com.craftle_mod.api.constants.FluidConstants;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.base.CraftleBlock;
import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.block.base.OreBlock;
import com.craftle_mod.common.block.base.ResourceBlock;
import com.craftle_mod.common.block.fluid.EnergyFluid;
import com.craftle_mod.common.block.machine.CoalGenerator;
import com.craftle_mod.common.block.machine.Crusher;
import com.craftle_mod.common.block.machine.WorkBench;
import com.craftle_mod.common.block.storage.CableConnector;
import com.craftle_mod.common.block.storage.EnergyTank;
import com.craftle_mod.common.resource.BlockResource;
import com.craftle_mod.common.resource.OreResource;
import com.craftle_mod.common.resource.ResourceType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleBlocks {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(
        ForgeRegistries.BLOCKS, Craftle.MODID);

    // ORES
    public static final RegistryObject<Block> COPPER_ORE = registerOre(OreResource.COPPER,
        CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> RUBY_ORE = registerOre(OreResource.RUBY,
        CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> SAPPHIRE_ORE = registerOre(OreResource.SAPPHIRE,
        CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> TIN_ORE = registerOre(OreResource.TIN,
        CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> URANIUM_ORE = registerOre(OreResource.URANIUM,
        CraftleBlock.BlockType.RESOURCE);

    //RESOURCE BLOCK
    public static final RegistryObject<Block> ALUMINIUM_BLOCK = registerResource(
        BlockResource.ALUMINIUM, CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> COPPER_BLOCK = registerResource(BlockResource.COPPER,
        CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> PLATINUM_BLOCK = registerResource(
        BlockResource.PLATINUM, CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> RUBY_BLOCK = registerResource(BlockResource.RUBY,
        CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> SAPPHIRE_BLOCK = registerResource(
        BlockResource.SAPPHIRE, CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> STEEL_BLOCK = registerResource(BlockResource.STEEL,
        CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> TIN_BLOCK = registerResource(BlockResource.TIN,
        CraftleBlock.BlockType.RESOURCE);

    // Crusher
    public static final RegistryObject<Block> CRUSHER_BASIC = registerMachine("crusher",
        new Crusher(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.BASIC));
    public static final RegistryObject<Block> CRUSHER_TIER_1 = registerMachine("crusher",
        new Crusher(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_1));
    public static final RegistryObject<Block> CRUSHER_TIER_2 = registerMachine("crusher",
        new Crusher(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_2));
    public static final RegistryObject<Block> CRUSHER_TIER_3 = registerMachine("crusher",
        new Crusher(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_3));
    public static final RegistryObject<Block> CRUSHER_TIER_4 = registerMachine("crusher",
        new Crusher(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_4));
    // Coal Generator
    public static final RegistryObject<Block> COAL_GENERATOR = registerMachineWithRegistryName(
        "coal_generator",
        new CoalGenerator(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.BASIC));

    // ENERGY MATRIX
    public static final RegistryObject<Block> ENERGY_TANK_BASIC = registerMachine("energy_tank",
        new EnergyTank(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.BASIC));
    public static final RegistryObject<Block> ENERGY_TANK_TIER_1 = registerMachine("energy_tank",
        new EnergyTank(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_1));
    public static final RegistryObject<Block> ENERGY_TANK_TIER_2 = registerMachine("energy_tank",
        new EnergyTank(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_2));
    public static final RegistryObject<Block> ENERGY_TANK_TIER_3 = registerMachine("energy_tank",
        new EnergyTank(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_3));
    public static final RegistryObject<Block> ENERGY_TANK_TIER_4 = registerMachine("energy_tank",
        new EnergyTank(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL,
            CraftleBaseTier.TIER_4));

    // workbench
    public static final RegistryObject<Block> WORKBENCH = registerMachineWithRegistryName(
        "workbench",
        new WorkBench(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.WOOD,
            CraftleBaseTier.BASIC));

    // cable connector
    public static final RegistryObject<Block> CABLE_CONNECTOR = registerMachineWithRegistryName(
        "cable_connector",
        new CableConnector(BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL));

    // machine base, tier (1-4) -
    public static final RegistryObject<Block> MACHINE_BASE_BASIC = registerResource(
        "machine_base_basic", BlockResource.STEEL, CraftleBlock.BlockType.MACHINE, SoundType.METAL);
    public static final RegistryObject<Block> MACHINE_BASE_TIER_1 = registerResource(
        "machine_base_tier_1", BlockResource.STEEL, CraftleBlock.BlockType.MACHINE,
        SoundType.METAL);
    public static final RegistryObject<Block> MACHINE_BASE_TIER_2 = registerResource(
        "machine_base_tier_2", BlockResource.STEEL, CraftleBlock.BlockType.MACHINE,
        SoundType.METAL);
    public static final RegistryObject<Block> MACHINE_BASE_TIER_3 = registerResource(
        "machine_base_tier_3", BlockResource.STEEL, CraftleBlock.BlockType.MACHINE,
        SoundType.METAL);
    public static final RegistryObject<Block> MACHINE_BASE_TIER_4 = registerResource(
        "machine_base_tier_4", BlockResource.STEEL, CraftleBlock.BlockType.MACHINE,
        SoundType.METAL);

    // ENERGY FLUID BLOCK
    public static final RegistryObject<Block> ENERGY = BLOCKS.register("energy",
        () -> new EnergyFluid(CraftleFluids.ENERGY_FLUID, FluidConstants.ENERGY_FLUID_PROPERTIES));

    // tanks -

    // compressor , purifier, tier (1-4) -
    // energy: power blocks, tier (1-4)
    // Hydro Generator -
    // Thermoelectric Generator -

    // infuser, tier (1-4) // infuse metals and other resources and fluids
    // cable, tier (1-4) -
    // Extractor: for rubber (tier 1-4) -
    // monitor and camera
    // coolant FLUID

    private static RegistryObject<Block> registerMachine(String machineType, MachineBlock block) {
        return BLOCKS.register(machineType + "_" + block.getCraftleTier().getTier(), () -> block);
    }

    private static RegistryObject<Block> registerMachineWithRegistryName(String registryName,
        MachineBlock block) {
        return BLOCKS.register(registryName, () -> block);
    }

    private static RegistryObject<Block> registerOre(OreResource resource,
        CraftleBlock.BlockType blockType) {
        String oreName = resource.getResourceName() + "_" + ResourceType.ORE.getResourceName();
        return BLOCKS.register(oreName, () -> new OreBlock(resource, blockType, SoundType.STONE));
    }

    private static RegistryObject<Block> registerResource(BlockResource resource,
        CraftleBlock.BlockType blockType) {
        String resourceName =
            resource.getResourceName() + "_" + ResourceType.BLOCK.getResourceName();
        return BLOCKS
            .register(resourceName, () -> new ResourceBlock(resource, blockType, SoundType.METAL));
    }

    private static RegistryObject<Block> registerResource(String resourceName,
        BlockResource resource, CraftleBlock.BlockType blockType, SoundType sound) {

        return BLOCKS.register(resourceName, () -> new ResourceBlock(resource, blockType, sound));
    }

}
