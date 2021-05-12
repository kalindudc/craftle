package com.craftle_mod.common.registries;

import com.craftle_mod.common.registries.util.RegistriesUtils;
import java.util.function.Supplier;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CraftleItems {

    /**
     * UNUSED
     */
    public CraftleItems() {
    }

    private static final DeferredRegister<Item> ITEMS = RegistriesUtils.createRegister(ForgeRegistries.ITEMS);


    static {

    }

    public static RegistryObject<Item> registerItem(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
