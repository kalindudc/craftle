package com.craftle_mod.common.tier;

public enum CraftleBaseTier {
    BASIC("basic", 1.0f, "Basic"), TIER_1("tier_1", 120.5f, "Tier 1"), TIER_2("tier_2", 1400.5f,
        "Tier 2"), TIER_3("tier_3", 19050.8f, "Tier 3"), TIER_4("tier_4", 508000.2f,
        "Tier 4"), UNLIMITED("unlimited", 1_000_000_000.0f, "UNLIMITED");

    private final String tier;
    private final float multiplier;
    private final String formattedName;

    CraftleBaseTier(String tier, float multiplier, String formattedName) {
        this.tier = tier;
        this.multiplier = multiplier;
        this.formattedName = formattedName;
    }

    public String getTier() {
        return tier;
    }

    public float getMultiplier() {
        return multiplier;
    }

    public String getFormattedName() {
        return formattedName;
    }

    @Override
    public String toString() {
        return "CraftleBaseTier{" + "tier='" + tier + '\'' + ", multiplier=" + multiplier
            + ", formattedName='" + formattedName + '\'' + '}';
    }
}
