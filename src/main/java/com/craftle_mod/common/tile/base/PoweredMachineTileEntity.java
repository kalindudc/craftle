package com.craftle_mod.common.tile.base;

import com.craftle_mod.api.NBTConstants;
import com.craftle_mod.common.block.base.ActiveBlockBase;
import com.craftle_mod.common.capability.energy.EnergyContainerCapability;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class PoweredMachineTileEntity extends MachineTileEntity
        implements ITickableTileEntity, ICapabilityProvider {

    public static final int DEFAULT_POWER_CAPACITY = 1_000;

    /*
    TODO: I may have to make my own energy variable, monitor this.
     */
    private EnergyContainerCapability energyContainer;

    protected boolean active;
    private   int     bufferedEnergy;
    private   int     energyReceive;
    private   int     energyExtract;

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn, int containerSize,
                                    CraftleBaseTier tier) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer = new EnergyContainerCapability(DEFAULT_POWER_CAPACITY, tier);
        init();
    }

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn, int containerSize,
                                    CraftleBaseTier tier, int capacity) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer = new EnergyContainerCapability(capacity, tier);
        init();
    }

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn, int containerSize,
                                    CraftleBaseTier tier, int capacity, int maxReceive,
                                    int maxExtract) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer =
                new EnergyContainerCapability(capacity, maxReceive, maxExtract, tier);
        init();
    }

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn, int containerSize,
                                    CraftleBaseTier tier, int capacity, int maxReceive,
                                    int maxExtract, int startingEnergy) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer =
                new EnergyContainerCapability(capacity, maxReceive, maxExtract, startingEnergy,
                                              tier);
        init();
    }

    public void resetBufferedEnergy() {
        this.bufferedEnergy = 0;
        this.setEnergyReceive(0);
    }

    public void setBufferedEnergy(int energy) {
        this.bufferedEnergy = energy;
    }

    public void addToBufferedEnergy(int energy) {
        this.bufferedEnergy += energy;
    }

    public void decrementBufferedEnergy(long energy) {
        this.bufferedEnergy -= energy;
    }

    public int getBufferedEnergy() {
        return bufferedEnergy;
    }

    private void init() {
        active         = false;
        bufferedEnergy = 0;
        energyReceive  = 0;
        energyExtract  = 0;
    }

    public int getEnergyReceive() {
        return energyReceive;
    }

    public void setEnergyReceive(int energyReceive) {
        this.energyReceive = energyReceive;
    }

    public int getEnergyExtract() {
        return energyExtract;
    }

    public void setEnergyExtract(int energyExtract) {
        this.energyExtract = energyExtract;
    }

    public void setBlockActive(boolean b) {
        if (active != b) {
            this.active = b;
            notifyBlockActive(b);
        }
    }

    private void notifyBlockActive(boolean b) {

        BlockState state = this.getBlockState();
        World      world = this.getWorld();
        BlockPos   pos   = this.pos;

        if (state.getBlock() instanceof ActiveBlockBase)
            ((ActiveBlockBase) state.getBlock()).changeState(b, state, world, pos);

    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return super.getUpdatePacket();

    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        return write(nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
        super.onDataPacket(net, pkt);
    }

    public EnergyContainerCapability getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public CraftleRecipeType<? extends IRecipe> getRecipeType() {
        return null;
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for (ItemStack itemstack : super.getContainerContents()) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            this.getRecipesUsed().compute(recipe.getId(), (p_214004_0_, usedCount) -> {
                return 1 + (usedCount == null ? 0 : usedCount);
            });
        }
    }

    public boolean hasCapability(Capability<?> capability, Direction direction) {
        return capability == CapabilityEnergy.ENERGY;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap,
                                             final @Nullable Direction side) {
        if (hasCapability(cap, side))
            return LazyOptional.of(() -> (T) this.energyContainer);
        return super.getCapability(cap, side);
    }


    @Override
    public CompoundNBT write(CompoundNBT compound) {

        super.write(compound);

        compound.putInt(NBTConstants.GENERATOR_BUFFERED_ENERGY, this.bufferedEnergy);
        compound.putInt(NBTConstants.ENERGY_CURRENT_EXTRACT, this.energyExtract);
        compound.putInt(NBTConstants.ENERGY_CURRENT_RECEIVE, this.energyReceive);

        this.getEnergyContainer().writeToNBT(compound);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {

        super.read(compound);
        this.getEnergyContainer().readFromNBT(compound);

        int bufferedEnergy = compound.getInt(NBTConstants.GENERATOR_BUFFERED_ENERGY);
        int energyReceive  = compound.getInt(NBTConstants.ENERGY_CURRENT_RECEIVE);
        int energyExtract  = compound.getInt(NBTConstants.ENERGY_CURRENT_EXTRACT);

        this.bufferedEnergy = bufferedEnergy;
        this.energyReceive  = energyReceive;
        this.energyExtract  = energyExtract;
        this.active         = this.bufferedEnergy > 0;
    }
}