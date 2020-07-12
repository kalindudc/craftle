package com.craftle_mod.client.render.tile;

import com.craftle_mod.api.ColorData;
import com.craftle_mod.api.constants.BlockConstants;
import com.craftle_mod.client.render.CraftleTileEntityRenderer;
import com.craftle_mod.common.tile.storage.EnergyTankTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnergyTankRenderer extends CraftleTileEntityRenderer<EnergyTankTileEntity> {

    public static float CONTAINER_THICKNESS = 0.038f;

    public EnergyTankRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull EnergyTankTileEntity tileEntityIn, float partialTicks,
        @Nonnull MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn,
        int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn.isRemoved() || tileEntityIn.getEnergyContainer().isEmpty()) {
            return;
        }

        matrixStackIn.push();
        renderFluid(tileEntityIn, matrixStackIn, bufferIn);
        matrixStackIn.pop();
    }

    private void renderFluid(@Nonnull EnergyTankTileEntity tileEntity, MatrixStack matrix,
        IRenderTypeBuffer bufferIn) {

        double capacity = tileEntity.getEnergyContainer().getCapacity();
        double amount = tileEntity.getEnergyContainer().getEnergy();

        float scale = (float) ((1.0f - CONTAINER_THICKNESS / 2.0f - CONTAINER_THICKNESS) * amount
            / capacity);
        if (scale > 0.0f) {

            Matrix4f matrix4f = matrix.getLast().getMatrix();
            Matrix3f matrix3f = matrix.getLast().getNormal();
            TextureAtlasSprite sprite = Minecraft.getInstance()
                .getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE)
                .apply(BlockConstants.ENERGY_STILL);

            if (sprite == null) {
                return;
            }

            IVertexBuilder renderer = bufferIn
                .getBuffer(RenderType.getText(sprite.getAtlasTexture().getTextureLocation()));

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            float margin = 1.0f - CONTAINER_THICKNESS;
            float offset = CONTAINER_THICKNESS;

            float r = ColorData.ENERGY.getRgb()[0] / 255f;
            float g = ColorData.ENERGY.getRgb()[1] / 255f;
            float b = ColorData.ENERGY.getRgb()[2] / 255f;
            float a = 1.0f;
            int light = 15728880; // using value from BeaconTileEntityRenderer

            // @formatter:off
            // Top
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, scale + CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, scale + CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u1, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, scale + CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, scale + CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u2, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

            // Bottom
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u2, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u1, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

            // Sides
            //NORTH
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, scale + CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u1, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u1, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, scale + CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

            //SOUTH
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, scale + CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u2, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u2, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, scale + CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

            //WEST
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, scale + CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, margin - CONTAINER_THICKNESS, scale + CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

            //EAST
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, scale + CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, CONTAINER_THICKNESS, CONTAINER_THICKNESS + offset).color(r, g, b, a).tex(u1, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v2).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
            renderer.pos(matrix4f, CONTAINER_THICKNESS + offset, scale + CONTAINER_THICKNESS, margin - CONTAINER_THICKNESS).color(r, g, b, a).tex(u2, v1).lightmap(light).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();

            // @formatter:on
        }
    }
}
