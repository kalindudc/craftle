package com.craftlemod.common.blockentity;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.block.machine.MachineBlock;
import com.craftlemod.common.block.machine.MachineControllerBlock;
import com.craftlemod.common.blockentity.inventory.ICraftleInventory;
import com.craftlemod.common.screen.FactoryScreenHandler;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.AirBlock;
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

    /**
     * Given the postition of this entity, test the factory to shape to ensure it is a valid factory multiblock configuration
     */
    public Pair<Integer, Integer> testFactoryShape(World world, BlockPos pos) {
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

        if (this.height == 0) {
            this.topLeftCord = null;
            this.bottomRightCord = null;
            this.isFactoryActive = false;
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putBoolean("is_factory_active", isFactoryActive);
        nbt.putInt("factory_height", height);
        if (this.topLeftCord != null) {
            nbt.putFloat("top_left_x", topLeftCord.x);
            nbt.putFloat("top_left_z", topLeftCord.y);
        }
        if (this.bottomRightCord != null) {
            nbt.putFloat("bottom_right_x", bottomRightCord.x);
            nbt.putFloat("bottom_right_z", bottomRightCord.y);
        }
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
        int radius = factoryConfig.getLeft();
        this.topLeftCord = new Vec2f(getPos().getX() - radius, getPos().getZ() + radius);
        this.bottomRightCord = new Vec2f(getPos().getX() + radius, getPos().getZ() - radius);
        setFactoryController(factoryConfig, true);
    }

    public void deactivateFactory() {
        this.isFactoryActive = false;
        setFactoryController(new Pair<>((int) (getPos().getX() - this.topLeftCord.x), this.height), false);
        this.height = 0;
        this.topLeftCord = null;
        this.bottomRightCord = null;
    }

    public boolean verifyFactoryShape() {
        assert world != null;
        // bottom layer and top layer
        for (int x = (int) topLeftCord.x; x <= bottomRightCord.x; x++) {
            for (int z = (int) topLeftCord.y; z >= bottomRightCord.y; z--) {
                // bottom layer and top layer
                BlockPos pos1 = new BlockPos(x, getPos().getY(), z);
                BlockPos pos2 = new BlockPos(x, getPos().getY() + (height - 1), z);
                if (!isValidMultiBlock(world.getBlockState(pos1).getBlock()) || !isValidMultiBlock(world.getBlockState(pos2).getBlock())) {
                    return false;
                }
            }
        }

        // walls, (height - 2) because top and bottom layers are done above
        for (int y = getPos().getY() + 1; y < getPos().getY() + (height - 1); y++) {
            for (int x = (int) topLeftCord.x; x <= bottomRightCord.x; x++) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos(x, y, (int) topLeftCord.y);
                BlockPos pos2 = new BlockPos(x, y, (int) bottomRightCord.y);
                if (!isValidMultiBlock(world.getBlockState(pos1).getBlock()) || !isValidMultiBlock(world.getBlockState(pos2).getBlock())) {
                    return false;
                }
            }

            for (int z = (int) topLeftCord.y; z >= bottomRightCord.y; z--) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos((int) topLeftCord.x, y, z);
                BlockPos pos2 = new BlockPos((int) bottomRightCord.x, y, z);
                if (!isValidMultiBlock(world.getBlockState(pos1).getBlock()) || !isValidMultiBlock(world.getBlockState(pos2).getBlock())) {
                    return false;
                }
            }
        }

        return verifyFactoryInterior(this.topLeftCord, this.bottomRightCord, this.height);
    }

    public boolean verifyFactoryInterior(Vec2f topLeftCord, Vec2f bottomRightCord, int height) {
        assert world != null;
        if (topLeftCord == null || bottomRightCord == null) {
            return false;
        }
        CraftleMod.LOGGER.error(topLeftCord.x + "," + topLeftCord.y + " | " + bottomRightCord.x + "," + bottomRightCord.y);

        for (int x = (int) topLeftCord.x + 1; x <= bottomRightCord.x - 1; x++) {
            for (int z = (int) topLeftCord.y - 1; z >= bottomRightCord.y + 1; z--) {
                for (int y = getPos().getY() + 1; y < getPos().getY() + (height - 2); y++) {
                    if (!(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof AirBlock)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void setFactoryController(Pair<Integer, Integer> factoryConfig, boolean activateFactory) {
        // bottom layer and top layer
        for (int x = (int) topLeftCord.x; x <= bottomRightCord.x; x++) {
            for (int z = (int) topLeftCord.y; z >= bottomRightCord.y; z--) {
                // bottom layer and top layer
                BlockPos pos1 = new BlockPos(x, getPos().getY(), z);
                BlockPos pos2 = new BlockPos(x, getPos().getY() + (height - 1), z);
                setController(pos1, activateFactory);
                setController(pos2, activateFactory);
            }
        }

        // walls, (height - 2) because top and bottom layers are done above
        for (int y = getPos().getY() + 1; y < getPos().getY() + (height - 1); y++) {
            for (int x = (int) topLeftCord.x; x <= bottomRightCord.x; x++) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos(x, y, (int) topLeftCord.y);
                BlockPos pos2 = new BlockPos(x, y, (int) bottomRightCord.y);
                setController(pos1, activateFactory);
                setController(pos2, activateFactory);
            }

            for (int z = (int) topLeftCord.y; z >= bottomRightCord.y; z--) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos((int) topLeftCord.x, y, z);
                BlockPos pos2 = new BlockPos((int) bottomRightCord.x, y, z);
                setController(pos1, activateFactory);
                setController(pos2, activateFactory);
            }
        }
    }

    private void setController(BlockPos pos, boolean activateFactory) {
        BlockPos controller = this.pos;

        assert world != null;
        if (world.getBlockState(pos).getBlock() instanceof MachineControllerBlock) {
            return;
        }

        if (world.getBlockEntity(pos) instanceof CraftleBlockEntity entity) {

            if (activateFactory) {
                entity.activateBlock(world, world.getBlockState(pos), controller, new Vec3f[]{new Vec3f(this.topLeftCord.x, this.getPos().getY(), this.topLeftCord.y),
                    new Vec3f(this.bottomRightCord.x, this.getPos().getY() + height - 1, this.bottomRightCord.y)});
            } else {
                entity.deactivateBlock(world, world.getBlockState(pos));
            }
        }
    }

    public boolean isFactoryActive() {
        return isFactoryActive;
    }

    public void setFactoryActive(boolean factoryActive) {
        isFactoryActive = factoryActive;
    }
}
