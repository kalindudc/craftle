package com.craftle_mod.common.util;

public abstract class EnergyUtils {

    public static float joulesToKiloJoules(float energy) {
        return energy / 1000f;
    }

    public static float joulesToMegaJoules(float energy) {
        return joulesToKiloJoules(energy) / 1000f;
    }

    public static float joulesToGigaJoules(float energy) {
        return joulesToMegaJoules(energy) / 1000f;
    }

    public static float joulesToTeraJoules(float energy) {
        return joulesToGigaJoules(energy) / 1000f;
    }

    public static float joulesToPetaJoules(float energy) {
        return joulesToTeraJoules(energy) / 1000f;
    }

    public static float kiloJoulesToMegaJoules(float energy) {
        return energy / 1000f;
    }

    public static float kiloJoulesToGigaJoules(float energy) {
        return kiloJoulesToMegaJoules(energy) / 1000f;
    }

    public static float kiloJoulesToTeraJoules(float energy) {
        return kiloJoulesToGigaJoules(energy) / 1000f;
    }

    public static float kiloJoulesToPetaJoules(float energy) {
        return kiloJoulesToTeraJoules(energy) / 1000f;
    }

    public static float megaJoulesToGigaJoules(float energy) {
        return energy / 1000f;
    }

    public static float megaJoulesToTeraJoules(float energy) {
        return megaJoulesToGigaJoules(energy) / 1000f;
    }

    public static float megaJoulesToPetaJoules(float energy) {
        return megaJoulesToTeraJoules(energy) / 1000f;
    }

    public static float gigaJoulesToTeraJoules(float energy) {
        return energy / 1000f;
    }

    public static float gigaJoulesToPetaJoules(float energy) {
        return gigaJoulesToTeraJoules(energy) / 1000f;
    }

    public static float teraJoulesToPetaJoules(float energy) {
        return energy / 1000f;
    }

    public static float petaJoulesToTeraJoules(float energy) {
        return energy * 1000f;
    }

    public static float petaJoulesToGigaJoules(float energy) {
        return petaJoulesToTeraJoules(energy) * 1000f;
    }

    public static float petaJoulesToMegaJoules(float energy) {
        return petaJoulesToGigaJoules(energy) * 1000f;
    }

    public static float petaJoulesToKiloJoules(float energy) {
        return petaJoulesToMegaJoules(energy) * 1000f;
    }

    public static float petaJoulesToJoules(float energy) {
        return petaJoulesToKiloJoules(energy) * 1000f;
    }

    public static float teraJoulesToGigaJoules(float energy) {
        return energy * 1000f;
    }

    public static float teraJoulesToMegaJoules(float energy) {
        return teraJoulesToGigaJoules(energy) * 1000f;
    }

    public static float teraJoulesToKiloJoules(float energy) {
        return teraJoulesToMegaJoules(energy) * 1000f;
    }

    public static float teraJoulesToJoules(float energy) {
        return teraJoulesToKiloJoules(energy) * 1000f;
    }

    public static float gigaJoulesToMegaJoules(float energy) {
        return energy * 1000f;
    }

    public static float gigaJoulesToKiloJoules(float energy) {
        return gigaJoulesToMegaJoules(energy) * 1000f;
    }

    public static float gigaJoulesToJoules(float energy) {
        return gigaJoulesToKiloJoules(energy) * 1000f;
    }

    public static float megaJoulesToKiloJoules(float energy) {
        return energy * 1000f;
    }

    public static float megaJoulesToJoules(float energy) {
        return megaJoulesToKiloJoules(energy) * 1000f;
    }

    public static float kiloJoulesToJoules(float energy) {
        return energy * 1000f;
    }

}
