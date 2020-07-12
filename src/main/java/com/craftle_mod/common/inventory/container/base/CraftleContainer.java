package com.craftle_mod.common.inventory.container.base;

import com.craftle_mod.common.inventory.slot.SlotConfig;
import com.craftle_mod.common.tile.base.CraftleTileEntity;
import com.craftle_mod.common.tile.base.MachineTileEntity;
import java.util.Objects;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

public class CraftleContainer extends Container {

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
        this.entity.getHotBarSlotConfig().setInventory(playerInventory);
        this.entity.getMainInventorySlotConfig().setInventory(playerInventory);
        init();
        initSlots();

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

            if (itemStack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemStack1.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemStack1);
            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemStack;
    }

    @Override
    public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {

        if (!(getEntity() instanceof MachineTileEntity)) {
            throw new IllegalStateException("Tile entity is not correct. ");
        }

        return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
            this.entity.getBlock());
    }

    public void addContainerSlot(IInventory entity, int index, int inputX, int inputY) {
        this.addSlot(new Slot(entity, index, inputX, inputY));
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public void initSlots() {
        openContainer(playerInventory);

        // go in reverse order to push adding main inventory slots last
        for (int i = entity.getSlotData().size() - 1; i >= 0; i--) {

            SlotConfig config = entity.getSlotData().get(i);

            for (int row = 0; row < config.getNumRows(); row++) {
                for (int col = 0; col < config.getNumCols(); col++) {

                    if (config.getSlot() == null) {
                        addContainerSlot(config.getInventory(), config.getIndex(row, col),
                            config.getX(col), config.getY(row));
                    } else {
                        this.addSlot(config.getSlot());
                    }
                }
            }
        }
    }

    protected void openContainer(@Nonnull PlayerInventory inv) {
        if (entity != null) {
            entity.addPlayer(inv.player);
        }
    }

    protected void closeContainer(PlayerEntity player) {
        if (entity != null) {
            entity.removePlayer(player);
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        closeContainer(player);
    }

}
