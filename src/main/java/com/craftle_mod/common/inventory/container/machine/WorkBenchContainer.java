package com.craftle_mod.common.inventory.container.machine;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import com.craftle_mod.common.tile.machine.WorkBenchTileEntity;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.world.World;

public class WorkBenchContainer extends EnergyContainer {

    private CraftingInventory craftMatrix;
    private CraftResultInventory craftResult;

    public WorkBenchContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);
    }

    public WorkBenchContainer(ContainerType<?> container, int windowId,
        PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);
    }

    public WorkBenchContainer(int windowId, PlayerInventory playerInventory,
        PacketBuffer packetBuffer) {
        this(CraftleContainerTypes.WORKBENCH.get(), windowId, playerInventory, packetBuffer);
    }

    @Override
    public void initSlots() {

        craftMatrix = new CraftingInventory(this,
            ((WorkBenchTileEntity) this.getEntity()).getCraftingMatrixSlotData().getNumCols(),
            ((WorkBenchTileEntity) this.getEntity()).getCraftingMatrixSlotData().getNumRows());
        craftResult = new CraftResultInventory();
        this.onCraftMatrixChanged(this.craftMatrix);

        loadToCraftMatrix();
        ((WorkBenchTileEntity) this.getEntity()).getCraftingMatrixSlotData()
            .setInventory(craftMatrix);
        ((WorkBenchTileEntity) this.getEntity()).getCraftingResultSlotData()
            .setInventory(craftResult);
        super.initSlots();
    }

    protected static void updateCraftingResult(int id, World worldIn, PlayerEntity playerIn,
        CraftingInventory inventoryIn, CraftResultInventory inventoryResult) {
        if (!worldIn.isRemote) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) playerIn;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optional = Objects.requireNonNull(worldIn.getServer())
                .getRecipeManager().getRecipe(IRecipeType.CRAFTING, inventoryIn, worldIn);
            if (optional.isPresent()) {
                ICraftingRecipe icraftingrecipe = optional.get();
                if (inventoryResult.canUseRecipe(worldIn, serverplayerentity, icraftingrecipe)) {
                    itemstack = icraftingrecipe.getCraftingResult(inventoryIn);
                }
            }

            inventoryResult.setInventorySlotContents(9, itemstack);
            serverplayerentity.connection.sendPacket(new SSetSlotPacket(id, 9, itemstack));
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    public void onCraftMatrixChanged(@Nonnull IInventory inventoryIn) {
        this.getWorldPosCallable().consume(
            (world, blockPos) -> updateCraftingResult(this.windowId, world,
                this.getPlayerInventory().player, this.craftMatrix, this.craftResult));
    }

    @Override
    public void onContainerClosed(@Nonnull PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        // save to inv
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ItemStack stack = craftMatrix.getStackInSlot((row * 3) + col);
                this.getEntity().addToContainer(stack, (row * 3) + col);
            }
        }
    }

    private void loadToCraftMatrix() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int index = ((WorkBenchTileEntity) this.getEntity()).getCraftingMatrixSlotData()
                    .getIndex(row, col);

                ItemStack stack = this.getEntity().getContainerContents().get(index);
                Craftle.logInfo("Craft matrix: " + craftMatrix);
                craftMatrix.setInventorySlotContents(index, stack);
            }
        }
    }
}
