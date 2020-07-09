package com.craftle_mod.common.item;

import com.craftle_mod.api.constants.TileEntityConstants;
import com.craftle_mod.common.registries.CraftleFluids;
import java.util.function.Supplier;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;

public class EnergyBucket extends BucketItem {

    public static double ENERGY = TileEntityConstants.COAL_BLOCK_FUEL_VALUE * 2.25;

    public EnergyBucket(Supplier<? extends Fluid> supplier, ItemGroup tab) {
        super(supplier,
            (new Item.Properties()).containerItem(Items.BUCKET).maxStackSize(1).group(tab));
    }

    public EnergyBucket(ItemGroup tab) {
        this(CraftleFluids.ENERGY_FLUID, tab);
    }

}
