package com.craftle_mod.common.tile;

import com.craftle_mod.common.registries.CraftleTileEntityTypes;
import com.craftle_mod.common.util.NBTUtils;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public class TileEntityQuarry extends TileEntity
    implements ITickableTileEntity {

    private int x;
    private int y;
    private int z;
    private int tick;
    private boolean initialized;

    public TileEntityQuarry(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.initialized = false;
    }

    public TileEntityQuarry() {
        this(CraftleTileEntityTypes.QUARRY.get());
        this.initialized = false;
    }

    @Override
    public void tick() {
        if (!initialized) {
            init();
        }

        tick++;

        if (tick >= 40) {
            tick = 0;
            if (y > 2) {
                execute();
            }
        }
    }

    private void init() {
        initialized = true;
        x = this.pos.getX() - 1;
        y = this.pos.getY() - 1;
        z = this.pos.getZ() - 1;
        tick = 0;

    }

    private void execute() {
        int index = 0;
        //Block[] blocksRemoved = new Block[25];

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                BlockPos posToBreak =
                    new BlockPos(this.x + x, this.y, this.z + z);
                assert this.world != null;
                //blocksRemoved[index] =
                this.world.getBlockState(posToBreak).getBlock();
                boolean destroyed = destroyBlock(posToBreak);
                index++;
            }
        }
        this.y--;
    }

    private boolean destroyBlock(BlockPos pos) {

        assert world != null;
        BlockState blockstate = world.getBlockState(pos);

        if (blockstate.isAir(world, pos)) {
            return false;
        } else {
            IFluidState iFluidState = world.getFluidState(pos);
            world.playEvent(2001, pos, Block.getStateId(blockstate));

            // drop blocks
            TileEntity tileEntity = blockstate.hasTileEntity() ?
                world.getTileEntity(pos) : null;
            Block.spawnDrops(blockstate, world, this.pos.add(0, 1.5, 0),
                tileEntity, null, ItemStack.EMPTY);

            return world.setBlockState(pos, iFluidState.getBlockState(), 3);
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("initvalues", NBTUtils.toNBT(this));
        return super.write(compound);
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        CompoundNBT initvalues = compound.getCompound("initvalues");
        this.x = initvalues.getInt("x");
        this.y = initvalues.getInt("y");
        this.z = initvalues.getInt("z");
        this.tick = 0;
        initialized = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
