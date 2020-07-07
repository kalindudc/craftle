package com.craftle_mod.common.tile.base;

import com.craftle_mod.api.constants.ContainerConstants;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.TestChest;
import com.craftle_mod.common.block.base.CraftleBlock;
import com.craftle_mod.common.inventory.container.base.CraftleContainer;
import com.craftle_mod.common.inventory.slot.SlotConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
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

public abstract class CraftleTileEntity extends LockableLootTileEntity {

    private NonNullList<ItemStack> containerContents;
    protected int numplayersUsing;
    private final IItemHandlerModifiable items;
    private LazyOptional<IItemHandlerModifiable> itemHandler;
    private int containerSize;

    protected CraftleBlock block;

    private final List<SlotConfig> slotData;
    private SlotConfig mainInventorySlotConfig;

    public CraftleTileEntity(CraftleBlock block, int containerSize) {
        super(block.getTileType());
        this.containerContents = NonNullList.withSize(containerSize, ItemStack.EMPTY);
        this.items = createHandler();
        this.itemHandler = LazyOptional.of(() -> items);
        this.containerSize = containerSize;
        this.block = block;
        slotData = new ArrayList<>();
        mainInventorySlotConfig = new SlotConfig(8, 84, ContainerConstants.TOTAL_SLOT_SIZE);
    }

    public SlotConfig getMainInventorySlotConfig() {
        return mainInventorySlotConfig;
    }

    public void setMainInventorySlotConfig(SlotConfig mainInventorySlotConfig) {
        this.mainInventorySlotConfig = mainInventorySlotConfig;
    }


    public CraftleBlock getBlock() {
        return block;
    }

    public List<SlotConfig> getSlotData() {
        return slotData;
    }

    public void addSlotData(SlotConfig config) {
        slotData.add(config);
    }

    @Override
    public int getSizeInventory() {
        return containerSize;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getItems() {
        return this.containerContents;
    }

    @Override
    public void setItems(@Nonnull NonNullList<ItemStack> itemsIn) {
        this.containerContents = itemsIn;
    }

    public void setContainerSize(int containerSize) {
        this.containerSize = containerSize;
        this.containerContents = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    @Nonnull
    @Override
    protected ITextComponent getDefaultName() {
        Craftle.LOGGER.info(
            "CRAFTLE {ContainerizedTileEntity}: type " + "registry name " + this.getType()
                .getRegistryName());
        return new TranslationTextComponent("container." + this.getType().getRegistryName());
    }

    @Nonnull
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory player) {

        return new CraftleContainer(getBlock().getContainerType(), id, player, this);
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.containerContents);
        }
        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        this.containerContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.containerContents);
        }
    }

    private void playSound(SoundEvent sound) {
        double dx = (double) this.pos.getX() + 0.5D;
        double dy = (double) this.pos.getY() + 0.5D;
        double dz = (double) this.pos.getZ() + 0.5D;

        assert this.world != null;
        this.world.playSound(null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f,
            this.world.rand.nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        if (id == 1) {
            this.numplayersUsing = type;
            return true;
        } else {
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
            assert this.world != null;
            this.world.addBlockEvent(this.pos, block, 1, this.numplayersUsing);
            this.world.notifyNeighborsOfStateChange(this.pos, block);
        }
    }

    public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
        BlockState blockstate = reader.getBlockState(pos);
        if (blockstate.hasTileEntity()) {
            TileEntity entity = reader.getTileEntity(pos);
            if (entity instanceof CraftleTileEntity) {
                return ((CraftleTileEntity) entity).numplayersUsing;
            }
        }
        return 0;
    }

    public static void swapContents(CraftleTileEntity entity, CraftleTileEntity otherEntity) {
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
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }

        return Objects.requireNonNull(super.getCapability(cap, side));
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
        this.containerContents = NonNullList.withSize(containerSize, ItemStack.EMPTY);
    }

    public NonNullList<ItemStack> getContainerContents() {
        return containerContents;
    }

    public int getNumPlayersUsing() {
        return numplayersUsing;
    }

    public LazyOptional<IItemHandlerModifiable> getItemHandler() {
        return itemHandler;
    }


    public void setItemHandler(LazyOptional<IItemHandlerModifiable> itemHandler) {
        this.itemHandler = itemHandler;
    }

    public void addToContainer(ItemStack stack, int index) {
        this.containerContents.set(index, stack);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return super.getUpdatePacket();
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        write(nbt);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
        super.onDataPacket(net, pkt);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CraftleTileEntity that = (CraftleTileEntity) o;
        return containerSize == that.containerSize && Objects
            .equals(containerContents, that.containerContents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(containerContents, containerSize);
    }

    @Override
    public String toString() {
        return "CraftleTileEntity{" + "containerContents=" + containerContents + ", containerSize="
            + containerSize + '}';
    }
}
