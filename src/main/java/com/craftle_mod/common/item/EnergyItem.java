package com.craftle_mod.common.item;

import com.craftle_mod.api.ItemConstants;
import com.craftle_mod.client.util.handler.CraftleKeyHandler;
import com.craftle_mod.common.item.base.CraftleItem;
import com.craftle_mod.common.item.base.EnergyProvider;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.util.EnergyUtils;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class EnergyItem extends CraftleItem {

    private int input;
    private int output;
    private final int maxCapacity;
    private final CraftleBaseTier tier;

    public EnergyItem(String registryName, ItemGroup tab, CraftleBaseTier tierConfig) {

        super(registryName + "_" + tierConfig.getTier(), new Item.Properties().maxStackSize(1),
            tab);

        this.maxCapacity = (int) (ItemConstants.BASE_ENERGY_CAPACITY * tierConfig.getMultiplier());
        this.input = 0;
        this.output = 0;
        this.tier = tierConfig;
    }

    public float getInput() {
        return input;
    }

    public float getOutput() {
        return output;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {

        return 1.0D - EnergyUtils.getEnergyPercentageFromItem(stack);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn,
        @Nonnull PlayerEntity playerIn,
        @Nonnull Hand handIn) {
        // TODO: use on armor

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn,
        @Nonnull List<ITextComponent> tooltip,
        @Nonnull ITooltipFlag flagIn) {
        if (CraftleKeyHandler.isHoldingShift()) {

            int energy = EnergyUtils.getEnergyStoredFromItem(stack);
            int capacity = EnergyUtils.getEnergyCapacityFromItem(stack);

            tooltip.add(new StringTextComponent(String.format("Capacity: %.02f %s", EnergyUtils
                .getJoulesForTierItem(tier, capacity), EnergyUtils.getUnitForTierItem(tier))));
            tooltip.add(new StringTextComponent(String.format("\u00A7aEnergy: %.02f %s", EnergyUtils
                .getJoulesForTierItem(tier, energy), EnergyUtils.getUnitForTierItem(tier))));
        } else {
            tooltip.add(new StringTextComponent("Hold \u00A7eSHIFT\u00A77 for more information."));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new EnergyProvider(maxCapacity, maxCapacity, maxCapacity,
            tier.equals(CraftleBaseTier.UNLIMITED) ? maxCapacity : 0, tier);
    }


}
