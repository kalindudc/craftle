package com.craftle_mod.common.registries.register;

import com.craftle_mod.common.registries.register.object.CraftleRegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A Wrapper object for an Deferred Register
 */
public class CraftleDeferredRegister<T extends IForgeRegistryEntry<T>> {

    protected final DeferredRegister<T> internal;

    public CraftleDeferredRegister(String modid, IForgeRegistry<T> registry) {
        internal = new DeferredRegister<>(registry, modid);
    }

    public <I extends T, W extends CraftleRegistryObject<I>> W register(
            String name, Supplier<? extends I> sup,
            Function<RegistryObject<I>, W> objectWrapper) {
        return objectWrapper.apply(internal.register(name, sup));
    }

    public void register(IEventBus bus) {
        internal.register(bus);
    }

}
