package com.craftle_mod.common.tier;

import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.resource.Resource;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum CraftleToolTier implements IItemTier {

    TEST("test", 4, 1500, 15.0F, 12.0F, 250,
            () -> Ingredient.fromItems(CraftleItems.TEST_INGOT.get())),
    RUBY(Resource.RUBY.getResourceName(), 2, 630, 7.0F, 2.0F, 14,
            () -> Ingredient.fromItems(CraftleItems.RUBY_INGOT.get())),
    SAPPHIRE(Resource.SAPPHIRE.getResourceName(), 2, 630, 7.0F, 2.0F, 10,
            () -> Ingredient.fromItems(CraftleItems.SAPPHIRE_INGOT.get())),
    STEEL(Resource.STEEL.getResourceName(), 3, 2100, 8.5F, 3.0F, 10,
            () -> Ingredient.fromItems(CraftleItems.STEEL_INGOT.get())),
    PLATINUM(Resource.PLATINUM.getResourceName(), 4, 2400, 9.8F, 4.0F, 12,
            () -> Ingredient.fromItems(CraftleItems.PLATINUM_INGOT.get()));


    private final String materialName;
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;
    private final boolean enhanced;

    CraftleToolTier(String materialName, int harvestLevel, int maxUses,
                    float efficiency, float attackDamage, int enchantability,
                    Supplier<Ingredient> repairMaterial) {
        this(materialName, harvestLevel, maxUses, efficiency, attackDamage,
                enchantability, repairMaterial, false);
    }

    CraftleToolTier(String materialName, CraftleToolTier tier,
                    boolean enhanced) {
        this(materialName, tier.getHarvestLevel(), tier.getMaxUses(),
                tier.getEfficiency(), tier.getAttackDamage(),
                tier.getEnchantability(), tier::getRepairMaterial,
                enhanced);
    }

    CraftleToolTier(String materialName, int harvestLevel, int maxUses,
                    float efficiency, float attackDamage, int enchantability,
                    Supplier<Ingredient> repairMaterial, boolean enhanced) {
        this.materialName = materialName;
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
        this.enhanced = enhanced;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    public boolean getEnhanced() {
        return enhanced;
    }

    public String getMaterialName() {
        return materialName;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.getValue();
    }
}
