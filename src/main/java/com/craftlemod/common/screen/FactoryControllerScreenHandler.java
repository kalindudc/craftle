package com.craftlemod.common.screen;

import com.craftlemod.common.registry.CraftleScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class FactoryControllerScreenHandler extends FactoryScreenHandler {

    public FactoryControllerScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf buffer) {
        this(syncId, inv, parseInventory(inv, buffer), new ArrayPropertyDelegate(1));
    }

    public FactoryControllerScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(CraftleScreenHandlers.FACTORY_CONTROLLER_SCREEN_HANDLER, syncId, playerInventory, inventory, propertyDelegate);
    }

}
