package com.craftle_mod.common.resource;

public enum Resource implements ICraftleResource {
    // minecraft
    IRON("iron", "Iron"),
    GOLD("gold", "Gold"),
    DIAMOND("diamond", "Diamond"), // craftle
    COPPER("copper", "Copper"),
    TIN("tin", "Tin"),
    STEEL("steel", "Steel"),
    ALUMINIUM("aluminium", "Aluminium"),
    PLATINUM("platinum", "Platinum"),
    URANIUM("uranium", "Uranium"),
    RUBY("ruby", "Ruby"),
    SAPPHIRE("sapphire", "Sapphire");

    private final String name;
    private final String formattedName;

    Resource(String name, String formattedName) {
        this.name = name;
        this.formattedName = formattedName;
    }

    @Override
    public String getResourceName() {
        return name;
    }

    @Override
    public String getFormattedName() {
        return formattedName;
    }
}
