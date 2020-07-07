package com.craftle_mod.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public abstract class CraftleGuiUtils {

    public static final int TEXTURE_WIDTH = 16;
    public static final int TEXTURE_HEIGHT = 16;

    public static boolean isWithinBounds(int x, int y, int xMin, int yMin, int xMax, int yMax) {
        return x <= xMax && x >= xMin && y <= yMax && y >= yMin;
    }

    /**
     * The expected resource location dimensions are 16x16. This method will render an extended
     * version of the given resource.
     */
    public static void drawExtendedGUI(ResourceLocation resource, int borderWidth, int borderHeight,
        int left, int top, int width, int height) {

        Minecraft.getInstance().textureManager.bindTexture(resource);

        int centerScreenWidth = width - (borderWidth * 2);
        int centerScreenHeight = height - (borderHeight * 2);
        int centerRightCorner = left + borderWidth + centerScreenWidth;
        int centerLeftCorner = left + borderWidth;
        int centerTopCorner = top + borderHeight;
        int centerBottomCorner = top + borderHeight + centerScreenHeight;
        int textureCenterRightCorner = TEXTURE_WIDTH - borderWidth;
        int textureCenterBottomCorner = TEXTURE_HEIGHT - borderHeight;
        int borderSizeVertical = TEXTURE_HEIGHT - (borderHeight * 2);
        int borderSizeHorizontal = TEXTURE_WIDTH - (borderWidth * 2);

        // @formatter:off
        // top left corner
        AbstractGui.blit(left, top, 0, 0, borderWidth, borderHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        // top right corner
        AbstractGui
            .blit(centerRightCorner, top, textureCenterRightCorner, 0, borderWidth, borderHeight,
                TEXTURE_WIDTH, TEXTURE_HEIGHT);
        // bottom left corner
        AbstractGui
            .blit(left, centerBottomCorner, 0, textureCenterBottomCorner, borderWidth, borderHeight,
                TEXTURE_WIDTH, TEXTURE_HEIGHT);
        // bottom right corner
        AbstractGui.blit(centerRightCorner, centerBottomCorner, textureCenterRightCorner,
            textureCenterBottomCorner, borderWidth, borderHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        // left border
        AbstractGui.blit(
            left,                               // x
            centerTopCorner,                    // y
            borderWidth,                        // desired width
            centerScreenHeight,                 // desired height
            0,                          // texture x
            borderHeight,                       // texture y
            borderWidth,                        // width
            borderSizeVertical,                 // height
            TEXTURE_WIDTH,                      // texture width
            TEXTURE_HEIGHT                      // texture height
        );

        // right border

        AbstractGui.blit(
            centerRightCorner,
            centerTopCorner,
            borderWidth,
            centerScreenHeight,
            textureCenterRightCorner,
            borderHeight,
            borderWidth,
            borderSizeVertical,
            TEXTURE_WIDTH,
            TEXTURE_HEIGHT
        );

        // top border
        AbstractGui.blit(
            centerLeftCorner,
            top,
            centerScreenWidth,
            borderHeight,
            borderWidth,
            0,
            borderSizeHorizontal,
            borderHeight,
            TEXTURE_WIDTH,
            TEXTURE_HEIGHT);

        // bottom border
        AbstractGui.blit(
            centerLeftCorner,
            centerBottomCorner,
            centerScreenWidth,
            borderHeight,
            borderWidth,
            textureCenterBottomCorner,
            borderSizeHorizontal,
            borderHeight,
            TEXTURE_WIDTH,
            TEXTURE_HEIGHT
        );

        // center
        AbstractGui
            .blit(centerLeftCorner, centerTopCorner, centerScreenWidth, centerScreenHeight, borderWidth, borderHeight,
                borderSizeHorizontal, borderSizeVertical, TEXTURE_WIDTH, TEXTURE_HEIGHT);

        // @formatter:on
    }

    // @formatter:off
    //Some blit param namings , credit: Mekanism
    //blit(int x, int y, int textureX, int textureY, int width, int height); non static
    //blit(int x, int y, TextureAtlasSprite icon, int width, int height);
    //blit(int x, int y, int zLevel, float textureX, float textureY, int width, int height, int textureWidth, int textureHeight);

    //blit(int x, int y, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);


    //blit(int x, int y, int desiredWidth, int desiredHeight, int textureX, int textureY, int width, int height, int textureWidth, int textureHeight);


    //innerBlit(int x, int endX, int y, int endY, int zLevel, int width, int height, float textureX, float textureY, int textureWidth, int textureHeight);
    // @formatter:on

}
