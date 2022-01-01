package com.craftlemod.common.screen;

import com.craftlemod.common.registry.CraftleScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;

public class FactoryIOScreenHandler extends FactoryScreenHandler {

    public FactoryIOScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf buffer) {
        this(syncId, inv, parseInventory(inv, buffer));
    }

    public FactoryIOScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(CraftleScreenHandlers.FACTORY_IO_SCREEN_HANDLER, syncId, playerInventory, inventory);
    }

}
