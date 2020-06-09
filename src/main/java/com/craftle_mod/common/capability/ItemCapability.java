package com.craftle_mod.common.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemCapability implements ICapabilityProvider {

    private final ItemStack            itemStack;
    private final List<ItemCapability> capabilities;
    private       boolean              capabilitiesInitialized;

    public ItemCapability(ItemStack stack) {

        capabilities = new ArrayList<>();
        itemStack    = stack;

        initCapabilities();

    }

    private void initCapabilities() {
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return null;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return null;
    }

}
