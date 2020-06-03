package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.CraftleChestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES =
            new DeferredRegister<>(ForgeRegistries.CONTAINERS, Craftle.MODID);

    public static final RegistryObject<ContainerType<CraftleChestContainer>>
            TEST_CHEST = CONTAINER_TYPES.register("test_chest",
                                                  () -> IForgeContainerType
                                                          .create(CraftleChestContainer::new));
}
