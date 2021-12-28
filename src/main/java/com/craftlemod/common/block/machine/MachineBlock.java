package com.craftlemod.common.block.machine;

import com.craftlemod.common.blockentity.CraftleBlockEntity;
import com.craftlemod.common.blockentity.FactoryBlockEntity;
import com.craftlemod.common.shared.IHasModelPath;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MachineBlock extends BlockWithEntity implements IHasModelPath {

    private final Identifier id;

    private FactoryBlockEntity controller;
    private String modelPath;
    private CraftleBlockEntity entity;

    public MachineBlock(Identifier id, String modelPath, Settings settings) {
        super(settings);
        this.id = id;
        this.modelPath = modelPath;
        this.controller = null;
        this.entity = null;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return null;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public String getModelPath() {
        return modelPath;
    }

    @Override
    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    public FactoryBlockEntity getController() {
        return controller;
    }

    public void setController(FactoryBlockEntity controller) {
        this.controller = controller;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            BlockPos controllerPos = pos;
            if (this.entity == null && this.controller == null) {
                return ActionResult.PASS;
            }

            if (this.controller != null) {
                controllerPos = getController().getPos();
            }

            //This will call the createScreenHandlerFactory method from BlockWithEntity, which will return our blockEntity casted to
            //a namedScreenHandlerFactory. If your block class does not extend BlockWithEntity, it needs to implement createScreenHandlerFactory.
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, controllerPos);

            if (screenHandlerFactory != null) {
                //With this call the server will request the client to open the appropriate Screenhandler
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    //This method will drop all items onto the ground when the block is broken
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof com.craftlemod.common.blockentity.FactoryBlockEntity) {
                ItemScatterer.spawn(world, pos, (com.craftlemod.common.blockentity.FactoryBlockEntity) blockEntity);
                // update comparators
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos));
    }

    public CraftleBlockEntity getEntity() {
        return entity;
    }

    public void setEntity(CraftleBlockEntity entity) {
        this.entity = entity;
    }
}
