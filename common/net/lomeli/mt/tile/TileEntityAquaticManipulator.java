package net.lomeli.mt.tile;

import net.lomeli.lomlib.fluid.FluidUtil;

import net.lomeli.mt.api.tile.IMTTile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityAquaticManipulator extends TileEntity implements IFluidHandler, IMTTile {

    private FluidTank water;
    private int rate;

    public TileEntityAquaticManipulator() {
        water = new FluidTank(40000);
    }

    protected int tick;

    @Override
    public void updateEntity() {
        if(!this.worldObj.isRemote) {
            tick++;
            if(tick > 8) {
                if(hasTwoSources()) {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
                    if(requiresMoreWater() && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
                        water.fill(new FluidStack(FluidRegistry.WATER, (1000 * rate)), true);
                    }
                }else
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
                outputWater();
                tick = 0;

                worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
                worldObj.markBlockForRenderUpdate(xCoord, yCoord + 1, zCoord);
            }
        }
    }

    public boolean hasWater() {
        return water.getFluidAmount() > 0;
    }

    public boolean requiresMoreWater() {
        return ((water.getCapacity() - water.getFluidAmount()) != 0);
    }

    public void outputWater() {
        TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
        if(tile != null) {
            if((tile instanceof IFluidHandler) && hasWater()) {
                if(((IFluidHandler) tile).canFill(ForgeDirection.DOWN, water.getFluid().getFluid())) {
                    if(water.getFluidAmount() > 1000) {
                        ((IFluidHandler) tile).fill(ForgeDirection.DOWN, new FluidStack(water.getFluid(), 1000), true);
                        water.drain(1000, true);
                    }else {
                        ((IFluidHandler) tile).fill(ForgeDirection.DOWN,
                                new FluidStack(water.getFluid(), water.getFluidAmount()), true);
                        water.drain(water.getFluidAmount(), true);
                    }
                    worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
                    worldObj.markBlockForRenderUpdate(xCoord, yCoord + 1, zCoord);
                }
            }
        }
    }

    public boolean hasTwoSources() {
        int i = 0;
        if(this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) == Block.waterStill.blockID
                && this.worldObj.getBlockId(this.xCoord + 1, this.yCoord, this.zCoord) != Block.waterMoving.blockID)
            i++;
        if(this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) == Block.waterStill.blockID
                && this.worldObj.getBlockId(this.xCoord - 1, this.yCoord, this.zCoord) != Block.waterMoving.blockID)
            i++;
        if(this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) == Block.waterStill.blockID
                && this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord + 1) != Block.waterMoving.blockID)
            i++;
        if(this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) == Block.waterStill.blockID
                && this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord - 1) != Block.waterMoving.blockID)
            i++;

        rate = i / 2;
        return i > 1;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        FluidUtil.readFluidTankNBT(data, water, FluidRegistry.WATER);
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        writeNBT(data);
    }

    public void writeNBT(NBTTagCompound data) {

        FluidUtil.writeFluidTankNBT(data, water);
    }

    @Override
    public Packet getDescriptionPacket() {
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound tag = packet != null ? packet.data : new NBTTagCompound();
        writeNBT(tag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
        super.onDataPacket(networkManager, packet);
        NBTTagCompound nbtTag = packet.data;
        readNBT(nbtTag);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return resource.getFluid().equals(FluidRegistry.WATER) ? drain(ForgeDirection.UP, resource.amount, true) : null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack output = null;

        if(from == ForgeDirection.UP && water.getFluidAmount() > 0) {
            int outAmount;
            if(water.getFluidAmount() >= maxDrain) {
                water.drain(maxDrain, true);
                outAmount = maxDrain;
            }else {
                outAmount = water.getFluidAmount();
                water.drain(water.getFluidAmount(), true);
            }
            output = new FluidStack(water.getFluid().getFluid(), outAmount);
        }

        return output;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { water.getInfo() };
    }

}
