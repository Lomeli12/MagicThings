package net.lomeli.mt.tile.energy;

import net.lomeli.lomlib.util.RotationHelper;

import net.lomeli.mt.api.tile.IMTBattery;
import net.lomeli.mt.api.tile.IMTCable;
import net.lomeli.mt.api.tile.IMTGenerator;
import net.lomeli.mt.api.tile.ITileEnergy;
import net.lomeli.mt.core.helper.EnergyHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

import ic2.api.tile.IWrenchable;

public class TileEntityBattery extends TileEntity implements IMTBattery, IWrenchable {

    private float energy, maxEnergy;

    public TileEntityBattery() {
        maxEnergy = 15000;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote)
            this.sendEnergy();
    }

    private void sendEnergy() {
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
            return;
        for (TileEntity tile : EnergyHelper.getAdjacentTiles(worldObj, xCoord, yCoord, zCoord)) {
            if (tile != null) {
                ForgeDirection dir = RotationHelper.getOrientationFromTile(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord), tile).getOpposite();
                if (tile instanceof IPowerReceptor && !(tile instanceof IMTGenerator)) {
                    PowerReceiver power = ((IPowerReceptor) tile).getPowerReceiver(dir);
                    if (power != null) {
                        if (power.powerRequest() > 0) {
                            float consumed = 0;
                            if (this.useEnergy(power.powerRequest(), true))
                                consumed = power.powerRequest();
                            else
                                consumed = this.getEnergy();
                            this.useEnergy(consumed, false);
                            power.receiveEnergy(Type.STORAGE, consumed, dir);
                        }
                    }
                } else if (tile instanceof IMTCable) {
                    if (!(dir.getOpposite().equals(ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord))))){
                        if (((IMTCable) tile).getAmountNeeded() > 0) {
                            float consumed = 0;
                            if (this.useEnergy(((IMTCable) tile).getEnergyCarry(), true))
                                consumed = ((IMTCable) tile).getEnergyCarry();
                            else
                                consumed = this.getEnergy();
                            this.useEnergy(consumed, false);
                            ((IMTCable) tile).addEnergy(consumed, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public float getMaxEnergy() {
        return maxEnergy;
    }

    @Override
    public float getEnergy() {
        return energy;
    }

    @Override
    public float getAmountNeeded() {
        return getMaxEnergy() - getEnergy();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void setMaxEnergy(float max) {
        maxEnergy = max;
    }

    @Override
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    @Override
    public float addEnergy(float energy, boolean simulated) {
        if (!simulated) {
            this.energy += energy;
            if (this.energy > this.maxEnergy)
                this.energy = this.maxEnergy;
            return this.energy;
        }
        return (getEnergy() + energy);
    }

    @Override
    public boolean useEnergy(float energy, boolean simulated) {
        if (!simulated) {
            if (useEnergy(energy, true)) {
                this.energy -= energy;
                if (this.energy < 0)
                    this.energy = 0;
                return true;
            }
        }
        return (getEnergy() >= energy);
    }

    @Override
    public float getEnergyScaled(int i) {
        return ((this.getEnergy() / this.getMaxEnergy()) * i);
    }

    @Override
    public boolean canDrawFromDirection(ForgeDirection direction) {
        int side = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        ForgeDirection validOutput = ForgeDirection.getOrientation(side);
        return (validOutput != null && !validOutput.equals(ForgeDirection.UNKNOWN) && direction != null && !direction.equals(ForgeDirection.UNKNOWN)) ? validOutput
                .equals(direction) : false;
    }

    @Override
    public void drawPower(ITileEnergy tile, float power) {
        float oldLevel = tile.getEnergy();
        float newLevel = tile.addEnergy(power, true);
        float finalOutPut = newLevel - oldLevel;
        if (this.useEnergy(finalOutPut, true)) {
            tile.addEnergy(finalOutPut, false);
            this.useEnergy(finalOutPut, false);
        }
    }

    @Override
    public void inputPower(ITileEnergy tile, float power) {
        float oldLevel = this.getEnergy();
        float newLevel = this.addEnergy(power, true);
        float output = newLevel - oldLevel;
        if (tile.useEnergy(output, true)) {
            tile.useEnergy(output, false);
            this.addEnergy(output, false);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        this.energy = data.getFloat("jiggawatts");
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        writeNBT(data);
    }

    public void writeNBT(NBTTagCompound data) {
        data.setFloat("jiggawatts", energy);
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
    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return side != worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    @Override
    public short getFacing() {
        return (short) worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    @Override
    public void setFacing(short facing) {
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, facing, 2);
    }

    @Override
    public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public float getWrenchDropRate() {
        return 1;
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        int id = worldObj.getBlockId(xCoord, yCoord, zCoord), meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        return new ItemStack(id, 1, meta);
    }
}
