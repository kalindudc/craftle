package com.craftle_mod.common.block;

import com.craftle_mod.common.resource.IResourceType;
import com.craftle_mod.common.resource.OreTypes;
import com.craftle_mod.common.resource.ResourceTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.OreBlock;

public class CraftleOreBlock extends OreBlock implements IResourceType {

    public CraftleOreBlock(OreTypes oreType) {
        super(getProperties(oreType));
    }

    public static Properties getProperties(OreTypes ore) {
        return AbstractBlock.Properties.create(ore.getMaterial()).hardnessAndResistance(ore.getHardness(), ore.getResistance())
            .setRequiresTool().harvestLevel(ore.getHarvestLevel());
    }


    @Override
    public ResourceTypes getType() {
        return ResourceTypes.ORE;
    }
}
