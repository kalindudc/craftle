package com.craftle_mod.common.item;

import com.craftle_mod.api.constants.TileEntityConstants;
import com.craftle_mod.client.util.handler.CraftleKeyHandler;
import com.craftle_mod.common.registries.CraftleFluids;
import com.craftle_mod.common.util.EnergyUtils;
import com.craftle_mod.common.util.EnergyUtils.EnergyConverter;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyBucket extends BucketItem {

    public static double ENERGY = TileEntityConstants.COAL_BLOCK_FUEL_VALUE * 5.25;

    public EnergyBucket(Supplier<? extends Fluid> supplier, ItemGroup tab) {
        super(supplier,
            (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(tab));
    }

    public EnergyBucket(ItemGroup tab) {
        this(CraftleFluids.ENERGY_FLUID, tab);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn,
        @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flagIn) {
        if (CraftleKeyHandler.isHoldingShift()) {

            EnergyConverter energyStoredConverter = new EnergyConverter(
                EnergyUtils.getEnergyStoredFromItem(stack));

            tooltip.add(new StringTextComponent(String
                .format("\u00A7aEnergy: %.02f %s", energyStoredConverter.getEnergy(),
                    energyStoredConverter.getUnit())));
        } else {
            tooltip.add(new StringTextComponent("Hold \u00A7eSHIFT\u00A77 for more information."));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
