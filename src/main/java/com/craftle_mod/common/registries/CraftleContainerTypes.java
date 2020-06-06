package com.craftle_mod.common.registries;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.CraftleChestContainer;
import com.craftle_mod.common.inventory.container.machine.crusher.CrusherContainer;
import com.craftle_mod.common.inventory.container.machine.crusher.CrusherContainerFactory;
import com.craftle_mod.common.tier.CraftleBaseTier;
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

    public static final RegistryObject<ContainerType<CrusherContainer>>
            CRUSHER_BASIC  = CONTAINER_TYPES.register("crusher_basic",
                                                      () -> IForgeContainerType
                                                              .create(CrusherContainerFactory
                                                                              .buildContainerFactory(
                                                                                      CraftleBaseTier.BASIC)));
    public static final RegistryObject<ContainerType<CrusherContainer>>
            CRUSHER_TIER_1 = CONTAINER_TYPES.register("crusher_tier_1",
                                                      () -> IForgeContainerType
                                                              .create(CrusherContainerFactory
                                                                              .buildContainerFactory(
                                                                                      CraftleBaseTier.TIER_1)));
    public static final RegistryObject<ContainerType<CrusherContainer>>
            CRUSHER_TIER_2 = CONTAINER_TYPES.register("crusher_tier_2",
                                                      () -> IForgeContainerType
                                                              .create(CrusherContainerFactory
                                                                              .buildContainerFactory(
                                                                                      CraftleBaseTier.TIER_2)));
    public static final RegistryObject<ContainerType<CrusherContainer>>
            CRUSHER_TIER_3 = CONTAINER_TYPES.register("crusher_tier_3",
                                                      () -> IForgeContainerType
                                                              .create(CrusherContainerFactory
                                                                              .buildContainerFactory(
                                                                                      CraftleBaseTier.TIER_3)));
    public static final RegistryObject<ContainerType<CrusherContainer>>
            CRUSHER_TIER_4 = CONTAINER_TYPES.register("crusher_tier_4",
                                                      () -> IForgeContainerType
                                                              .create(CrusherContainerFactory
                                                                              .buildContainerFactory(
                                                                                      CraftleBaseTier.TIER_4)));

}
