package com.craftlemod.common.screen;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.blockentity.factory.FactoryBlockEntity;
import com.craftlemod.common.registry.CraftleScreenHandlers;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class FactoryScreenHandler extends ScreenHandler {

    private final Inventory inventory;

    private BlockPos pos;

    public FactoryScreenHandler(int syncId, PlayerInventory inv, PacketByteBuf buffer) {
        this(syncId, inv, parseInventory(inv, buffer));
    }

    public FactoryScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(CraftleScreenHandlers.FACTORY_SCREEN_HANDLER, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        this.pos = BlockPos.ORIGIN;

        //this.addSlot(new Slot(inventory, 0, 62, 17));

        //The player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 140 + m * 18));
            }
        }
        //The player Hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 198));
        }

    }

    private static Inventory parseInventory(PlayerInventory inv, PacketByteBuf buffer) {
        final BlockEntity entity = inv.player.world.getBlockEntity(buffer.readBlockPos());
        CraftleMod.LOGGER.error("on parse: " + entity);

        if (entity instanceof FactoryBlockEntity facEntity) {
            CraftleMod.LOGGER.error("fac: " + ((FactoryBlockEntity) entity).getFactoryConfig().toString());
            return (Inventory) entity;
        }
        return new SimpleInventory(1);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public BlockPos getPos() {
        return pos;
    }

    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        return ItemStack.EMPTY;
    }

}
