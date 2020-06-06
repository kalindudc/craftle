package com.craftle_mod.common.tile.base;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.TestChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public abstract class ContainerizedTileEntity extends LockableLootTileEntity {

    private   NonNullList<ItemStack>               containerContents;
    protected int                                  numplayersUsing;
    private   IItemHandlerModifiable               items;
    private   LazyOptional<IItemHandlerModifiable> itemHandler;
    private   int                                  containerSize;

    public ContainerizedTileEntity(TileEntityType<?> typeIn,
                                   int containerSize) {
        super(typeIn);
        this.containerContents =
                NonNullList.withSize(containerSize, ItemStack.EMPTY);
        this.items             = createHandler();
        this.itemHandler       = LazyOptional.of(() -> items);
        this.containerSize     = containerSize;
    }

    @Override
    public int getSizeInventory() {
        return containerSize;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.containerContents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.containerContents = itemsIn;
    }

    public void setContainerSize(int containerSize) {
        this.containerSize     = containerSize;
        this.containerContents =
                NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Override
    protected ITextComponent getDefaultName() {
        Craftle.LOGGER.debug("CRAFTLE {ContainerizedTileEntity}: type " +
                             "registry name " +
                             this.getType().getRegistryName());
        return new TranslationTextComponent(
                "container." + this.getType().getRegistryName());
    }

    @Override
    public abstract Container createMenu(int id, PlayerInventory player);

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.containerContents);
        }
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.containerContents =
                NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.containerContents);
        }
    }

    private void playSound(SoundEvent sound) {
        double dx = (double) this.pos.getX() + 0.5D;
        double dy = (double) this.pos.getY() + 0.5D;
        double dz = (double) this.pos.getZ() + 0.5D;

        this.world.playSound((PlayerEntity) null, dx, dy, dz, sound,
                             SoundCategory.BLOCKS, 0.5f,
                             this.world.rand.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.numplayersUsing = type;
            return true;
        }
        else {
            return super.receiveClientEvent(id, type);
        }
    }

    @Override
    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.numplayersUsing < 0) {
                this.numplayersUsing = 0;
            }
            ++this.numplayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.numplayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof TestChest) {
            this.world.addBlockEvent(this.pos, block, 1, this.numplayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, block);
        }
    }

    public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
        BlockState blockstate = reader.getBlockState(pos);
        if (blockstate.hasTileEntity()) {
            TileEntity entity = reader.getTileEntity(pos);
            if (entity instanceof ContainerizedTileEntity) {
                return ((ContainerizedTileEntity) entity).numplayersUsing;
            }
        }
        return 0;
    }

    public static void swapContents(ContainerizedTileEntity entity,
                                    ContainerizedTileEntity otherEntity) {
        NonNullList<ItemStack> list = entity.getItems();
        entity.setItems(otherEntity.getItems());
        otherEntity.setItems(list);
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
        if (this.itemHandler != null) {
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                             @Nonnull Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void remove() {
        super.remove();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
    }

    protected void reloadContainer() {
        this.containerContents =
                NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    public NonNullList<ItemStack> getContainerContents() {
        return containerContents;
    }

    public int getNumplayersUsing() {
        return numplayersUsing;
    }

    public LazyOptional<IItemHandlerModifiable> getItemHandler() {
        return itemHandler;
    }

    public void setItemHandler(
            LazyOptional<IItemHandlerModifiable> itemHandler) {
        this.itemHandler = itemHandler;
    }
}
