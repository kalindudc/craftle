package com.craftle_mod.client.gui.machine.crusher;

import com.craftle_mod.api.GUIConstants;
import com.craftle_mod.common.inventory.container.machine.crusher.CrusherContainer;
import com.craftle_mod.common.tier.CraftleBaseTier;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrusherScreenFactory {

    public static <M extends CrusherContainer, U extends Screen & IHasContainer<M>> ScreenManager.IScreenFactory<M, U> build(
            CraftleBaseTier tier) {

        switch (tier) {
            case TIER_1:
                return (screenContainer, inv, titleIn) -> (U) new CrusherScreen(screenContainer,
                                                                                inv, titleIn,
                                                                                GUIConstants.CRUSHER_TIER_1);
            case TIER_2:
                return (screenContainer, inv, titleIn) -> (U) new CrusherScreen(screenContainer,
                                                                                inv, titleIn,
                                                                                GUIConstants.CRUSHER_TIER_2);
            case TIER_3:
                return (screenContainer, inv, titleIn) -> (U) new CrusherScreen(screenContainer,
                                                                                inv, titleIn,
                                                                                GUIConstants.CRUSHER_TIER_3);
            case TIER_4:
            case UNLIMITED:
                return (screenContainer, inv, titleIn) -> (U) new CrusherScreen(screenContainer,
                                                                                inv, titleIn,
                                                                                GUIConstants.CRUSHER_TIER_4);
            case BASIC:
            default:
                return (screenContainer, inv, titleIn) -> (U) new CrusherScreen(screenContainer,
                                                                                inv, titleIn,
                                                                                GUIConstants.CRUSHER_BASIC);

        }
    }
}
