package com.craftle_mod.common.inventory.container.base;

import com.craftle_mod.common.tile.base.ContainerizedTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import java.util.Objects;

public abstract class CraftleContainer extends Container {

    private IWorldPosCallable       canInteractWithCallable;
    private ContainerizedTileEntity entity;
    private PlayerInventory         playerInventory;

    public CraftleContainer(ContainerType<?> container, final int windowId,
                            final PlayerInventory playerInventory,
                            final ContainerizedTileEntity entity) {

        super(container, windowId);
        this.playerInventory         = playerInventory;
        this.entity                  = entity;
        this.canInteractWithCallable =
                IWorldPosCallable.of(entity.getWorld(), entity.getPos());
        initSlots();
    }

    public CraftleContainer(ContainerType<?> container, final int windowId,
                            final PlayerInventory playerInventory,
                            final PacketBuffer data) {
        this(container, windowId, playerInventory,
             getTileEntity(playerInventory, data));
    }

    public ContainerizedTileEntity getEntity() {
        return entity;
    }

    public IWorldPosCallable getCanInteractWithCallable() {
        return canInteractWithCallable;
    }

    private static ContainerizedTileEntity getTileEntity(
            final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory,
                               "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final TileEntity tileAtPos =
                playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof ContainerizedTileEntity) {
            return (ContainerizedTileEntity) tileAtPos;
        }

        throw new IllegalStateException(
                "Tile entity is not correct. " + tileAtPos);
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

    public void addContainerSlot(int index, int inputX, int inputY) {
        this.addSlot(new Slot(entity, index, inputX, inputY));
    }

    public void addPlayerInventorySlots(int startX, int startY,
                                        int totalSlotSpaceSize) {
        // Main Player Inventory
        int startPlayerInvX = startX;
        int startPlayerInvY = startY;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                // extra 9 + is to account for the hotbar
                this.addSlot(new Slot(playerInventory, (9 + (row * 9)) + col,
                                      startPlayerInvX +
                                      (col * totalSlotSpaceSize),
                                      startPlayerInvY +
                                      (row * totalSlotSpaceSize)));
            }
        }

        // Hot Bar
        int hotbarX = 8;
        int hotbarY = 142;
        for (int col = 0; col < 9; col++) {
            // extra 9 + is to account for the hotbar
            this.addSlot(new Slot(playerInventory, col,
                                  hotbarX + (col * totalSlotSpaceSize),
                                  hotbarY));
        }
    }

    public abstract void initSlots();

}
