package com.craftlemod.common.registry;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.block.machine.MachineBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CraftleBlockEntityTypes {

    public static BlockEntityType<BlockEntity> FACTORY_BLOCK_BLOCK_ENTITY;
    public static BlockEntityType<BlockEntity> FACTORY_GLASS_BLOCK_BLOCK_ENTITY;
    public static BlockEntityType<BlockEntity> FLUID_TANK_BLOCK_ENTITY;
    public static BlockEntityType<BlockEntity> FACTORY_INTAKE_BLOCK_ENTITY;
    public static BlockEntityType<BlockEntity> FACTORY_EXHAUST_BLOCK_ENTITY;

    public static void registerAll() {
        FLUID_TANK_BLOCK_ENTITY = registerBlockEntity("fluid_tank_block_entity", (MachineBlock) CraftleBlocks.FLUID_TANK_CONTROLLER);
        FACTORY_INTAKE_BLOCK_ENTITY = registerBlockEntity("factory_intake_block_entity", (MachineBlock) CraftleBlocks.FACTORY_INTAKE);
        FACTORY_EXHAUST_BLOCK_ENTITY = registerBlockEntity("factory_exhaust_block_entity", (MachineBlock) CraftleBlocks.FACTORY_EXHAUST);
        FACTORY_BLOCK_BLOCK_ENTITY = registerBlockEntity("factory_block_block_entity", (MachineBlock) CraftleBlocks.FACTORY_BLOCK);
        FACTORY_GLASS_BLOCK_BLOCK_ENTITY = registerBlockEntity("factory_glass_block_block_entity", (MachineBlock) CraftleBlocks.FACTORY_GLASS_BLOCK);
    }

    private static BlockEntityType<BlockEntity> registerBlockEntity(String name, MachineBlock block) {
        BlockEntityType<BlockEntity> entity = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(CraftleMod.MODID, name),
            FabricBlockEntityTypeBuilder.create(block.getBlockEntityConstructor()::apply, block).build(null));
        block.setEntityType(entity);
        return entity;
    }
}
