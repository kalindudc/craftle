package com.craftle_mod.common.recipe.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * Used to register various Craftle machine recipes
 */
public final class EmptyInventory implements IInventory {

    public static EmptyInventory INSTANCE = new EmptyInventory();

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {

    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {

    }
}
