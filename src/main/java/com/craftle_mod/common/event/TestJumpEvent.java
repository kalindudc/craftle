package com.craftle_mod.common.event;

import com.craftle_mod.common.Craftle;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Craftle.MODID, bus = Bus.FORGE)
public class TestJumpEvent {

    @SubscribeEvent
    public static void TestJumpEvent(LivingJumpEvent event) {
        
    }
}
