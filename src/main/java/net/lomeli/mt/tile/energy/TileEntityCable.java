package net.lomeli.mt.tile.energy;

import net.lomeli.lomlib.util.RotationHelper;

import net.lomeli.mt.api.tile.IMTBattery;
import net.lomeli.mt.api.tile.IMTGenerator;
import net.lomeli.mt.api.tile.IMTCable;
import net.lomeli.mt.api.tile.ITileEnergy;
import net.lomeli.mt.core.helper.EnergyHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import buildcraft.api.transport.IPipe;
import buildcraft.api.transport.IPipeTile;

import universalelectricity.prefab.tile.TileEntityElectrical;

import ic2.api.energy.tile.IEnergySink;

import cofh.api.energy.IEnergyHandler;

public class TileEntityCable extends TileEntity implements IMTCable, IPipeTile {
    private float energy;
    private boolean source, machine;

    public TileEntityCable() {
        source = machine = false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            for (TileEntity tile : EnergyHelper.getAdjacentTiles(worldObj, xCoord, yCoord, zCoord)) {
                if (tile != null) {
                    ForgeDirection dir = RotationHelper.getOrientationFromTile(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord), tile).getOpposite();
                    if (tile instanceof ITileEnergy && !(tile instanceof IMTGenerator)) {
                        if (tile instanceof IMTBattery) {
                            if (!((IMTBattery) tile).canDrawFromDirection(dir)) {
                                machine = true;
                                this.addJW(((IMTBattery) tile), getEnergyCarry());
                            }
                        } else if (tile instanceof IMTCable) {
                            if (((IMTCable) tile).source())
                                this.drainJW((ITileEnergy) tile, getEnergyCarry());
                            else {
                                if (((IMTCable) tile).machine()) {
                                    if (((IMTCable) tile).getEnergy() < ((((IMTCable) tile).getMaxEnergy() * 3) / 4))
                                        this.addJW((ITileEnergy) tile, getEnergyCarry());
                                    else
                                        this.drainJW(((IMTCable) tile), getEnergyCarry());
                                } else {
                                    if (((IMTCable) tile).getEnergy() < this.getEnergy())
                                        this.addJW(((IMTCable) tile), getEnergyCarry());
                                    else
                                        this.drainJW(((IMTCable) tile), getEnergyCarry());
                                }
                            }
                        } else {
                            if (((ITileEnergy) tile).getAmountNeeded() > 0) {
                                machine = true;
                                this.addJW((ITileEnergy) tile, getEnergyCarry());
                            }
                        }
                    }
                    if (tile instanceof IPowerReceptor && !(tile instanceof IMTGenerator)) {
                        machine = true;
                        PowerReceiver power = ((IPowerReceptor) tile).getPowerReceiver(dir);
                        if (power != null) {
                            if (power.powerRequest() > 0) {
                                float consumed = 0;
                                if (this.useEnergy(power.powerRequest(), true))
                                    consumed = power.powerRequest();
                                else
                                    consumed = this.getEnergy();
                                this.useEnergy(consumed, false);
                                power.receiveEnergy(Type.PIPE, consumed, dir);
                            }
                        }
                    }
                    if (this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) >= 2) {
                        if (tile instanceof IEnergySink) {
                            machine = true;
                            if (((IEnergySink) tile).demandedEnergyUnits() > 0) {
                                ((IEnergySink) tile).injectEnergyUnits(dir, getEnergyCarry() * 2);
                                this.useEnergy(getEnergyCarry(), false);
                            }
                        } else if (tile instanceof IEnergyHandler) {
                            machine = true;
                            if (((IEnergyHandler) tile).canInterface(dir)) {
                                if (((IEnergyHandler) tile).getEnergyStored(null) < ((IEnergyHandler) tile).getMaxEnergyStored(null)) {
                                    ((IEnergyHandler) tile).receiveEnergy(dir, (int) getEnergyCarry() * 2, false);
                                    this.useEnergy(getEnergyCarry(), false);
                                }
                            }
                        } else if (tile instanceof TileEntityElectrical) {
                            machine = true;
                            if (((TileEntityElectrical) tile).getEnergyStored() < ((TileEntityElectrical) tile).getMaxEnergyStored()) {
                                ((TileEntityElectrical) tile).receiveElectricity(getEnergyCarry() * 2, true);
                                this.useEnergy(getEnergyCarry(), false);
                            }
                        }
                    }
                }
            }
            /*
             * source = machine = false; for(TileEntity tile :
             * EnergyHelper.getAdjacentTiles(worldObj, xCoord, yCoord, zCoord)){
             * if(tile != null){ ForgeDirection dir =
             * RotationHelper.getOrientationFromTile
             * (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord),
             * tile).getOpposite(); if(tile instanceof ITileEnergy){ if(tile
             * instanceof IMTGenerator){ source = true;
             * this.drainJW((IMTGenerator)tile, getEnergyCarry()); }else if(tile
             * instanceof IMTBattery){
             * if(((IMTBattery)tile).canDrawFromDirection(dir)){ source = true;
             * (
             * (IMTBattery)tile).drawPower((IMTCable)worldObj.getBlockTileEntity
             * (xCoord, yCoord, zCoord), getEnergyCarry()); }else{
             * 
             * } }else{ if(tile instanceof IMTCable){
             * if(((IMTCable)tile).source()) this.drainJW((ITileEnergy)tile,
             * getEnergyCarry()); else{ if(((IMTCable)tile).machine()) {
             * if(((IMTCable)tile).getEnergy() <
             * ((((IMTCable)tile).getMaxEnergy() * 3) / 4))
             * this.addJW((ITileEnergy)tile, getEnergyCarry()); else
             * this.drainJW(((IMTCable)tile), getEnergyCarry()); } else{
             * if(((IMTCable)tile).getEnergy() < this.getEnergy())
             * this.addJW(((IMTCable)tile), getEnergyCarry()); else
             * this.drainJW(((IMTCable)tile), getEnergyCarry()); } } }else
             * if(((ITileEnergy)tile).getAmountNeeded() > 0){
             * this.addJW((ITileEnergy)tile, getEnergyCarry()); machine = true;
             * } } }else if(tile instanceof IPowerReceptor){ machine = true;
             * PowerReceiver power = ((IPowerReceptor)
             * tile).getPowerReceiver(dir); if (power != null) { if
             * (power.powerRequest() > 0 && this.useEnergy(getEnergyCarry(),
             * true)) { power.receiveEnergy(Type.ENGINE, getEnergyCarry(), dir);
             * this.useEnergy(getEnergyCarry(), false); } } }else{
             * if(this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) >= 2){
             * if(tile instanceof IEnergySink){ machine = true;
             * if(((IEnergySink)tile).demandedEnergyUnits() > 0){
             * ((IEnergySink)tile).injectEnergyUnits(dir, getEnergyCarry() * 2);
             * this.useEnergy(getEnergyCarry(), false); } }else if(tile
             * instanceof IEnergyHandler){ machine = true;
             * if(((IEnergyHandler)tile).canInterface(dir)){
             * if(((IEnergyHandler)tile).getEnergyStored(null) <
             * ((IEnergyHandler)tile).getMaxEnergyStored(null)){
             * ((IEnergyHandler)tile).receiveEnergy(dir, (int) getEnergyCarry()
             * * 2, false); this.useEnergy(getEnergyCarry(), false); } } }else
             * if(tile instanceof TileEntityElectrical){ machine = true;
             * if(((TileEntityElectrical)tile).getEnergyStored() <
             * ((TileEntityElectrical)tile).getMaxEnergyStored()){
             * ((TileEntityElectrical)tile).receiveElectricity(getEnergyCarry()
             * * 2, true); this.useEnergy(getEnergyCarry(), false); } } } } } }
             */
        }
    }

    @Override
    public boolean source() {
        return this.source;
    }

    public void drainJW(ITileEnergy generator, float energy) {
        float consumed = 0;
        if (generator.useEnergy(energy, true))
            consumed = energy;
        else
            consumed = generator.getEnergy();
        this.addEnergy(consumed, false);
        generator.useEnergy(consumed, false);
    }

    public void addJW(ITileEnergy machine, float energy) {
        float consumed = 0;
        if (this.useEnergy(energy, true))
            consumed = energy;
        else
            consumed = this.getEnergy();
        this.useEnergy(consumed, false);
        machine.addEnergy(consumed, false);
    }

    @Override
    public float getEnergyCarry() {
        return 2 * (1 + this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
    }

    @Override
    public float getMaxEnergy() {
        return (8 * (1 + this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord)));
    }

    @Override
    public float getEnergy() {
        return this.energy;
    }

    @Override
    public float getAmountNeeded() {
        return this.getMaxEnergy() - this.getEnergy();
    }

    @Override
    public boolean isActive() {
        return this.getEnergy() > (this.getMaxEnergy() / 2);
    }

    @Override
    public void setMaxEnergy(float max) {
    }

    @Override
    public void setEnergy(float energy) {
        this.energy = energy;
    }

    @Override
    public float addEnergy(float energy, boolean simulated) {
        if (!simulated && !worldObj.isRemote) {
            this.energy += energy;
            if (this.energy > this.getMaxEnergy())
                this.energy = this.getMaxEnergy();
            return this.energy;
        } else {
            float j = this.energy;
            return (j + energy);
        }
    }

    @Override
    public boolean useEnergy(float energy, boolean simulated) {
        if (!simulated && !worldObj.isRemote) {
            if (useEnergy(energy, true)) {
                this.energy -= energy;
                return true;
            } else
                return false;
        } else {
            return (this.energy > 0 && this.energy >= energy);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getEnergyScaled(int i) {
        return ((this.getEnergy() / this.getMaxEnergy()) * i);
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
    public boolean machine() {
        return machine;
    }

    @Override
    public boolean isSolidOnSide(ForgeDirection arg0) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return null;
    }

    @Override
    public IPipe getPipe() {
        return null;
    }

    @Override
    public PipeType getPipeType() {
        return IPipeTile.PipeType.POWER;
    }

    @Override
    public int injectItem(ItemStack stack, boolean doAdd, ForgeDirection from) {
        return 0;
    }

    @Override
    public boolean isPipeConnected(ForgeDirection with) {
        return true;
    }

}
