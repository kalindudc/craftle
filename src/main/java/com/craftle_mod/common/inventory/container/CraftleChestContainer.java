package com.craftle_mod.common.inventory.container;

import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tile.TileEntityTestChest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public class CraftleChestContainer extends Container {


    public final  TileEntityTestChest entity;
    private final IWorldPosCallable   canInteractWithCallable;
    private       int                 numRows;
    private       int                 numCols;


    public CraftleChestContainer(final int windowId,
                                 final PlayerInventory playerInventory,
                                 final TileEntityTestChest entity) {
        super(CraftleContainerTypes.TEST_CHEST.get(), windowId);
        this.entity                  = entity;
        this.canInteractWithCallable =
                IWorldPosCallable.of(entity.getWorld(), entity.getPos());
        this.numRows                 = 6;
        this.numCols                 = 9;
        
        // Main Inventory
        int startX        = 8;
        int startY        = 18;
        int slotSizePlus2 = 18;

        for (int row = 0; row < this.numRows; row++) {
            for (int col = 0; col < this.numCols; col++) {
                this.addSlot(new Slot(entity, (row * this.numCols) + col,
                                      startX + (col * slotSizePlus2),
                                      startY + (row * slotSizePlus2)));
            }
        }

        // Main Player Inventory
        int startPlayerInvX = 8;
        int startPlayerInvY = 140;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                // extra 9 + is to account for the hotbar
                this.addSlot(new Slot(playerInventory, (9 + (row * 9)) + col,
                                      startPlayerInvX + (col * slotSizePlus2),
                                      startPlayerInvY + (row * slotSizePlus2)));
            }
        }

        // Hot Bar
        int hotbarX = 8;
        int hotbarY = 198;
        for (int col = 0; col < 9; col++) {
            // extra 9 + is to account for the hotbar
            this.addSlot(new Slot(playerInventory, col,
                                  hotbarX + (col * slotSizePlus2), hotbarY));
        }
    }

    public CraftleChestContainer(final int windowId,
                                 final PlayerInventory playerInventory,
                                 final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    private static TileEntityTestChest getTileEntity(
            final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory,
                               "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final TileEntity tileAtPos =
                playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof TileEntityTestChest) {
            return (TileEntityTestChest) tileAtPos;
        }

        throw new IllegalStateException(
                "Tile entity is not correct. " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn,
                                      CraftleBlocks.TEST_CHEST.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot      slot      = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if (index < 54) {
                if (!this.mergeItemStack(itemStack1, 54,
                                         this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemStack1, 0, 54, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.putStack(itemStack.EMPTY);
            }
            else {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }
}
