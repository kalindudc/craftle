package com.craftle_mod.common.tile.base;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.block.base.ActiveBlockBase;
import com.craftle_mod.common.capability.energy.EnergyContainerCapability;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
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

    private ActiveBlockBase block;
    private boolean         active;
    private int             bufferedEnergy;

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn,
                                    int containerSize, CraftleBaseTier tier) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer =
                new EnergyContainerCapability(DEFAULT_POWER_CAPACITY);
        init();
    }

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn,
                                    int containerSize, CraftleBaseTier tier,
                                    int capacity) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer = new EnergyContainerCapability(capacity);
        init();
    }

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn,
                                    int containerSize, CraftleBaseTier tier,
                                    int capacity, int maxRecieve,
                                    int maxExtract) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer =
                new EnergyContainerCapability(capacity, maxRecieve, maxExtract);
        init();
    }

    public PoweredMachineTileEntity(TileEntityType<?> typeIn,
                                    IRecipeType<? extends IRecipe> recipeTypeIn,
                                    int containerSize, CraftleBaseTier tier,
                                    int capacity, int maxRecieve,
                                    int maxExtract, int startingEnergy) {
        super(typeIn, recipeTypeIn, containerSize, tier);
        this.energyContainer =
                new EnergyContainerCapability(capacity, maxRecieve, maxExtract,
                                              startingEnergy);
        init();
    }

    public void resetBufferedEnergy() {
        this.bufferedEnergy = 0;
    }

    public void setBufferedEnergy(int energy) {
        this.bufferedEnergy = energy;
    }

    public void addToBufferedEnergy(int energy) {
        this.bufferedEnergy += energy;
    }

    public void decrementBufferedEnergy(int energy) {
        this.bufferedEnergy -= energy;
    }

    public int getBufferedEnergy() {
        return bufferedEnergy;
    }

    private void init() {
        block          = null;
        active         = false;
        bufferedEnergy = 0;
    }


    public void setBlockActive(boolean b) {
        if (active != b && block != null) {
            this.active = b;
            notifyBlockActive(b);
        }
    }

    public void addBlock(ActiveBlockBase block) {
        this.block = block;
    }

    private void notifyBlockActive(boolean b) {

        BlockState state = this.getBlockState();
        World      world = this.getWorld();
        BlockPos   pos   = this.pos;
        Craftle.LOGGER.info(String.format("CRAFTLE: setting block."));
        block.changeState(b, state, world, pos);
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
            this.getRecipesUsed()
                .compute(recipe.getId(), (p_214004_0_, usedCount) -> {
                    return 1 + (usedCount == null ? 0 : usedCount);
                });
        }
    }

    public boolean hasCapability(Capability<?> capability,
                                 Direction direction) {
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
}
