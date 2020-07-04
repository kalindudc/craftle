package com.craftle_mod.common.inventory.container.base;

import com.craftle_mod.common.tile.base.CraftleTileEntity;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

public abstract class CraftleContainer extends Container {

    private final IWorldPosCallable canInteractWithCallable;
    private final CraftleTileEntity entity;
    private final PlayerInventory playerInventory;
    private final World world;
    private final IWorldPosCallable worldPosCallable;

    public CraftleContainer(ContainerType<?> container, final int windowId,
        final PlayerInventory playerInventory, final CraftleTileEntity entity) {

        super(container, windowId);
        this.playerInventory = playerInventory;
        this.entity = entity;
        this.canInteractWithCallable = IWorldPosCallable
            .of(Objects.requireNonNull(entity.getWorld()), entity.getPos());
        this.world = entity.getWorld();
        worldPosCallable = IWorldPosCallable.of(this.getWorld(), entity.getPos());
        init();

    }

    public void init() {
    }

    public CraftleContainer(ContainerType<?> container, final int windowId,
        final PlayerInventory playerInventory, final PacketBuffer data) {
        this(container, windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    public CraftleTileEntity getEntity() {
        return entity;
    }

    public IWorldPosCallable getCanInteractWithCallable() {
        return canInteractWithCallable;
    }

    private static CraftleTileEntity getTileEntity(final PlayerInventory playerInventory,
        final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");

        final TileEntity tileAtPos = playerInventory.player.world
            .getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof CraftleTileEntity) {
            return (CraftleTileEntity) tileAtPos;
        }

        throw new IllegalStateException("Tile entity is not correct. " + tileAtPos);
    }

    public World getWorld() {
        return world;
    }

    public IWorldPosCallable getWorldPosCallable() {
        return worldPosCallable;
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(@Nonnull PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if (index < entity.getSizeInventory()) {
                if (!this.mergeItemStack(itemStack1, entity.getSizeInventory(),
                    this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemStack1, 0, entity.getSizeInventory(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }

    public void addContainerSlot(int index, int inputX, int inputY) {
        this.addSlot(new Slot(entity, index, inputX, inputY));
    }

    public void addPlayerInventorySlots(int startX, int startY, int totalSlotSpaceSize) {
        // Main Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                // extra 9 + is to account for the hotbar
                this.addSlot(new Slot(playerInventory, (9 + (row * 9)) + col,
                    startX + (col * totalSlotSpaceSize), startY + (row * totalSlotSpaceSize)));
            }
        }

        // Hot Bar
        int hotbarX = 8;
        int hotbarY = 142;
        for (int col = 0; col < 9; col++) {
            // extra 9 + is to account for the hotbar
            this.addSlot(
                new Slot(playerInventory, col, hotbarX + (col * totalSlotSpaceSize), hotbarY));
        }
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public abstract void initSlots();

}
