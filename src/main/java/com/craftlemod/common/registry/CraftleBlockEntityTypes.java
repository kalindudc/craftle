package com.craftlemod.common.registry;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.block.machine.MachineControllerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CraftleBlockEntityTypes {

    public static BlockEntityType<BlockEntity> FLUID_TANK_BLOCK_ENTITY;
    public static BlockEntityType<BlockEntity> FACTORY_INTAKE_BLOCK_ENTITY;
    public static BlockEntityType<BlockEntity> FACTORY_EXHAUST_BLOCK_ENTITY;

    public static void registerAll() {
        FLUID_TANK_BLOCK_ENTITY = registerBlockEntity("fluid_tank_block_entity", (MachineControllerBlock) CraftleBlocks.FLUID_TANK_CONTROLLER);
        FACTORY_INTAKE_BLOCK_ENTITY = registerBlockEntity("factory_intake_block_entity", (MachineControllerBlock) CraftleBlocks.FACTORY_INTAKE);
        FACTORY_EXHAUST_BLOCK_ENTITY = registerBlockEntity("factory_exhaust_block_entity", (MachineControllerBlock) CraftleBlocks.FACTORY_EXHAUST);
    }

    private static BlockEntityType<BlockEntity> registerBlockEntity(String name, MachineControllerBlock block) {
        BlockEntityType<BlockEntity> entity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CraftleMod.MODID, name),
            FabricBlockEntityTypeBuilder.create(block.getConstructor()::apply, block).build(null));
        block.setEntityType(entity);
        return entity;
    }
}
