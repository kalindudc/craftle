package com.craftle_mod.common.inventory.machine.crusher;

import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tier.CraftleBaseTier;
import com.craftle_mod.common.tile.machine.CrusherTileEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.IContainerFactory;

public class CrusherContainerFactory {

    public static IContainerFactory<CrusherContainer> buildContainerFactory(
            CraftleBaseTier tier) {

        return (windowId, playerInventory, data) -> build(tier, windowId,
                                                          playerInventory,
                                                          data);
    }

    public static CrusherContainer build(CraftleBaseTier tier, int windowId,
                                         PlayerInventory playerInventory,
                                         PacketBuffer data) {

        switch (tier) {
            case TIER_1:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_1.get(), windowId,
                        playerInventory, data);
            case TIER_2:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_2.get(), windowId,
                        playerInventory, data);
            case TIER_3:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_3.get(), windowId,
                        playerInventory, data);
            case TIER_4:
            case UNLIMITED:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_4.get(), windowId,
                        playerInventory, data);
            case BASIC:
            default:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_BASIC.get(), windowId,
                        playerInventory, data);

        }
    }

    public static CrusherContainer buildWithTileEntity(CraftleBaseTier tier,
                                                       int id,
                                                       PlayerInventory player,
                                                       CrusherTileEntity crusherTileEntity) {
        switch (tier) {
            case TIER_1:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_1.get(), id, player,
                        crusherTileEntity);
            case TIER_2:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_2.get(), id, player,
                        crusherTileEntity);
            case TIER_3:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_3.get(), id, player,
                        crusherTileEntity);
            case TIER_4:
            case UNLIMITED:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_TIER_4.get(), id, player,
                        crusherTileEntity);
            case BASIC:
            default:
                return new CrusherContainer(
                        CraftleContainerTypes.CRUSHER_BASIC.get(), id, player,
                        crusherTileEntity);

        }
    }
}
