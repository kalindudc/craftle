package com.craftlemod.common.screen;

import com.craftlemod.common.blockentity.factory.FactoryBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public abstract class FactoryScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    private BlockPos pos;

    public FactoryScreenHandler(ScreenHandlerType<FactoryScreenHandler> handler, int syncId, PlayerInventory playerInventory, Inventory inventory,
        PropertyDelegate propertyDelegate) {
        super(handler, syncId);
        checkSize(inventory, 1);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate;
        this.pos = BlockPos.ORIGIN;

        this.addProperties(propertyDelegate);

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

    public int getSyncedNumber() {
        return propertyDelegate.get(0);
    }

    protected static Inventory parseInventory(PlayerInventory inv, PacketByteBuf buffer) {
        final BlockEntity entity = inv.player.world.getBlockEntity(buffer.readBlockPos());

        if (entity instanceof FactoryBlockEntity facEntity) {
            NbtCompound nbt = buffer.readNbt();
            assert nbt != null;
            facEntity.readNbt(nbt);
            // ignore close exception
            return facEntity;
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
