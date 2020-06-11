package com.craftle_mod.common.inventory.container.machine;

import com.craftle_mod.common.inventory.container.base.EnergyContainer;
import com.craftle_mod.common.registries.CraftleBlocks;
import com.craftle_mod.common.registries.CraftleContainerTypes;
import com.craftle_mod.common.tile.base.MachineTileEntity;
import com.craftle_mod.common.tile.base.PoweredMachineTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.world.World;

import java.util.Optional;

public class WorkBenchContainer extends EnergyContainer {

    private final CraftingInventory    craftMatrix;
    private final CraftResultInventory craftResult;

    public WorkBenchContainer(ContainerType<?> container, int windowId,
                              PlayerInventory playerInventory, PoweredMachineTileEntity entity) {
        super(container, windowId, playerInventory, entity);
        craftMatrix = new CraftingInventory(this, 3, 3);
        craftResult = new CraftResultInventory();
        initSlots();
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    public WorkBenchContainer(ContainerType<?> container, int windowId,
                              PlayerInventory playerInventory, PacketBuffer data) {
        super(container, windowId, playerInventory, data);
        craftMatrix = new CraftingInventory(this, 3, 3);
        craftResult = new CraftResultInventory();
        initSlots();
        this.onCraftMatrixChanged(this.craftMatrix);
    }

    public WorkBenchContainer(int windowId, PlayerInventory playerInventory,
                              PacketBuffer packetBuffer) {
        this(CraftleContainerTypes.WORKBENCH.get(), windowId, playerInventory, packetBuffer);
    }

    @Override
    public void initSlots() {

        loadToCraftMatrix();

        int startX = 30;
        int startY = 17;
        // slots for crafting
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                addCraftingContainerSlot(((i * 3) + j), startX + (j * 18), startY + (i * 18));
            }
        }

        // slots for output
        addContainerResultSlot(0, 124, 35);

        startX = 184;
        startY = 70;
        // slots for container
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                addContainerSlot(10 + ((i * 5)) + j, startX + (j * 18), startY + (i * 18));
            }
        }
        //addContainerBlankSlot(0, 112, 12, 135 - 112, 73 - 12);

        // Main Player Inventory
        int startPlayerInvX = 8;
        int startPlayerInvY = 84;
        addPlayerInventorySlots(startPlayerInvX, startPlayerInvY, 18);
    }

    public void addContainerResultSlot(int index, int inputX, int inputY) {
        this.addSlot(new CraftingResultSlot(this.getPlayerInventory().player, this.craftMatrix,
                                            this.craftResult, index, inputX, inputY));
    }

    public void addCraftingContainerSlot(int index, int inputX, int inputY) {
        this.addSlot(new Slot(craftMatrix, index, inputX, inputY));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        if (!(getEntity() instanceof MachineTileEntity))
            throw new IllegalStateException("Tile entity is not correct. ");

        return isWithinUsableDistance(getCanInteractWithCallable(), playerIn,
                                      CraftleBlocks.WORKBENCH.get());
    }

    protected static void updateCraftingResult(int id, World worldIn, PlayerEntity playerIn,
                                               CraftingInventory inventoryIn,
                                               CraftResultInventory inventoryResult) {
        if (!worldIn.isRemote) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) playerIn;
            ItemStack          itemstack          = ItemStack.EMPTY;
            Optional<ICraftingRecipe> optional = worldIn.getServer().getRecipeManager()
                                                        .getRecipe(IRecipeType.CRAFTING,
                                                                   inventoryIn, worldIn);
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
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.getWorldPosCallable().consume((world, blockPos) -> {
            updateCraftingResult(this.windowId, world, this.getPlayerInventory().player,
                                 this.craftMatrix, this.craftResult);
        });
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
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
                ItemStack stack = this.getEntity().getContainerContents().get((row * 3) + col);
                craftMatrix.setInventorySlotContents((row * 3) + col, stack);
            }
        }
    }
}
