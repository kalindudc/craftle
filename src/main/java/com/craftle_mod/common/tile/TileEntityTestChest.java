package com.craftle_mod.common.tile;

import com.craftle_mod.common.block.TestChest;
import com.craftle_mod.common.inventory.container.CraftleChestContainer;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
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
import java.util.Objects;

public class TileEntityTestChest extends LockableLootTileEntity {

    protected int numplayersUsing;
    private NonNullList<ItemStack> chestContents;
    private IItemHandlerModifiable items;
    private LazyOptional<IItemHandlerModifiable> itemHandler;

    public TileEntityTestChest(TileEntityType<?> typeIn) {
        super(typeIn);
        init();
    }

    public TileEntityTestChest() {
        this(CraftleTileEntityTypes.TEST_CHEST.get());
    }

    public static int getPlayersUsing(IBlockReader reader, BlockPos pos) {
        BlockState blockstate = reader.getBlockState(pos);
        if (blockstate.hasTileEntity()) {
            TileEntity entity = reader.getTileEntity(pos);
            if (entity instanceof TileEntityTestChest) {
                return ((TileEntityTestChest) entity).numplayersUsing;
            }
        }
        return 0;
    }

    public static void swapContents(TileEntityTestChest entity,
                                    TileEntityTestChest otherEntity) {
        NonNullList<ItemStack> list = entity.getItems();
        entity.setItems(otherEntity.getItems());
        otherEntity.setItems(list);
    }

    private void init() {
        this.chestContents = NonNullList.withSize(54, ItemStack.EMPTY);
        this.items = createHandler();
        this.itemHandler = LazyOptional.of(() -> items);
    }

    @Override
    public int getSizeInventory() {
        return 54;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    public void setItems(@Nonnull NonNullList<ItemStack> itemsIn) {
        this.chestContents = itemsIn;
    }

    @Nonnull
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.test_chest");
    }

    @Nonnull
    @Override
    protected Container createMenu(int id, @Nonnull PlayerInventory player) {
        return new CraftleChestContainer(id, player, this);
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.chestContents);
        }
        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        this.chestContents =
                NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compound)) {
            ItemStackHelper.loadAllItems(compound, this.chestContents);
        }
    }

    private void playSound(SoundEvent sound) {
        double dx = (double) this.pos.getX() + 0.5D;
        double dy = (double) this.pos.getY() + 0.5D;
        double dz = (double) this.pos.getZ() + 0.5D;

        assert this.world != null;
        this.world.playSound(null, dx, dy, dz, sound,
                SoundCategory.BLOCKS, 0.5f,
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
                                             Direction side) {
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
}
