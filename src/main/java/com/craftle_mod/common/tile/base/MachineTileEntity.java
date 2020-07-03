package com.craftle_mod.common.tile.base;

import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class MachineTileEntity extends CraftleTileEntity implements IRecipeHolder,
    IRecipeHelperPopulator, ITickableTileEntity {

    private CraftleBaseTier tier;

    // using abstract furnace information
    private final Map<ResourceLocation, Integer> recipesUsed = Maps.newHashMap();
    protected final IRecipeType<? extends IRecipe<?>> recipeType;

    private boolean dirty;

    public MachineTileEntity(TileEntityType<?> typeIn,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, int containerSize, CraftleBaseTier tier) {
        super(typeIn, containerSize);
        this.tier = tier;
        this.recipeType = recipeTypeIn;
        dirty = false;
    }

    public void resetContainerSize(int size) {
        super.setContainerSize(size);
    }

    public void setCraftleMachineTier(CraftleBaseTier tier) {
        this.tier = tier;
    }

    public CraftleBaseTier getCraftleMachineTier() {
        return tier;
    }

    public abstract CraftleRecipeType<? extends IRecipe<?>> getRecipeType();

    public Map<ResourceLocation, Integer> getRecipesUsed() {
        return recipesUsed;
    }

    //TODO: add capabilities and energy management

    public World getNonNullWorld() {
        return Objects.requireNonNull(this.getWorld());
    }

    @Override
    public void tick() {
        // if remote update client else update server
        if (getNonNullWorld().isRemote()) {
            // other client updates
            // sounds
            // render updates
            tickClient();
        } else {
            // other server updates
            // set states
            tickServer();
        }

        if (dirty) {
            markDirty();
            dirty = false;
        }
    }

    protected abstract void tickServer();

    protected abstract void tickClient();

    public boolean isDirty() {
        return dirty;
    }

    public void markTileDirty() {
        dirty = true;
    }
}
