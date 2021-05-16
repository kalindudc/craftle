package com.craftle_mod.common.registries.util;

import com.craftle_mod.common.Craftle;
import java.util.StringJoiner;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistriesUtils {

    /**
     * UNUSED
     */
    private RegistriesUtils() {
    }

    public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> createRegister(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, Craftle.MODID);
    }

    public static String buildName(String... args) {

        StringJoiner builder = new StringJoiner("_");

        for (String arg : args) {
            if (arg != null) {
                builder.add(arg);
            }
        }

        return builder.toString();
    }

}
