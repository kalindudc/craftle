package com.craftlemod.common.block.machine;

import com.craftlemod.common.blockentity.CraftleBlockEntity;
import com.craftlemod.common.blockentity.factory.FactoryBlockEntity;
import com.craftlemod.common.shared.IHasModelPath;
import java.util.function.BiFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MachineBlock extends BlockWithEntity implements IHasModelPath {

    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    private final BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor;
    private final Identifier id;

    private String modelPath;
    private BlockEntityType<BlockEntity> entityType;

    public MachineBlock(Identifier id, String modelPath, Settings settings, BiFunction<BlockPos, BlockState, BlockEntity> blockEntityConstructor) {
        super(settings);
        this.id = id;
        this.modelPath = modelPath;
        this.blockEntityConstructor = blockEntityConstructor;
        this.entityType = null;
        setDefaultState(getStateManager().getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return blockEntityConstructor.apply(pos, state);
    }

    public BlockEntityType<BlockEntity> getEntityType() {
        return entityType;
    }

    public void setEntityType(BlockEntityType<BlockEntity> entityType) {
        this.entityType = entityType;
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

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            if (!(world.getBlockEntity(pos) instanceof CraftleBlockEntity entity)) {
                return ActionResult.PASS;
            }

            BlockPos controllerPos = pos;
            if (!(this instanceof MachineControllerBlock) && entity.getEntityControllerPos() == null) {
                return ActionResult.PASS;
            }

            if (entity.getEntityControllerPos() != null) {
                if (!(world.getBlockEntity(entity.getEntityControllerPos()) instanceof FactoryBlockEntity)) {
                    return ActionResult.PASS;
                }
                controllerPos = entity.getEntityControllerPos();
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
            if (blockEntity instanceof FactoryBlockEntity) {
                ItemScatterer.spawn(world, pos, (FactoryBlockEntity) blockEntity);
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

    public BiFunction<BlockPos, BlockState, BlockEntity> getBlockEntityConstructor() {
        return blockEntityConstructor;
    }
}
