package com.craftle_mod.common.tile.machine;

import com.craftle_mod.api.constants.TileEntityConstants;
import com.craftle_mod.common.block.machine.CoalGenerator;
import com.craftle_mod.common.inventory.container.machine.GeneratorContainer;
import com.craftle_mod.common.recipe.CraftleRecipeType;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.base.GeneratorTileEntity;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public class CoalGeneratorTileEntity extends GeneratorTileEntity {

    public CoalGeneratorTileEntity(CoalGenerator block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, CraftleBaseTier tier, double capacity,
        double maxReceive, double maxExtract) {

        super(block, recipeTypeIn, tier, capacity, maxReceive, maxExtract);
        setBurnMultiplier(TileEntityConstants.COAL_GENERATOR_BURN_MULTIPLIER);
    }

    public CoalGeneratorTileEntity(CoalGenerator block,
        IRecipeType<? extends IRecipe<?>> recipeTypeIn, CraftleBaseTier tier) {
        this(block, recipeTypeIn, tier,
            (TileEntityConstants.COAL_GENERATOR_BASE_CAPACITY * tier.getMultiplier()),
            (TileEntityConstants.COAL_GENERATOR_BASE_MAX_INPUT * tier.getMultiplier()),
            (TileEntityConstants.COAL_GENERATOR_BASE_MAX_OUTPUT * tier.getMultiplier() * 1.5));
    }

    public CoalGeneratorTileEntity() {
        this((CoalGenerator) CraftleBlocks.COAL_GENERATOR.get(), CraftleRecipeType.SMELTING,
            CraftleBaseTier.BASIC);
    }

    @Nonnull
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory player) {

        return new GeneratorContainer(getBlock().getContainerType(), id, player, this,
            generatorData);
    }

    @Override
    public double getFuelValue(ItemStack stackInSlot) {

        if (Items.COAL.equals(stackInSlot.getItem()) || Items.CHARCOAL
            .equals(stackInSlot.getItem())) {
            return TileEntityConstants.COAL_FUEL_VALUE;
        }

        if (Items.COAL_BLOCK.equals(stackInSlot.getItem())) {
            return TileEntityConstants.COAL_BLOCK_FUEL_VALUE;
        }

        return 0;
    }

    @Override
    public boolean isItemFuel(ItemStack stackInSlot) {
        return getFuelValue(stackInSlot) > 0;
    }
}
