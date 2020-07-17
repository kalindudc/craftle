package com.craftle_mod.datagen.lang;

import com.craftle_mod.common.Craftle;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class CraftleLangProvider extends LanguageProvider {

    public CraftleLangProvider(DataGenerator gen) {
        super(gen, Craftle.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

    }
}
