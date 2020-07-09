package com.craftle_mod.api.constants;

public class TileEntityConstants {

    public static final int TICKER_PER_SECOND = 20;

    public static final double DEFAULT_POWER_CAPACITY = 10_000D;

    // GENERATORS


    // COAL GENERATOR
    public final static double COAL_GENERATOR_BASE_CAPACITY = 2_400D;
    public final static double COAL_GENERATOR_BASE_MAX_INPUT = 50D;
    public final static double COAL_GENERATOR_BASE_MAX_OUTPUT = 50D;
    public final static double COAL_GENERATOR_BURN_MULTIPLIER = 1.3D;

    // ENERGY MATRIX
    public final static double ENERGY_MATRIX_BASE_CAPACITY = 26_000D;

    // FUEL VALUES
    public final static double COAL_FUEL_VALUE = 38D;
    public final static double COAL_BLOCK_FUEL_VALUE = (int) (COAL_FUEL_VALUE * 9D * 1.25);

    // WORKBENCH

    public final static double WORKBENCH_BASE_CAPACITY = 950D;
    public final static double WORKBENCH_BASE_MAX_INPUT = 50D;
    public final static double WORKBENCH_BASE_MAX_OUTPUT = 0;
    public final static int WORKBENCH_CONTAINER_SIZE = 4 * 6;
    public final static int WORKBENCH_CRAFTING_OUTPUT_SIZE = 1;
}
