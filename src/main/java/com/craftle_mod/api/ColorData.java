package com.craftle_mod.api;

public enum ColorData {
    // @formatter:off

    // https://www.colorschemer.com/minecraft-color-codes/
    // minecraft codes
    BLACK("\u00A70", "#000000", "black", "Black"),
    DARK_BLUE("\u00A71", "#0000AA", "dark_blue", "Dark Blue"),
    DARK_GREEN("\u00A72", "#00AA00", "dark_green", "Dark Green"),
    DARK_AQUA("\u00A73", "#00AAAA", "dark_aqua", "Dark Aqua"),
    DARK_RED("\u00A74", "#AA0000", "dark_red", "Dark Red"),
    DARK_PURPLE("\u00A75", "#AA00AA", "dark_purple", "Dark Purple"),
    GOLD("\u00A76", "#FFAA00", "gold", "Gold"),
    GREY("\u00A77", "#AAAAAA", "grey", "Grey"),
    DARK_GREY("\u00A78", "#555555", "dark_grey", "Dark Grey"),
    BLUE("\u00A79", "#5555FF", "blue", "Blue"),
    GREEN("\u00A7a", "#55FF55", "green", "Green"),
    AQUA("\u00A7b", "#55FFFF", "aqua", "Aqua"),
    RED("\u00A7c", "#FF5555", "red", "Red"),
    LIGHT_PURPLE("\u00A7d", "#FF55FF", "light_purple", "Light Purple"),
    YELLOW("\u00A7e", "#FFFF55", "yellow", "Yellow"),
    WHITE("\u00A7f", "#FFFFFF", "white", "White"),
    ENERGY("", "#18ec95", "white", "White")

    // non-minecraft colors
    ;
    // @formatter:on

    private final String textCode;
    private final int[] rgb;
    private final int colorCode;
    private final String hexCode;
    private final String name;
    private final String registryName;

    ColorData(String textCode, int[] rgb, int colorCode, String hexCode, String registryName,
        String name) {
        this.textCode = textCode;
        this.rgb = rgb;
        this.colorCode = colorCode;
        this.name = name;
        this.registryName = registryName;
        this.hexCode = hexCode;
    }

    ColorData(String textCode, String hexCode, String registryName, String name) {
        this.textCode = textCode;
        this.rgb = hexToRGB(hexCode);
        this.colorCode = rgbToDecimal(rgb);
        this.name = name;
        this.registryName = registryName;
        this.hexCode = hexCode;
    }

    ColorData(String textCode, int[] rgb, String registryName, String name) {
        this.textCode = textCode;
        this.rgb = rgb;
        this.colorCode = rgbToDecimal(rgb);
        this.name = name;
        this.registryName = registryName;
        this.hexCode = rgbToHEX(rgb);
    }

    ColorData(String textCode, int colorCode, String registryName, String name) {
        this.textCode = textCode;
        this.rgb = decimalToRgb(colorCode);
        this.colorCode = colorCode;
        this.name = name;
        this.registryName = registryName;
        this.hexCode = rgbToHEX(rgb);
    }

    ColorData(String hexCode, String registryName, String name) {
        this.textCode = "";
        this.rgb = hexToRGB(hexCode);
        this.colorCode = rgbToDecimal(rgb);
        this.name = name;
        this.registryName = registryName;
        this.hexCode = hexCode;
    }

    ColorData(int[] rgb, String registryName, String name) {
        this.textCode = "";
        this.rgb = rgb;
        this.colorCode = rgbToDecimal(rgb);
        this.name = name;
        this.registryName = registryName;
        this.hexCode = rgbToHEX(rgb);
    }

    ColorData(int colorCode, String registryName, String name) {
        this.textCode = "";
        this.rgb = decimalToRgb(colorCode);
        this.colorCode = colorCode;
        this.name = name;
        this.registryName = registryName;
        this.hexCode = rgbToHEX(rgb);
    }

    public static int rgbToDecimal(int[] rgb) {

        // https://stackoverflow.com/questions/4801366/convert-rgb-values-to-integer
        int code = rgb[0];
        code = (code << 8) + rgb[1];
        code = (code << 8) + rgb[2];

        return code;
    }

    public static int[] decimalToRgb(int code) {

        // https://stackoverflow.com/questions/4801366/convert-rgb-values-to-integer
        int red = (code >> 16) & 0xFF;
        int green = (code >> 8) & 0xFF;
        int blue = code & 0xFF;

        return new int[]{red, green, blue};
    }

    public static int[] hexToRGB(String hexCode) {

        // https://stackoverflow.com/questions/31227036/java-convert-hex-color-rrggbb-to-rgb-r-g-b?lq=1
        int r = Integer.valueOf(hexCode.substring(1, 3), 16);
        int g = Integer.valueOf(hexCode.substring(3, 5), 16);
        int b = Integer.valueOf(hexCode.substring(5, 7), 16);

        return new int[]{r, g, b};
    }

    public static String rgbToHEX(int[] rgb) {

        return String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2]);
    }

    public String getTextCode() {
        return textCode;
    }

    public int[] getRgb() {
        return rgb;
    }

    public int getColorCode() {
        return colorCode;
    }

    public String getHexCode() {
        return hexCode;
    }

    public String getName() {
        return name;
    }

    public String getRegistryName() {
        return registryName;
    }
}
