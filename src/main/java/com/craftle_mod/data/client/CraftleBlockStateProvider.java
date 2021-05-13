package com.craftle_mod.data.client;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.resource.IResourceType;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class CraftleBlockStateProvider extends BlockStateProvider {

    public CraftleBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Craftle.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<Block> blockObj : CraftleBlocks.BLOCKS.getEntries()) {
            Block block = blockObj.get();

            if (block instanceof IResourceType) {
                String name = block.getRegistryName().getPath();
                simpleBlock(block, models().cubeAll(name, modLoc("block/" + ((IResourceType) block).getType().getName() + "/" + name)));
            } else {
                simpleBlock(block);
            }
        }
    }
}
