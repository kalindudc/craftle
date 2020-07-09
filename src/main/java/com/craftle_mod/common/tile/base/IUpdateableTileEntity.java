package com.craftle_mod.common.tile.base;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public interface IUpdateableTileEntity {

    void sendUpdatePacket(TileEntity tile);

    void sendUpdatePacket();

    void handlePacket(CompoundNBT tag);
}
