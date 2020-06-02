package com.craftle_mod.common.util;

import com.craftle_mod.common.tile.TileEntityQuarry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NBTUtils {

    public static CompoundNBT toNBT(Object object) {
        if (object instanceof ItemStack) {
            return writeItemStack((ItemStack) object);
        }

        if (object instanceof TileEntityQuarry) {
            return writeQuarry((TileEntityQuarry) object);
        }

        return null;
    }


    private static CompoundNBT writeQuarry(TileEntityQuarry o) {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("x", o.getX());
        compound.putInt("y", o.getY());
        compound.putInt("z", o.getZ());

        return compound;
    }

    private static CompoundNBT writeItemStack(ItemStack o) {
        CompoundNBT compound = new CompoundNBT();

        compound.putInt("count", o.getCount());
        compound.putString("item", o.getItem().getRegistryName().toString());
        compound.putByte("type", (byte) 0);

        return compound;
    }

    @Nullable
    public static Object fromNBT(@Nonnull CompoundNBT compound) {
        switch (compound.getByte("type")) {
            case 0:
                return readItemStack(compound);
            default:
                return null;
        }
    }

    private static ItemStack readItemStack(CompoundNBT compound) {
        Item item = ForgeRegistries.ITEMS
                .getValue(new ResourceLocation(compound.getString("item")));
        int count = compound.getInt("count");
        return new ItemStack(item, count);
    }

}
