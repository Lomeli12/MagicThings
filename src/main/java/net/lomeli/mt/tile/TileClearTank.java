package net.lomeli.mt.tile;

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

public class TileClearTank extends TileEntity implements IFluidHandler, IMTTile {

    public FluidTank tank;

    public TileClearTank() {
        tank = new FluidTank(32000);
    }

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
            pullLiquid();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void pullLiquid() {
        TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
        if (tile != null && tile instanceof TileClearTank) {
            TileClearTank clearTank = (TileClearTank) tile;
            if (clearTank.getTankInfo(null)[0] != null && clearTank.getTankInfo(null)[0].fluid != null) {
                if (clearTank.getTankInfo(null)[0].fluid.amount < 32000) {
                    clearTank.fill(null, tank.drain(tank.getFluidAmount(), true), true);
                }
            }
        }
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
        worldObj.markBlockForUpdate(xCoord, yCoord - 1, zCoord);
        worldObj.markBlockForRenderUpdate(xCoord, yCoord - 1, zCoord);
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
        TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
        return tank.getFluidAmount() < tank.getCapacity() ? tank.fill(resource, doFill) : (tile != null && tile instanceof TileClearTank)? ((TileClearTank)tile).fill(from, resource, doFill) : 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid()))
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
