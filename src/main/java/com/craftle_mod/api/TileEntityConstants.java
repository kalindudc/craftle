package com.craftle_mod.api;

public class TileEntityConstants {

    public static final int TICKER_PER_SECOND = 20;

    // GENERATORS


    // COAL GENERATOR
    public final static int COAL_GENERATOR_BASE_CAPACITY = 240;
    public final static int COAL_GENERATOR_BASE_MAX_INPUT = 5;
    public final static int COAL_GENERATOR_BASE_MAX_OUTPUT = 5;
    public final static int COAL_GENERATOR_BURN_MULTIPLIER = 10;

    // ENERGY MATRIX
    public final static int ENERGY_MATRIX_BASE_CAPACITY = 2600;

    // FUEL VALUES
    public final static int COAL_FUEL_VALUE = 1;
    public final static int COAL_BLOCK_FUEL_VALUE = (int) (COAL_FUEL_VALUE * 9 * 1.25);

    // WORKBENCH

    public final static int WORKBENCH_BASE_CAPACITY = 95;
    public final static int WORKBENCH_BASE_MAX_INPUT = 5;
    public final static int WORKBENCH_BASE_MAX_OUTPUT = 0;
    public final static int WORKBENCH_CONTAINER_SIZE = 5 * 5;
    public final static int WORKBENCH_CRAFTING_OUTPUT_SIZE = 1;

}
