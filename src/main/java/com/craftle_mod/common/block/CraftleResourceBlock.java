package com.craftle_mod.common.block;

import com.craftle_mod.common.resource.IResourceType;
import com.craftle_mod.common.resource.ResourceTypes;
import com.craftle_mod.common.resource.ore.OreTypes;
import net.minecraft.block.AbstractBlock;
import net.minecraftforge.common.ToolType;

public class CraftleResourceBlock extends CraftleBlock implements IResourceType {

    private final ResourceTypes type;

    public CraftleResourceBlock(OreTypes ore, ResourceTypes type) {
        this(AbstractBlock.Properties.create(ore.getMaterial()).hardnessAndResistance(ore.getHardness(), ore.getResistance())
            .harvestLevel(ore.getHarvestLevel()).harvestTool(ToolType.PICKAXE).setRequiresTool(), type);
    }

    public CraftleResourceBlock(Properties properties, ResourceTypes type) {
        super(properties);
        this.type = type;
    }

    @Override
    public ResourceTypes getType() {
        return type;
    }
}
