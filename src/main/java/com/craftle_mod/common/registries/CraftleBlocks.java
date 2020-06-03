package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.OreBlock;
import com.craftle_mod.common.block.Quarry;
import com.craftle_mod.common.block.SpecialBlock;
import com.craftle_mod.common.block.TestChest;
import com.craftle_mod.common.block.base.CraftleBlock;
import com.craftle_mod.common.resource.BlockResource;
import com.craftle_mod.common.resource.OreResource;
import com.craftle_mod.common.resource.ResourceType;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
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
    public static final RegistryObject<Block> TEST_BLOCK    =
            BLOCKS.register("test_block", () -> new Block(
                    Block.Properties.create(Material.ORGANIC)
                                    .hardnessAndResistance(1.0F, 1.0F)
                                    .sound(SoundType.GROUND)));
    public static final RegistryObject<Block> SPECIAL_BLOCK =
            BLOCKS.register("special_block",
                            () -> new SpecialBlock(BlockResource.STEEL,
                                                   CraftleBlock.BlockType.MISC,
                                                   SoundType.METAL));
    public static final RegistryObject<Block> QUARRY        =
            BLOCKS.register("quarry", () -> new Quarry(BlockResource.STEEL,
                                                       CraftleBlock.BlockType.MACHINE,
                                                       SoundType.METAL));
    public static final RegistryObject<Block> TEST_CHEST    =
            BLOCKS.register("test_chest",
                            () -> new TestChest(BlockResource.WOOD,
                                                CraftleBlock.BlockType.MISC,
                                                SoundType.WOOD));
    public static final RegistryObject<Block> TEST_STAIRS   =
            BLOCKS.register("test_stairs", () -> new StairsBlock(
                    () -> CraftleBlocks.TEST_ORE.get().getDefaultState(),
                    Block.Properties.create(Material.ROCK)
                                    .hardnessAndResistance(3.0F, 3.0F)
                                    .sound(SoundType.STONE)));
    public static final RegistryObject<Block> TEST_FENCE    =
            BLOCKS.register("test_fence", () -> new FenceBlock(Block.Properties
                                                                       .create(Material.ROCK,
                                                                               MaterialColor.DIAMOND)
                                                                       .hardnessAndResistance(
                                                                               3.0F,
                                                                               3.0F)
                                                                       .sound(SoundType.STONE)));
    // ORES
    public static final RegistryObject<Block> COPPER_ORE    =
            registerOre(OreResource.COPPER, CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> RUBY_ORE      =
            registerOre(OreResource.RUBY, CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> SAPPHIRE_ORE  =
            registerOre(OreResource.SAPPHIRE, CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> TIN_ORE       =
            registerOre(OreResource.TIN, CraftleBlock.BlockType.RESOURCE);
    public static final RegistryObject<Block> URANIUM_ORE   =
            registerOre(OreResource.URANIUM, CraftleBlock.BlockType.RESOURCE);

    // ore
    // resource block
    // machine
    private static RegistryObject<Block> registerOre(OreResource resource,
                                                     CraftleBlock.BlockType blockType) {
        String oreName = resource.getResourceName() + "_" +
                         ResourceType.ORE.getResourceName();
        return BLOCKS.register(oreName, () -> new OreBlock(resource, blockType,
                                                           SoundType.STONE));
    }

}
