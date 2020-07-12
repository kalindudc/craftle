package com.craftle_mod.client.render;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;

public abstract class CraftleTileEntityRenderer<T extends TileEntity> extends
    TileEntityRenderer<T> {

    public CraftleTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

}
