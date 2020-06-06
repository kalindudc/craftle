package com.craftle_mod.common.inventory.machine.crusher;


import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tile.machine.CrusherTileEntity;
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

public class CrusherContainer extends Container {

    public  CrusherTileEntity entity;
    private IWorldPosCallable canInteractWithCallable;
    private int               numRows;
    private int               numCols;


    public CrusherContainer(ContainerType<CrusherContainer> container,
                            final int windowId,
                            final PlayerInventory playerInventory,
                            final CrusherTileEntity entity) {

        super(container, windowId);

        init(windowId, playerInventory, entity);
    }

    private void init(final int windowId, final PlayerInventory playerInventory,
                      final CrusherTileEntity entity) {

        this.entity                  = entity;
        this.canInteractWithCallable =
                IWorldPosCallable.of(entity.getWorld(), entity.getPos());

        int inputX;
        int inputY;
        int fuelX;
        int fuelY;
        int chestStartX;
        int chestStartY;
        switch (entity.getCraftleMachineTier()) {

            case TIER_1:
                this.numRows = 2;
                this.numCols = 2;
                inputX = 46;
                inputY = 16;
                fuelX = 46;
                fuelY = 53;
                chestStartX = 104;
                chestStartY = 25;
                break;
            case TIER_2:
                this.numRows = 2;
                this.numCols = 4;
                inputX = 36;
                inputY = 17;
                fuelX = 36;
                fuelY = 53;
                chestStartX = 90;
                chestStartY = 25;
                break;
            case TIER_3:
                this.numRows = 4;
                this.numCols = 4;
                inputX = 36;
                inputY = 17;
                fuelX = 36;
                fuelY = 53;
                chestStartX = 90;
                chestStartY = 7;
                break;
            case TIER_4:
            case UNLIMITED:
                this.numRows = 4;
                this.numCols = 6;
                inputX = 12;
                inputY = 17;
                fuelX = 12;
                fuelY = 53;
                chestStartX = 62;
                chestStartY = 7;
                break;
            case BASIC:
            default:
                this.numRows = 0;
                this.numCols = 0;
                inputX = 56;
                inputY = 17;
                fuelX = 56;
                fuelY = 53;
                chestStartX = 116;
                chestStartY = 35;
                break;

        }
        int slotSizePlus2 = 18;

        // Main Inventory
        //    base crusher slots
        //    inventory

        boolean base = numRows == 0 && numCols == 0;
        this.addSlot(new Slot(entity, 0, inputX, inputY));
        // TODO: replace slot with custom fuel slot for machines
        this.addSlot(new Slot(entity, 1, fuelX, fuelY));
        // TODO: replace the chest inventory with custom machine result slots

        if (base) {
            this.addSlot(new Slot(entity, 2, chestStartX, chestStartY));
        }
        else {
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    // extra 9 + is to account for the hotbar
                    this.addSlot(new Slot(entity, (2 + (row * numCols)) + col,
                                          chestStartX + (col * slotSizePlus2),
                                          chestStartY + (row * slotSizePlus2)));
                }
            }
        }


        // Main Player Inventory
        int startPlayerInvX = 8;
        int startPlayerInvY = 84;
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
        int hotbarY = 142;
        for (int col = 0; col < 9; col++) {
            // extra 9 + is to account for the hotbar
            this.addSlot(new Slot(playerInventory, col,
                                  hotbarX + (col * slotSizePlus2), hotbarY));
        }
    }

    public CrusherContainer(ContainerType<CrusherContainer> container,
                            final int windowId,
                            final PlayerInventory playerInventory,
                            final PacketBuffer data) {
        this(container, windowId, playerInventory,
             getTileEntity(playerInventory, data));
    }

    private static CrusherTileEntity getTileEntity(
            final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory,
                               "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final TileEntity tileAtPos =
                playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof CrusherTileEntity) {
            return (CrusherTileEntity) tileAtPos;
        }

        throw new IllegalStateException(
                "Tile entity is not correct. " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        switch (entity.getCraftleMachineTier()) {

            case TIER_1:
                return isWithinUsableDistance(canInteractWithCallable, playerIn,
                                              CraftleBlocks.CRUSHER_TIER_1
                                                      .get());
            case TIER_2:
                return isWithinUsableDistance(canInteractWithCallable, playerIn,
                                              CraftleBlocks.CRUSHER_TIER_2
                                                      .get());
            case TIER_3:
                return isWithinUsableDistance(canInteractWithCallable, playerIn,
                                              CraftleBlocks.CRUSHER_TIER_3
                                                      .get());
            case TIER_4:
            case UNLIMITED:
                return isWithinUsableDistance(canInteractWithCallable, playerIn,
                                              CraftleBlocks.CRUSHER_TIER_4
                                                      .get());
            case BASIC:
            default:
                return isWithinUsableDistance(canInteractWithCallable, playerIn,
                                              CraftleBlocks.CRUSHER_BASIC
                                                      .get());
        }
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
