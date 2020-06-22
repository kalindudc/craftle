package com.craftle_mod.common.registries.register.object;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class CraftleRegistryObject<T extends IForgeRegistryEntry<? super T>>
    implements Supplier<T> {

    protected RegistryObject<T> registryObject;

    public CraftleRegistryObject(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    @Nonnull
    @Override
    public T get() {
        return registryObject.get();
    }

}
