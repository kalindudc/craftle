package com.craftlemod.common.blockentity.factory;

import com.craftlemod.api.constant.FactoryConstants;
import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.blockentity.BlockEntityRecord;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;

public class FluidTankBlockEntity extends FactoryBlockEntity {

    public static final int MAX_TANK_LENGTH = 64;

    private int fluidCapacity;
    private int currentFluidAmount;
    private int fluidType;

    public FluidTankBlockEntity(BlockEntityRecord record) {
        super(record);
        this.fluidCapacity = 0;
        this.currentFluidAmount = 0;
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        FluidTankBlockEntity.this.getUsedVolume();
                        break;
                    default:
                        break;
                }
                return FluidTankBlockEntity.this.getUsedVolume();
            }

            @Override
            public void set(int index, int value) {
                //FluidTankBlockEntity.this.setUsedVolume(value);
            }

            @Override
            public int size() {
                return 1;
            }
        };
        this.fluidType = FactoryConstants.FLUID_TYPE_DEFAULT;
    }


    public int getCurrentFluidAmount() {
        return currentFluidAmount;
    }

    public void setCurrentFluidAmount(int currentFluidAmount) {
        this.currentFluidAmount = currentFluidAmount;
    }

    public int getFluidCapacity() {
        return fluidCapacity;
    }

    public void setFluidCapacity(int fluidCapacity) {
        this.fluidCapacity = fluidCapacity;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("fluid_capacity", this.fluidCapacity);
        nbt.putInt("current_fluid_amount", this.currentFluidAmount);
        nbt.putInt("fluid_type", this.fluidType);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.fluidCapacity = nbt.getInt("fluid_capacity");
        this.currentFluidAmount = nbt.getInt("current_fluid_amount");
        this.fluidType = nbt.getInt("fluid_type");
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        if (this.isFactoryActive()) {
            if (!this.verifyFactoryShape()) {
                this.deactivateFactory();
                return;
            }

            // do other tank related things
        } else {
            boolean validFactory = testFactoryShape(world, pos);
            // check for tank shape
            if (!validFactory) {
                return;
            }

            // activate tank
            this.activateFactory();
            //CraftleMod.LOGGER.error("\n\nshape: " + siloConfig.getLeft().toString() + "," + siloConfig.getRight().toString());
        }
    }

    @Override
    public boolean canUseItem(ItemStack stack) {
        return stack.getItem() instanceof BucketItem;
    }

    @Override
    public boolean useItem(World world, PlayerEntity player, Hand hand, ItemStack itemStack, boolean isIntake) {
        CraftleMod.LOGGER.error("used volume: " + getUsedVolume());
        CraftleMod.LOGGER.error("world client: " + world.isClient);

        if (!canUseItem(itemStack)) {
            return false;
        }

        BucketItem bucket = (BucketItem) itemStack.getItem();
        if (!FactoryConstants.BUCKET_TYPE_TO_FLUID_TYPE.containsKey(bucket.getTranslationKey())) {
            return false;
        }

        int fluidType = FactoryConstants.BUCKET_TYPE_TO_FLUID_TYPE.get(bucket.getTranslationKey());
        if (this.fluidType == FactoryConstants.FLUID_TYPE_DEFAULT && fluidType == FactoryConstants.FLUID_TYPE_DEFAULT) {
            return false;
        }

        if (isIntake) {
            if (this.fluidType == FactoryConstants.FLUID_TYPE_DEFAULT) {
                this.fluidType = fluidType;
            }

            // the current fluid type must be the same as the intake fluid type
            if (this.fluidType != fluidType) {
                return false;
            }

            // tank is full
            if (!this.insert()) {
                return false;
            }

            ItemStack emptied = BucketItem.getEmptiedStack(itemStack, player);
            player.setStackInHand(hand, emptied);
        } else {
            if (fluidType != FactoryConstants.FLUID_TYPE_DEFAULT) {
                return false;
            }

            if (!this.extract()) {
                return false;
            }

            ItemStack newStack = null;
            if (this.fluidType == FactoryConstants.FLUID_TYPE_LAVA) {
                newStack = new ItemStack(Items.LAVA_BUCKET);
            } else if (this.fluidType == FactoryConstants.FLUID_TYPE_WATER) {
                newStack = new ItemStack(Items.WATER_BUCKET);
            }

            if (newStack == null) {
                return false;
            }

            if (!player.getAbilities().creativeMode) {
                player.setStackInHand(hand, newStack);
            }

            if (this.getUsedVolume() == 0) {
                this.fluidType = FactoryConstants.FLUID_TYPE_DEFAULT;
            }
        }

        return true;
    }

    @Override
    public boolean testFactoryShape(World world, BlockPos pos) {
        BlockPos[] baseEdges = new BlockPos[2];
        int radius = 0;
        int height = 0;
        List<BlockPos> intakes = new ArrayList<>();
        List<BlockPos> exhausts = new ArrayList<>();

        // check the base
        for (int i = 1; i < MAX_TANK_LENGTH; i++) {
            BlockPos pos1 = new BlockPos(pos.getX() - i, pos.getY(), pos.getZ() + i);
            BlockPos pos2 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ() - i);
            int numBlocks = 2 * i + 1;

            Pair<Boolean, List<List<BlockPos>>> edgeItems = areValidEdges(world, pos1, pos2, numBlocks);
            if (edgeItems.getLeft()) {
                baseEdges[0] = pos1;
                baseEdges[1] = pos2;
                radius++;
                intakes.addAll(edgeItems.getRight().get(0));
                exhausts.addAll(edgeItems.getRight().get(1));
            } else {
                break;
            }
        }

        if (baseEdges[0] == null || baseEdges[1] == null) {
            this.setFactoryConfig(new FactoryConfig(height, radius, new Vec2f(0, 0), new Vec2f(0, 0), intakes, exhausts));
            this.setErrorString("Invalid factory base");
            return false;
        }

        Vec2f topLeftCords = new Vec2f(baseEdges[0].getX(), baseEdges[0].getZ());
        Vec2f bottomRightCords = new Vec2f(baseEdges[1].getX(), baseEdges[1].getZ());

        height++;
        // check the walls
        for (int i = 1; i < MAX_TANK_LENGTH * 2; i++) {
            BlockPos pos1 = new BlockPos(baseEdges[0].getX(), pos.getY() + i, baseEdges[0].getZ());
            BlockPos pos2 = new BlockPos(baseEdges[1].getX(), pos.getY() + i, baseEdges[1].getZ());
            int numBlocks = 2 * radius + 1;

            Pair<Boolean, List<List<BlockPos>>> edgeItems = areValidEdges(world, pos1, pos2, numBlocks);
            if (edgeItems.getLeft()) {
                height++;
                intakes.addAll(edgeItems.getRight().get(0));
                exhausts.addAll(edgeItems.getRight().get(1));
            } else {
                break;
            }
        }

        var factoryConfig = new FactoryConfig(height, radius, topLeftCords, bottomRightCords, intakes, exhausts);
        this.setFactoryConfig(factoryConfig);
        if (height < 3) {
            this.setErrorString("Invalid height, factory must be at least 3 blocks high");
            return false;
        }

        // check roof
        boolean validRoof = true;
        for (int i = 1; i < radius; i++) {
            BlockPos pos1 = new BlockPos(baseEdges[0].getX() + i, pos.getY() + (height - 1), baseEdges[0].getZ() - i);
            BlockPos pos2 = new BlockPos(baseEdges[1].getX() - i, pos.getY() + (height - 1), baseEdges[1].getZ() + i);
            int numBlocks = (2 * (radius - i)) + 1;

            Pair<Boolean, List<List<BlockPos>>> edgeItems = areValidEdges(world, pos1, pos2, numBlocks);
            if (!edgeItems.getLeft()) {
                validRoof = false;
                break;
            } else {
                intakes.addAll(edgeItems.getRight().get(0));
                exhausts.addAll(edgeItems.getRight().get(1));
            }
        }

        BlockPos roofCenterPos = new BlockPos(pos.getX(), pos.getY() + (height - 1), pos.getZ());
        // check roof center
        validRoof = validRoof && isValidMultiBlock(world.getBlockState(roofCenterPos).getBlock(), roofCenterPos);
        if (!validRoof) {
            this.setErrorString("Invalid factory roof");
            return false;
        } else {
            if (isValidIntake(world.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + height, pos.getZ())))) {
                intakes.add(new BlockPos(pos.getX(), pos.getY() + height, pos.getZ()));
            }
            if (isValidExhaust(world.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + height, pos.getZ())))) {
                exhausts.add(new BlockPos(pos.getX(), pos.getY() + height, pos.getZ()));
            }
        }

        if (!verifyFactoryInterior(topLeftCords, bottomRightCords, height)) {
            this.setErrorString("Factory interior is not empty");
            return false;
        }

        if (intakes.size() == 0) {
            this.setErrorString("Missing intake, factory must include at least one intake");
            return false;
        }

        if (exhausts.size() == 0) {
            this.setErrorString("Missing exhaust, factory must include at least one exhaust");
            return false;
        }

        return true;
    }

    public int getFluidType() {
        return fluidType;
    }

    public void setFluidType(int fluidType) {
        this.fluidType = fluidType;
    }
}
