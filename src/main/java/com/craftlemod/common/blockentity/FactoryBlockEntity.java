package com.craftlemod.common.blockentity;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.block.machine.MachineBlock;
import com.craftlemod.common.block.machine.MachineControllerBlock;
import com.craftlemod.common.blockentity.inventory.ICraftleInventory;
import com.craftlemod.common.screen.FactoryScreenHandler;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FactoryBlockEntity extends CraftleBlockEntity implements ExtendedScreenHandlerFactory, ICraftleInventory {

    private final DefaultedList<ItemStack> inventory;
    private List<FactoryIOBlockEntity> factoryIOs;
    private Vec2f topLeftCord;
    private Vec2f bottomRightCord;
    private int height;
    private boolean isFactoryActive;

    public FactoryBlockEntity(BlockEntityRecord record) {
        super(record);
        this.factoryIOs = new ArrayList<>();
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        this.topLeftCord = new Vec2f(this.getPos().getX(), this.getPos().getZ());
        this.bottomRightCord = new Vec2f(this.getPos().getX(), this.getPos().getZ());
        this.height = 0;
        this.isFactoryActive = false;
    }

    /**
     * Check if the given block is valid for this factory multiblock
     */
    public boolean isValidMultiBlock(Block block) {
        return block instanceof MachineBlock;
    }

    public boolean isValidFactoryIO(BlockEntity entity) {
        return entity instanceof FactoryIOBlockEntity;
    }

    public Pair<Boolean, List<FactoryIOBlockEntity>> areValidEdges(World world, BlockPos pos1, BlockPos pos2, int numBlocks) {
        List<FactoryIOBlockEntity> factoryIOs = new ArrayList<>();

        boolean top = isValidRow(world, pos1, new Vec3f(1, 0, 0), numBlocks, factoryIOs);
        boolean left = isValidRow(world, pos1, new Vec3f(0, 0, -1), numBlocks, factoryIOs);
        boolean bottom = isValidRow(world, pos2, new Vec3f(-1, 0, 0), numBlocks, factoryIOs);
        boolean right = isValidRow(world, pos2, new Vec3f(0, 0, 1), numBlocks, factoryIOs);

        return new Pair<>(top && left && bottom && right, factoryIOs);
    }

    public boolean isValidRow(World world, BlockPos pos, Vec3f dir, int numBlocks, List<FactoryIOBlockEntity> factoryIOs) {
        for (int i = 0; i < numBlocks; i++) {
            int x = pos.getX() + (i * (int) dir.getX());
            int y = pos.getY() + (i * (int) dir.getY());
            int z = pos.getZ() + (i * (int) dir.getZ());

            if (!isValidMultiBlock(world.getBlockState(new BlockPos(x, y, z)).getBlock())) {
                return false;
            } else {
                if (isValidFactoryIO(world.getBlockEntity(new BlockPos(x, y, z)))) {
                    factoryIOs.add((FactoryIOBlockEntity) world.getBlockEntity(new BlockPos(x, y, z)));
                }
            }
        }
        return true;
    }

    public Pair<Integer, Integer> testShape(World world, BlockPos pos) {
        return null;
    }

    public List<FactoryIOBlockEntity> getFactoryIOs() {
        return factoryIOs;
    }

    public void setFactoryIOs(List<FactoryIOBlockEntity> factoryIOs) {
        this.factoryIOs = factoryIOs;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FactoryScreenHandler(syncId, inv, this);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        this.topLeftCord = new Vec2f(nbt.getFloat("top_left_x"), nbt.getFloat("top_left_z"));
        this.bottomRightCord = new Vec2f(nbt.getFloat("bottom_right_x"), nbt.getFloat("bottom_right_z"));
        this.isFactoryActive = nbt.getBoolean("is_factory_active");
        this.height = nbt.getInt("factory_height");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putBoolean("is_factory_active", isFactoryActive);
        nbt.putInt("factory_height", height);
        nbt.putFloat("top_left_x", topLeftCord.x);
        nbt.putFloat("top_left_z", topLeftCord.y);
        nbt.putFloat("bottom_right_x", bottomRightCord.x);
        nbt.putFloat("bottom_right_z", bottomRightCord.y);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public Vec2f getTopLeftCord() {
        return topLeftCord;
    }

    public void setTopLeftCord(Vec2f topLeftCord) {
        this.topLeftCord = topLeftCord;
    }

    public Vec2f getBottomRightCord() {
        return bottomRightCord;
    }

    public void setBottomRightCord(Vec2f bottomRightCord) {
        this.bottomRightCord = bottomRightCord;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void activateFactory(Pair<Integer, Integer> factoryConfig) {
        this.isFactoryActive = true;
        this.height = factoryConfig.getRight();
        setFactoryController(factoryConfig, this);
    }

    public void deactivateFactory() {
        this.isFactoryActive = false;
        setFactoryController(new Pair<>((int) (getPos().getX() - this.topLeftCord.x), this.height), null);
        this.height = 0;
    }


    public void setFactoryController(Pair<Integer, Integer> factoryConfig, FactoryBlockEntity entity) {
        int radius = factoryConfig.getLeft();
        this.topLeftCord = new Vec2f(getPos().getX() - radius, getPos().getZ() + radius);
        this.bottomRightCord = new Vec2f(getPos().getX() + radius, getPos().getZ() - radius);

        // bottom layer and top layer
        for (int x = (int) topLeftCord.x; x <= bottomRightCord.x; x++) {
            for (int z = (int) topLeftCord.y; z >= bottomRightCord.y; z--) {
                CraftleMod.LOGGER.error("inf 1");
                BlockPos pos1 = new BlockPos(x, getPos().getY(), z);
                BlockPos pos2 = new BlockPos(x, getPos().getY() + (height - 1), z);
                setController(pos1, entity);
                setController(pos2, entity);
            }
        }

        // walls, (height - 2) because top and bottom layers are done above
        for (int y = getPos().getY() + 1; y < getPos().getY() + (height - 2); y++) {
            for (int x = (int) topLeftCord.x; x <= bottomRightCord.x; x++) {
                CraftleMod.LOGGER.error("inf 2");
                BlockPos pos1 = new BlockPos(x, getPos().getY(), (int) topLeftCord.y);
                BlockPos pos2 = new BlockPos(x, getPos().getY(), (int) bottomRightCord.y);
                setController(pos1, entity);
                setController(pos2, entity);
            }

            for (int z = (int) topLeftCord.y; z >= bottomRightCord.y; z--) {
                CraftleMod.LOGGER.error("inf 3");
                BlockPos pos1 = new BlockPos((int) topLeftCord.x, getPos().getY(), z);
                BlockPos pos2 = new BlockPos((int) bottomRightCord.x, getPos().getY(), z);
                setController(pos1, entity);
                setController(pos2, entity);
            }
        }
    }

    private void setController(BlockPos pos, FactoryBlockEntity entity) {
        assert world != null;
        if (world.getBlockState(pos).getBlock() instanceof MachineControllerBlock) {
            return;
        }

        if (world.getBlockState(pos).getBlock() instanceof MachineBlock block) {
            block.setController(entity);
        }
    }

    public boolean isFactoryActive() {
        return isFactoryActive;
    }

    public void setFactoryActive(boolean factoryActive) {
        isFactoryActive = factoryActive;
    }
}
