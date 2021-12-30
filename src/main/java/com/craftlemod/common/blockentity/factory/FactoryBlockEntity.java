package com.craftlemod.common.blockentity.factory;

import com.craftlemod.common.block.machine.MachineBlock;
import com.craftlemod.common.block.machine.MachineControllerBlock;
import com.craftlemod.common.blockentity.BlockEntityRecord;
import com.craftlemod.common.blockentity.CraftleBlockEntity;
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
    private boolean isFactoryActive;
    private FactoryConfig factoryConfig;
    private String errorString;

    public FactoryBlockEntity(BlockEntityRecord record) {
        super(record);
        inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
        this.isFactoryActive = false;
        this.errorString = "";
    }

    /**
     * Check if the given block is valid for this factory multiblock
     */
    public boolean isValidMultiBlock(Block block, BlockPos pos) {
        if (block instanceof MachineBlock) {
            setController(pos, true, false);
            return true;
        }
        return false;
    }

    public boolean isValidIntake(BlockEntity entity) {
        return entity instanceof FactoryIOBlockEntity && ((FactoryIOBlockEntity) entity).isIntake();
    }

    public boolean isValidExhaust(BlockEntity entity) {
        return entity instanceof FactoryIOBlockEntity && !((FactoryIOBlockEntity) entity).isIntake();
    }

    public Pair<Boolean, List<List<BlockPos>>> areValidEdges(World world, BlockPos pos1, BlockPos pos2, int numBlocks) {
        List<List<BlockPos>> factoryIOs = new ArrayList<>();
        factoryIOs.add(new ArrayList<>());
        factoryIOs.add(new ArrayList<>());

        boolean top = isValidRow(world, pos1, new Vec3f(1, 0, 0), numBlocks, factoryIOs);
        boolean left = isValidRow(world, pos1, new Vec3f(0, 0, -1), numBlocks, factoryIOs);
        boolean bottom = isValidRow(world, pos2, new Vec3f(-1, 0, 0), numBlocks, factoryIOs);
        boolean right = isValidRow(world, pos2, new Vec3f(0, 0, 1), numBlocks, factoryIOs);

        return new Pair<>(top && left && bottom && right, factoryIOs);
    }

    public boolean isValidRow(World world, BlockPos pos, Vec3f dir, int numBlocks, List<List<BlockPos>> factoryIOs) {
        for (int i = 0; i < numBlocks; i++) {
            int x = pos.getX() + (i * (int) dir.getX());
            int y = pos.getY() + (i * (int) dir.getY());
            int z = pos.getZ() + (i * (int) dir.getZ());
            BlockPos blockPos = new BlockPos(x, y, z);

            if (!isValidMultiBlock(world.getBlockState(blockPos).getBlock(), blockPos)) {
                return false;
            } else {
                BlockPos factoryIOPos = new BlockPos(x, y, z);
                if (isValidIntake(world.getBlockEntity(factoryIOPos))) {
                    factoryIOs.get(0).add(factoryIOPos);
                }
                if (isValidExhaust(world.getBlockEntity(factoryIOPos))) {
                    factoryIOs.get(1).add(factoryIOPos);
                }
            }
        }
        return true;
    }

    /**
     * Given the postition of this entity, test the factory to shape to ensure it is a valid factory multiblock configuration
     */
    public boolean testFactoryShape(World world, BlockPos pos) {
        return false;
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

    public void readFactoryFromNbt(NbtCompound nbt) {
        this.isFactoryActive = nbt.getBoolean("is_factory_active");
        if (!this.isFactoryActive) {
            this.factoryConfig = null;
            return;
        }

        Vec2f topLeftCords = new Vec2f(nbt.getInt("top_left_x"), nbt.getInt("top_left_z"));
        Vec2f bottomRightCords = new Vec2f(nbt.getInt("bottom_right_x"), nbt.getInt("bottom_right_z"));
        int height = nbt.getInt("factory_height");
        int radius = nbt.getInt("factory_radius");
        int numIntakes = nbt.getInt("number_of_intakes");
        int numExhausts = nbt.getInt("number_of_exhausts");

        List<BlockPos> intakes = new ArrayList<>();
        List<BlockPos> exhausts = new ArrayList<>();

        for (int i = 0; i < numIntakes; i++) {
            intakes.add(new BlockPos(nbt.getInt("intake_" + i + "_x"), nbt.getInt("intake_" + i + "_y"), nbt.getInt("intake_" + i + "_z")));
        }
        for (int i = 0; i < numExhausts; i++) {
            intakes.add(new BlockPos(nbt.getInt("exhaust_" + i + "_x"), nbt.getInt("exhaust_" + i + "_y"), nbt.getInt("exhaust_" + i + "_z")));
        }
        this.factoryConfig = new FactoryConfig(height, radius, topLeftCords, bottomRightCords, intakes, exhausts);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, this.inventory);
        readFactoryFromNbt(nbt);
    }

    private void writeFactoryToNbt(NbtCompound nbt) {
        nbt.putBoolean("is_factory_active", isFactoryActive);

        if (!this.isFactoryActive) {
            return;
        }

        nbt.putInt("factory_height", this.factoryConfig.height());
        nbt.putInt("factory_radius", this.factoryConfig.radius());
        nbt.putInt("number_of_intakes", this.factoryConfig.intakes().size());
        nbt.putInt("number_of_exhausts", this.factoryConfig.exhausts().size());
        nbt.putFloat("top_left_x", this.factoryConfig.topLeftCords().x);
        nbt.putFloat("top_left_z", this.factoryConfig.topLeftCords().y);
        nbt.putFloat("bottom_right_x", this.factoryConfig.bottomRightCords().x);
        nbt.putFloat("bottom_right_z", this.factoryConfig.bottomRightCords().y);

        for (int i = 0; i < this.factoryConfig.intakes().size(); i++) {
            nbt.putInt("intake_" + i + "_x", this.factoryConfig.intakes().get(i).getX());
            nbt.putInt("intake_" + i + "_y", this.factoryConfig.intakes().get(i).getY());
            nbt.putInt("intake_" + i + "_z", this.factoryConfig.intakes().get(i).getZ());
        }
        for (int i = 0; i < this.factoryConfig.exhausts().size(); i++) {
            nbt.putInt("exhaust_" + i + "_x", this.factoryConfig.exhausts().get(i).getX());
            nbt.putInt("exhaust_" + i + "_y", this.factoryConfig.exhausts().get(i).getY());
            nbt.putInt("exhaust_" + i + "_z", this.factoryConfig.exhausts().get(i).getZ());
        }
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        writeFactoryToNbt(nbt);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
        NbtCompound nbt = new NbtCompound();
        this.writeFactoryToNbt(nbt);
        buf.writeNbt(nbt);
    }

    public void activateFactory() {
        this.isFactoryActive = true;
        this.errorString = "";
        setFactoryController(factoryConfig, true);
    }

    public void deactivateFactory() {
        this.isFactoryActive = false;
        setFactoryController(this.factoryConfig, false);
        this.factoryConfig = null;
    }

    public boolean verifyFactoryShape() {
        assert world != null;
        // bottom layer and top layer
        for (int x = (int) factoryConfig.topLeftCords().x; x <= factoryConfig.bottomRightCords().x; x++) {
            for (int z = (int) factoryConfig.topLeftCords().y; z >= factoryConfig.bottomRightCords().y; z--) {
                // bottom layer and top layer
                BlockPos pos1 = new BlockPos(x, getPos().getY(), z);
                BlockPos pos2 = new BlockPos(x, getPos().getY() + (factoryConfig.height() - 1), z);
                if (!isValidMultiBlock(world.getBlockState(pos1).getBlock(), pos1) || !isValidMultiBlock(world.getBlockState(pos2).getBlock(), pos2)) {
                    return false;
                }
            }
        }

        // walls, (height - 2) because top and bottom layers are done above
        for (int y = getPos().getY() + 1; y < getPos().getY() + (factoryConfig.height() - 1); y++) {
            for (int x = (int) factoryConfig.topLeftCords().x; x <= factoryConfig.bottomRightCords().x; x++) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos(x, y, (int) factoryConfig.topLeftCords().y);
                BlockPos pos2 = new BlockPos(x, y, (int) factoryConfig.bottomRightCords().y);
                if (!isValidMultiBlock(world.getBlockState(pos1).getBlock(), pos1) || !isValidMultiBlock(world.getBlockState(pos2).getBlock(), pos2)) {
                    return false;
                }
            }

            for (int z = (int) factoryConfig.topLeftCords().y; z >= factoryConfig.bottomRightCords().y; z--) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos((int) factoryConfig.topLeftCords().x, y, z);
                BlockPos pos2 = new BlockPos((int) factoryConfig.bottomRightCords().x, y, z);
                if (!isValidMultiBlock(world.getBlockState(pos1).getBlock(), pos1) || !isValidMultiBlock(world.getBlockState(pos2).getBlock(), pos2)) {
                    return false;
                }
            }
        }

        return verifyFactoryInterior(this.factoryConfig.topLeftCords(), this.factoryConfig.bottomRightCords(), this.factoryConfig.height());
    }

    public boolean verifyFactoryInterior(Vec2f topLeftCord, Vec2f bottomRightCord, int height) {
        assert world != null;
        if (topLeftCord == null || bottomRightCord == null) {
            return false;
        }

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

    public void setFactoryController(FactoryConfig factoryConfig, boolean activateFactory) {
        // bottom layer and top layer
        for (int x = (int) factoryConfig.topLeftCords().x; x <= factoryConfig.bottomRightCords().x; x++) {
            for (int z = (int) factoryConfig.topLeftCords().y; z >= factoryConfig.bottomRightCords().y; z--) {
                // bottom layer and top layer
                BlockPos pos1 = new BlockPos(x, getPos().getY(), z);
                BlockPos pos2 = new BlockPos(x, getPos().getY() + (factoryConfig.height() - 1), z);
                setController(pos1, activateFactory, activateFactory);
                setController(pos2, activateFactory, activateFactory);
            }
        }

        // walls, (height - 2) because top and bottom layers are done above
        for (int y = getPos().getY() + 1; y < getPos().getY() + (factoryConfig.height() - 1); y++) {
            for (int x = (int) factoryConfig.topLeftCords().x; x <= factoryConfig.bottomRightCords().x; x++) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos(x, y, (int) factoryConfig.topLeftCords().y);
                BlockPos pos2 = new BlockPos(x, y, (int) factoryConfig.bottomRightCords().y);
                setController(pos1, activateFactory, activateFactory);
                setController(pos2, activateFactory, activateFactory);
            }

            for (int z = (int) factoryConfig.topLeftCords().y; z >= factoryConfig.bottomRightCords().y; z--) {
                // blocks of opposing walls
                BlockPos pos1 = new BlockPos((int) factoryConfig.topLeftCords().x, y, z);
                BlockPos pos2 = new BlockPos((int) factoryConfig.bottomRightCords().x, y, z);
                setController(pos1, activateFactory, activateFactory);
                setController(pos2, activateFactory, activateFactory);
            }
        }
    }

    public void setController(BlockPos pos, boolean activateFactory, boolean activateBlock) {
        BlockPos controller = this.pos;

        assert world != null;
        if (world.getBlockState(pos).getBlock() instanceof MachineControllerBlock) {
            return;
        }

        if (world.getBlockEntity(pos) instanceof CraftleBlockEntity entity) {

            if (activateFactory) {
                Vec3f[] bounds = null;
                if (activateBlock) {
                    bounds = new Vec3f[]{new Vec3f(this.factoryConfig.topLeftCords().x, this.getPos().getY(), this.factoryConfig.topLeftCords().y),
                        new Vec3f(this.factoryConfig.bottomRightCords().x, this.getPos().getY() + factoryConfig.height() - 1, this.factoryConfig.bottomRightCords().y)};
                }
                entity.activateBlock(world, world.getBlockState(pos), controller, bounds, activateBlock);
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

    public FactoryConfig getFactoryConfig() {
        return factoryConfig;
    }

    public void setFactoryConfig(FactoryConfig factoryConfig) {
        this.factoryConfig = factoryConfig;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }
}
