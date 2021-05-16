package com.craftle_mod.data.common.loot.tables;

import com.craftle_mod.common.block.CraftleOreBlock;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.resource.ore.OreResourceTypes;
import com.craftle_mod.common.resource.ore.OreTypes;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.fml.RegistryObject;

public class CraftleBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {

        // all blocks except for ores
        for (RegistryObject<Block> registryObject : CraftleBlocks.BLOCKS.getEntries()) {
            if (!(registryObject.get() instanceof CraftleOreBlock)) {
                registerDropSelfLootTable(registryObject.get());
            }
        }

        // process compatible ores to drop raw_ore / ingots
        for (OreTypes ore : OreTypes.values()) {
            if (ore.shouldCreateOre()) {
                String key = ore.getNameWithResource(OreResourceTypes.ORE);
                String itemKey = ore.getNameWithResource(ore.getDrops()[0]);

                // redstone like property (drop more than one)
                registerLootTable(CraftleBlocks.RESOURCE_BLOCKS.get(key).get(),
                    droppingWithSilkTouch(CraftleBlocks.RESOURCE_BLOCKS.get(key).get(),
                        withExplosionDecay(CraftleBlocks.RESOURCE_BLOCKS.get(key).get(),
                            ItemLootEntry.builder(CraftleItems.RESOURCES.get(ore.getDrops()[0].getResourceType()).get(itemKey).get()))
                            .acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, ore.getDropsMax())))
                            .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))));
            }
        }
        // otherwise just the block

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return CraftleBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
