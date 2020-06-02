package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.CraftleQuarry;
import com.craftle_mod.common.block.SpecialBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            new DeferredRegister<>(ForgeRegistries.BLOCKS, Craftle.MODID);

    public static final RegistryObject<Block> TEST_ORE      =
            BLOCKS.register("test_ore", () -> new Block(
                    Block.Properties.create(Material.ROCK)
                                    .hardnessAndResistance(3.0F, 3.0F)
                                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> SPECIAL_BLOCK =
            BLOCKS.register("special_block", () -> new SpecialBlock(
                    Block.Properties.create(Material.IRON)
                                    .hardnessAndResistance(2.0F, 10.0F)
                                    .harvestLevel(2)
                                    .harvestTool(ToolType.PICKAXE)
                                    .sound(SoundType.METAL).lightValue(4)));
    public static final RegistryObject<Block> QUARRY        =
            BLOCKS.register("quarry", () -> new CraftleQuarry(
                    Block.Properties.create(Material.IRON)
                                    .hardnessAndResistance(2.0F, 10.0F)
                                    .harvestLevel(2)
                                    .harvestTool(ToolType.PICKAXE)
                                    .sound(SoundType.METAL).lightValue(4)));
}
