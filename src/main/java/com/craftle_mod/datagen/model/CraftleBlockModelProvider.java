package com.craftle_mod.datagen.model;

import com.craftle_mod.common.Craftle;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public class CraftleBlockModelProvider extends ItemModelProvider {

    public CraftleBlockModelProvider(DataGenerator generator,
        ExistingFileHelper existingFileHelper) {
        super(generator, Craftle.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    @Override
    public String getName() {
        return null;
    }
}
