package com.craftle_mod.common.registries;

import com.craftle_mod.common.registries.util.RegistriesUtils;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleContainerTypes {

    /**
     * UNUSED
     */
    private CraftleContainerTypes() {
    }

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = RegistriesUtils.createRegister(ForgeRegistries.CONTAINERS);


    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }

}
