package com.craftle_mod.common.block.base;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public interface HasTileEntity<T extends TileEntity> {

    TileEntityType<? extends T> getTileEntityType();

}
