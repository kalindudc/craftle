package com.craftle_mod.common.tier;

import com.craftle_mod.common.init.CraftleItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import java.util.function.Supplier;

public enum CraftleItemTier implements IItemTier {

    TEST(4, 1500, 15.0F, 12.0F, 250,
         () -> Ingredient.fromItems(CraftleItems.TEST_INGOT));

    private final int                   harvestLevel;
    private final int                   maxUses;
    private final float                 efficiency;
    private final float                 attackDamage;
    private final int                   enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    CraftleItemTier(int harvestLevel, int maxUses, float efficiency,
                    float attackDamage, int enchantability,
                    Supplier<Ingredient> repairMaterial) {
        this.harvestLevel   = harvestLevel;
        this.maxUses        = maxUses;
        this.efficiency     = efficiency;
        this.attackDamage   = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    @Override
    public int getMaxUses() {
        return maxUses;
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
