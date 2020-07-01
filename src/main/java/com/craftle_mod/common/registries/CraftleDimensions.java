package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.dimension.TestModDimension;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleDimensions {

    public static final DeferredRegister<ModDimension> DIMENSIONS =
            new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS,
                    Craftle.MODID);

    public static final RegistryObject<ModDimension> TEST_DIMENSION =
            DIMENSIONS.register("test_dimension", TestModDimension::new);
}
