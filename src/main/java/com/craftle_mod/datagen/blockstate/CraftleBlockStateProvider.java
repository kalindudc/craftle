package com.craftle_mod.datagen.blockstate;

import com.craftle_mod.common.Craftle;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class CraftleBlockStateProvider extends BlockStateProvider {

    public CraftleBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Craftle.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
