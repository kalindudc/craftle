package com.craftle_mod.common.item;

import com.craftle_mod.client.util.handler.CraftleKeyHandler;
import com.craftle_mod.common.registries.CraftleBlocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class SpecialItem extends Item {

    public SpecialItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return true;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn,
                               @Nonnull List<ITextComponent> tooltip,
                               @Nonnull ITooltipFlag flagIn) {
        if (CraftleKeyHandler.isHoldingShift()) {
            tooltip.add(new StringTextComponent("Test Information ..."));
            tooltip.add(new StringTextComponent("Test a new line ..."));
        } else {
            tooltip.add(new StringTextComponent(
                    "Hold " + "\u00A7e" + "SHIFT" + "\u00A77" + " for more " +
                            "information."));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn,
                                                    PlayerEntity playerIn,
                                                    @Nonnull Hand handIn) {
        playerIn.addPotionEffect(new EffectInstance(Effects.LEVITATION, 10, 2));
        worldIn.setRainStrength(3.0f);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        entity.world.setBlockState(entity.getPosition().down(),
                CraftleBlocks.TEST_ORE.get()
                        .getDefaultState());
        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 600;
    }
}
