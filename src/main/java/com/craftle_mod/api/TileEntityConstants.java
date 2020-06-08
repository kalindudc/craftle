package com.craftle_mod.api;

public class TileEntityConstants {

    // GENERATORS


    // COAL GENERATOR
    public final static int COAL_GENERATOR_BASE_CAPACITY   = 100_000;
    public final static int COAL_GENERATOR_BASE_MAX_INPUT  =
            (int) (COAL_GENERATOR_BASE_CAPACITY * 0.001);
    public final static int COAL_GENERATOR_BASE_MAX_OUTPUT = COAL_GENERATOR_BASE_MAX_INPUT;
    public final static int COAL_GENERATOR_BURN_MULTIPLIER = 2;
}
