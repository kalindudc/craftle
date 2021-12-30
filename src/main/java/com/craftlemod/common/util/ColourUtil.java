package com.craftlemod.common.util;

/**
 * https://stackoverflow.com/questions/4801366/convert-rgb-values-to-integer/4801397
 */
public class ColourUtil {


    public record Colour(int r, int g, int b) {

        public int encode() {
            return ColourUtil.encode(this);
        }
    }

    public static Colour createColour(int r, int g, int b) {
        return new Colour(r, g, b);
    }

    public static int encode(Colour colour) {
        int rgb = colour.r;
        rgb = (rgb << 8) + colour.g;
        rgb = (rgb << 8) + colour.b;

        return rgb;
    }

    public static Colour decode(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        return new Colour(red, green, blue);
    }

}
