package com.craftle_mod.common.event;

import com.craftle_mod.common.Craftle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Craftle.MODID, bus = Bus.FORGE)
public class TestJumpEvent {

    @SubscribeEvent
    public static void TestJumpEvent(LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        World        world        = livingEntity.getEntityWorld();

        livingEntity.addPotionEffect(new EffectInstance(Effects.RESISTANCE,
                                                        5000, 255));
        livingEntity.addPotionEffect(new EffectInstance(Effects.GLOWING,
                                                        50, 2));
    }
}
