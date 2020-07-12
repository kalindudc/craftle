package com.craftle_mod.common.tile.base;

import com.craftle_mod.api.constants.GUIConstants;
import com.craftle_mod.api.constants.NBTConstants;
import com.craftle_mod.api.constants.TileEntityConstants;
import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.base.ActiveBlockBase;
import com.craftle_mod.common.block.base.MachineBlock;
import com.craftle_mod.common.capability.Capabilities;
import com.craftle_mod.common.capability.energy.CraftleEnergyStorage;
import com.craftle_mod.common.capability.energy.ICraftleEnergyStorage;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.item.EnergyItem;
import com.craftle_mod.common.network.packet.EnergyItemUpdatePacket;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.util.EnergyUtils;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
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

    /*
    TODO: I may have to make my own energy variable, monitor this.
     */
    private final CraftleEnergyStorage energyContainer;

    protected boolean active;
    private double bufferedEnergy;
    private double energyInjectRate;
    private double energyExtractRate;

    private int infoScreenWidth;
    private int infoScreenHeight;

    /**
     * Used to calculate inject rate and extract rate when energy is directly injected / extracted
     * from the energy container.
     */
    private double previousEnergyIncrement;

    public PoweredMachineTileEntity(MachineBlock block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, int containerSize, CraftleBaseTier tier) {

        super(block, recipeTypeIn, containerSize, tier);
        this.energyContainer = new CraftleEnergyStorage(TileEntityConstants.DEFAULT_POWER_CAPACITY,
            TileEntityConstants.DEFAULT_POWER_CAPACITY, 0, tier);
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

    @Nonnull
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory player) {

        return new EnergyContainer(getBlock().getContainerType(), id, player, this);
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

        infoScreenWidth = GUIConstants.INFO_SCREEN_WIDTH;
        infoScreenHeight = GUIConstants.INFO_SCREEN_HEIGHT;
        previousEnergyIncrement = energyContainer.getEnergy();
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

        this.bufferedEnergy = compound.getDouble(NBTConstants.GENERATOR_BUFFERED_ENERGY);
        this.active = this.bufferedEnergy > 0;
    }

    public CompoundNBT getTileUpdateTag() {
        return write(getUpdateTag());
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
        return !energyContainer.isFilled() && getEnergyInjectRate() < energyContainer
            .getMaxInjectRate();
    }

    public double injectEnergy(double energy) {

        double injectedEnergy = this.energyContainer.injectEnergy(energy);

        this.incrementInjectRate(injectedEnergy);
        return injectedEnergy;
    }

    public abstract boolean canEmitEnergy();

    @Override
    protected void tickServer() {

        // reset energy inject and extract
        this.setEnergyInjectRate(0);
        this.setEnergyExtractRate(0);

        // handle any energy that was given directly to the energy container
        if (previousEnergyIncrement < energyContainer.getEnergy()) {
            this.incrementInjectRate(energyContainer.getEnergy() - previousEnergyIncrement);
        }

        if (previousEnergyIncrement > energyContainer.getEnergy()) {
            this.incrementExtractRate(previousEnergyIncrement - energyContainer.getEnergy());
        }

        this.previousEnergyIncrement = energyContainer.getEnergy();
    }

    public void emitEnergy() {
        if (canEmitEnergy()) {
            // emit energy
            if (!getEnergyContainer().isEmpty()) {
                this.incrementExtractRate(EnergyUtils.emitEnergy(getEnergyContainer(), this,
                    getEnergyContainer().getMaxExtractRate()
                        / TileEntityConstants.TICKER_PER_SECOND));
            }
        }
    }

    public void incrementExtractRate(double energy) {
        this.previousEnergyIncrement -= energy;
        this.setEnergyExtractRate(energy + getEnergyExtractRate());
    }

    public void incrementInjectRate(double energy) {
        this.previousEnergyIncrement += energy;
        this.setEnergyInjectRate(energy + getEnergyInjectRate());
    }

    @Override
    protected void tickClient() {
    }

    public int getInfoScreenWidth() {
        return infoScreenWidth;
    }

    public void setInfoScreenWidth(int infoScreenWidth) {
        this.infoScreenWidth = infoScreenWidth;
    }

    public int getInfoScreenHeight() {
        return infoScreenHeight;
    }

    public void setInfoScreenHeight(int infoScreenHeight) {
        this.infoScreenHeight = infoScreenHeight;
    }

    public void handlePacket(double injectRate, double extractRate) {
        this.setEnergyInjectRate(injectRate);
        this.setEnergyExtractRate(extractRate);
    }

    public double extractFromItemSlot(ItemStack extractStack) {

        double energyExtract = 0;

        // check for an item in extract
        double stackEnergyLevel = EnergyUtils.getEnergyPercentageFromItem(extractStack);
        if (validToReceive(extractStack) && !getEnergyContainer().isEmpty()
            && stackEnergyLevel < 1.0D) {

            double toExtract = EnergyUtils.getEnergyRequiredForItem(extractStack);
            double received;
            double extracted;
            if (toExtract < this.getEnergyContainer().getEnergy()) {
                received = EnergyUtils.injectEnergyToItem(extractStack, toExtract);
            } else {
                received = EnergyUtils
                    .injectEnergyToItem(extractStack, this.getEnergyContainer().getEnergy());
            }

            extracted = this.extractEnergy(received);
            energyExtract = extracted;

            // send packet to client to notify the item stack was given energy
            Craftle.packetHandler
                .sendToTrackingClients(new EnergyItemUpdatePacket(extractStack, this.getPos()),
                    this);
        }

        return energyExtract;
    }

    private double extractEnergy(double energy) {

        double extracted = this.energyContainer.extractEnergy(energy);
        this.setEnergyExtractRate(extracted + getEnergyExtractRate());

        return extracted;
    }

    public double injectFromItemSlot(ItemStack injectStack) {

        double energyReceive = 0;
        // check for an item in inject
        if (!injectStack.isEmpty() && isItemFuel(injectStack)) {

            double storedEnergy = getFuelValue(injectStack);
            double received;

            if (storedEnergy < getEnergyContainer().getEnergyToFill()) {

                received = this.injectEnergy(storedEnergy);
            } else {

                received = this.injectEnergy(getEnergyContainer().getEnergyToFill());
            }

            EnergyUtils.extractEnergyFromItem(injectStack, received);
            energyReceive = received;

            // send packet to notify client that energy was extracted from the item stack
            Craftle.packetHandler
                .sendToTrackingClients(new EnergyItemUpdatePacket(injectStack, this.getPos()),
                    this);
        }

        return energyReceive;
    }

    private boolean validToReceive(ItemStack stack) {
        return stack.getItem() instanceof EnergyItem;
    }


    private double getFuelValue(ItemStack stack) {
        if (validToReceive(stack)) {
            return EnergyUtils.getEnergyStoredFromItem(stack);
        }

        return 0;
    }

    private boolean isItemFuel(ItemStack stackInSlot) {
        return getFuelValue(stackInSlot) > 0;
    }
}
