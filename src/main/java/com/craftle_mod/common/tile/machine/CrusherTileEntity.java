package com.craftle_mod.common.tile.machine;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.TestChest;
import com.craftle_mod.common.inventory.machine.crusher.CrusherContainerFactory;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.recipe.base.CraftleRecipe;
import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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
import javax.annotation.Nullable;
import java.util.Map;

public class CrusherTileEntity extends LockableLootTileEntity
        implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator,
                   ITickableTileEntity {

    private static final int[] SLOTS_UP         = new int[]{0};
    private static final int[] SLOTS_DOWN       = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};


    private       NonNullList<ItemStack>               chestContents;
    protected     int                                  numplayersUsing;
    private       IItemHandlerModifiable               items;
    private       LazyOptional<IItemHandlerModifiable> itemHandler;
    private final CraftleBaseTier                      tier;

    // using abstract furnace information
    private final   Map<ResourceLocation, Integer>       recipesUsed =
            Maps.newHashMap();
    protected final IRecipeType<? extends CraftleRecipe> recipeType;


    private void init(CraftleBaseTier tier) {
        int chestSize;
        switch (tier) {

            case TIER_1:
                chestSize = 2 + 4;
                break;
            case TIER_2:
                chestSize = 2 + 8;
                break;
            case TIER_3:
                chestSize = 2 + 16;
                break;
            case TIER_4:
            case UNLIMITED:
                chestSize = 2 + 24;
                break;
            case BASIC:
            default:
                chestSize = 2 + 1;
                break;

        }
        this.chestContents = NonNullList.withSize(chestSize, ItemStack.EMPTY);
        this.items         = createHandler();
        this.itemHandler   = LazyOptional.of(() -> items);
    }

    public CrusherTileEntity(TileEntityType<?> typeIn,
                             IRecipeType<? extends CraftleRecipe> recipeTypeIn,
                             CraftleBaseTier tier) {
        super(typeIn);
        init(tier);
        this.recipeType = recipeTypeIn;
        this.tier       = tier;
    }

    public CrusherTileEntity(TileEntityType<?> typeIn, CraftleBaseTier tier) {
        this(typeIn, CraftleRecipeType.CRUSHING, tier);

    }

    public CrusherTileEntity() {
        this(CraftleTileEntityTypes.CRUSHER_BASIC.get(),
             CraftleRecipeType.CRUSHING, CraftleBaseTier.BASIC);
    }

    public CraftleBaseTier getCraftleMachineTier() {
        return tier;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        }
        else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn,
                                 @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack,
                                  Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.chestContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.chestContents = itemsIn;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(
                "block." + Craftle.MODID + ".crusher_" + tier.getTier());
    }

    @Override
    public Container createMenu(int id, PlayerInventory player) {
        return CrusherContainerFactory
                .buildWithTileEntity(tier, id, player, this);
    }

    @Override
    public int getSizeInventory() {
        return this.chestContents.size();
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for (ItemStack itemstack : this.chestContents) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            this.recipesUsed
                    .compute(recipe.getId(), (p_214004_0_, usedCount) -> {
                        return 1 + (usedCount == null ? 0 : usedCount);
                    });
        }
    }

    @Nullable
    @Override
    public IRecipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void tick() {

    }

    @Override
    public void remove() {
        super.remove();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (!this.checkLootAndWrite(compound)) {
            ItemStackHelper.saveAllItems(compound, this.chestContents);
        }
        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
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
            if (entity instanceof CrusherTileEntity) {
                return ((CrusherTileEntity) entity).numplayersUsing;
            }
        }
        return 0;
    }

    public static void swapContents(CrusherTileEntity entity,
                                    CrusherTileEntity otherEntity) {
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
}

