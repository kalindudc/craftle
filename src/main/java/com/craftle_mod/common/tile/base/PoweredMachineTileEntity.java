package com.craftle_mod.common.tile.base;

import com.craftle_mod.api.NBTConstants;
import com.craftle_mod.common.block.base.ActiveBlockBase;
import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.capability.Capabilities;
import com.craftle_mod.common.capability.energy.CraftleEnergyStorage;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public abstract class PoweredMachineTileEntity extends MachineTileEntity implements
    ICapabilityProvider {

    public static final int DEFAULT_POWER_CAPACITY = 1_000;

    /*
    TODO: I may have to make my own energy variable, monitor this.
     */
    private final CraftleEnergyStorage energyContainer;

    protected boolean active;
    private double bufferedEnergy;
    private double energyInjectRate;
    private double energyExtractRate;

    public PoweredMachineTileEntity(MachineBlock block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, int containerSize, CraftleBaseTier tier) {

        super(block, recipeTypeIn, containerSize, tier);
        this.energyContainer = new CraftleEnergyStorage(DEFAULT_POWER_CAPACITY,
            DEFAULT_POWER_CAPACITY, 0, tier);
        init();
    }

    public PoweredMachineTileEntity(MachineBlock block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, int containerSize, CraftleBaseTier tier,
        double capacity) {

        super(block, recipeTypeIn, containerSize, tier);
        this.energyContainer = new CraftleEnergyStorage(capacity, tier);
        init();
    }

    public PoweredMachineTileEntity(MachineBlock block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, int containerSize, CraftleBaseTier tier,
        double capacity, double maxReceive, double maxExtract) {

        super(block, recipeTypeIn, containerSize, tier);
        this.energyContainer = new CraftleEnergyStorage(capacity, maxReceive, maxExtract, tier);
        init();
    }

    public PoweredMachineTileEntity(MachineBlock block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, int containerSize, CraftleBaseTier tier,
        double capacity, double maxReceive, double maxExtract, double startingEnergy) {

        super(block, recipeTypeIn, containerSize, tier);
        this.energyContainer = new CraftleEnergyStorage(capacity, maxReceive, maxExtract,
            startingEnergy, tier);
        init();
    }

    public void resetBufferedEnergy() {
        this.bufferedEnergy = 0;
        this.setEnergyInjectRate(0);
    }

    public void setBufferedEnergy(double energy) {
        this.bufferedEnergy = energy;
    }

    public void addToBufferedEnergy(double energy) {
        this.bufferedEnergy += energy;
    }

    public void decrementBufferedEnergy(double energy) {
        this.bufferedEnergy -= energy;
    }

    public double getBufferedEnergy() {
        return bufferedEnergy;
    }

    private void init() {
        active = false;
        bufferedEnergy = 0;
        energyInjectRate = 0;
        energyExtractRate = 0;
    }

    public double getEnergyInjectRate() {
        return energyInjectRate;
    }

    public void setEnergyInjectRate(double energyInjectRate) {
        this.energyInjectRate = energyInjectRate;
    }

    public double getEnergyExtractRate() {
        return energyExtractRate;
    }

    public void setEnergyExtractRate(double energyExtractRate) {
        this.energyExtractRate = energyExtractRate;
    }

    public void setBlockActive(boolean b) {
        active = b;
        notifyBlockActive(b);
    }

    private void notifyBlockActive(boolean b) {

        BlockState state = this.getBlockState();
        World world = this.getWorld();
        BlockPos pos = this.pos;

        if (!(state.get(ActiveBlockBase.LIT) && b)) {
            assert world != null;
            world.setBlockState(pos, state.with(ActiveBlockBase.LIT, b), 3);
        }
    }

    public CraftleEnergyStorage getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public CraftleRecipeType<? extends IRecipe<?>> getRecipeType() {
        return null;
    }

    @Override
    public void fillStackedContents(@Nonnull RecipeItemHelper helper) {
        for (ItemStack itemstack : super.getContainerContents()) {
            helper.accountStack(itemstack);
        }
    }

    @Override
    public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
        if (recipe != null) {
            this.getRecipesUsed().compute(recipe.getId(),
                (p_214004_0_, usedCount) -> 1 + (usedCount == null ? 0 : usedCount));
        }
    }

    public boolean hasCapability(Capability<?> capability, Direction direction) {
        return capability == Capabilities.ENERGY_CAPABILITY;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap,
        final @Nullable Direction side) {
        if (hasCapability(cap, side)) {
            return LazyOptional.of(() -> this.energyContainer).cast();
        }
        assert side != null;
        return super.getCapability(cap, side);
    }


    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT compound) {

        super.write(compound);

        compound.putDouble(NBTConstants.GENERATOR_BUFFERED_ENERGY, this.bufferedEnergy);

        this.getEnergyContainer().serializeNBT(compound);

        return compound;
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {

        super.read(compound);
        this.getEnergyContainer().deserializeNBT(compound);

        double bufferedEnergy = compound.getDouble(NBTConstants.GENERATOR_BUFFERED_ENERGY);

        this.bufferedEnergy = bufferedEnergy;
        this.active = this.bufferedEnergy > 0;
    }

    @Override
    public String toString() {
        return "PoweredMachineTileEntity{" + "energyContainer=" + energyContainer + ", active="
            + active + ", bufferedEnergy=" + bufferedEnergy + ", energyReceive=" + energyInjectRate
            + ", energyExtract=" + energyExtractRate + "super=" + super.toString() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PoweredMachineTileEntity that = (PoweredMachineTileEntity) o;
        return active == that.active && Double.compare(that.bufferedEnergy, bufferedEnergy) == 0
            && Double.compare(that.energyInjectRate, energyInjectRate) == 0
            && Double.compare(that.energyExtractRate, energyExtractRate) == 0 && Objects
            .equals(energyContainer, that.energyContainer);
    }

    @Override
    public int hashCode() {
        return Objects
            .hash(super.hashCode(), energyContainer, active, bufferedEnergy, energyInjectRate,
                energyExtractRate);
    }

    public void synchronizeEnergyContainer(ICraftleEnergyStorage storage) {
        this.energyContainer.copyFrom(storage);
    }

    public boolean validAcceptor() {
        return energyContainer.getEnergyToFill() > 0;
    }

    public void injectEnergy(double energy) {
        this.energyContainer.injectEnergy(energy);
    }
}
