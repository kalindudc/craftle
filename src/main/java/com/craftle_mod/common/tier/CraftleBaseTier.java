package com.craftle_mod.common.tier;

public enum CraftleBaseTier {
    BASIC("basic", 1.0f),
    TIER_1("tier_1", 1.5f),
    TIER_2("tier_2", 2.5f),
    TIER_3("tier_3", 4.8f),
    TIER_4("tier_4", 7.2f),
    UNLIMITED("unlimited", 1_000_000_000.0f);

    private final String tier;
    private final float  multiplier;

    CraftleBaseTier(String tier, float multiplier) {
        this.tier       = tier;
        this.multiplier = multiplier;
    }

    public String getTier() {
        return tier;
    }

    public float getMultiplier() {
        return multiplier;
    }
}
