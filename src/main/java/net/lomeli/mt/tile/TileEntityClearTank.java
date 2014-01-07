package net.lomeli.mt.tile;

import net.lomeli.lomlib.util.FluidUtil;

import net.lomeli.mt.api.tile.IMTTile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityClearTank extends TileEntity implements IFluidHandler, IMTTile {

    public FluidTank tank;

    public TileEntityClearTank() {
        tank = new FluidTank(32000);
    }

    private int tick;

    public void updateRender() {
        int id = worldObj.getBlockId(xCoord, yCoord, zCoord);
        if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == id) {
            if (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == id)
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
            else
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 3, 2);
        } else if (worldObj.getBlockId(xCoord, yCoord - 1, zCoord) == id) {
            if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == id)
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
            else
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 2);
        } else
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            updateRender();
            tick++;
            if (tick > 20) {
                if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == worldObj.getBlockId(xCoord, yCoord, zCoord))
                    this.pullLiquid();

                tick = 0;
            }
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void pullLiquid() {
        TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
        if (tile != null && tile instanceof IFluidHandler) {
            if (((IFluidHandler) tile).canDrain(ForgeDirection.DOWN, null)) {
                FluidStack fluid = ((IFluidHandler) tile).drain(ForgeDirection.DOWN, 1000, true);
                if (fluid != null) {
                    if (tank.getFluid() != null) {
                        if (tank.getFluidAmount() < tank.getCapacity() && fluid.isFluidEqual(tank.getFluid())) {
                            tank.fill(fluid, true);
                        } else {
                            ((IFluidHandler) tile).fill(ForgeDirection.UP, fluid, true);
                        }
                    } else {
                        tank.fill(fluid, true);
                    }
                }
            }
            if (tile instanceof TileEntityClearTank)
                ((TileEntityClearTank) tile).updateRender();
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForUpdate(xCoord, yCoord + 1, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord + 1, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        tank.readFromNBT(data);
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        writeNBT(data);
    }

    public void writeNBT(NBTTagCompound data) {
        tank.writeToNBT(data);
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
        if (resource != null) {
            if (tank.getFluid() != null) {
                if (FluidUtil.areFluidsEqual(tank.getFluid(), resource)) {
                    if (tank.getFluidAmount() < tank.getCapacity()) {
                        tank.fill(resource, true);
                        return resource.amount;
                    } else {
                        if (worldObj.getBlockId(xCoord, yCoord + 1, zCoord) == worldObj.getBlockId(xCoord, yCoord, zCoord)) {
                            TileEntityClearTank tile = (TileEntityClearTank) worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                            if (tile != null && tile.canFill(null, resource.getFluid())) {
                                tile.fill(null, resource, true);
                                return resource.amount;
                            }
                        }
                    }
                }
            } else {
                tank.fill(resource, true);
                return resource.amount;
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null)
            return null;
        if (!resource.isFluidEqual(tank.getFluid()))
            return null;
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo() };
    }

}
