package net.lomeli.mt.tile.energy;

import net.lomeli.lomlib.util.RotationHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cofh.api.energy.IEnergyHandler;

import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

import universalelectricity.prefab.tile.TileEntityElectrical;

import net.lomeli.mt.api.tile.IMTCable;
import net.lomeli.mt.api.tile.IMTGenerator;
import net.lomeli.mt.core.helper.EnergyHelper;

import ic2.api.energy.tile.IEnergySource;

public class TileEntityCoalGen extends TileEntityElectrical implements IMTGenerator, IEnergySource, IPowerReceptor, IPowerEmitter, IEnergyHandler, IInventory {

    protected PowerHandler powerHandler;
    private ItemStack[] inventory;
    public int burnTime, currentBurnTime, tick;

    public TileEntityCoalGen() {
        init();
        inventory = new ItemStack[1];
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            this.sendEnergy();
            this.energyStored = powerHandler.getEnergyStored();

            if (burnTime > 0) {
                burnTime--;
                if (this.getAmountNeeded() > 0)
                    produceEnergy();
            }

            if (burnTime == 0 && this.getAmountNeeded() > 0) {
                burnTime = currentBurnTime = TileEntityFurnace.getItemBurnTime(inventory[0]);
                if (burnTime > 0) {
                    if (inventory[0] != null) {
                        --inventory[0].stackSize;
                        if (inventory[0].stackSize == 0)
                            inventory[0] = inventory[0].getItem().getContainerItemStack(inventory[0]);
                        onInventoryChanged();
                    }
                }
            }
        }
    }

    private void sendEnergy() {
        if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
            return;
        for (TileEntity tile : EnergyHelper.getAdjacentTiles(worldObj, xCoord, yCoord, zCoord)) {
            if (tile != null) {
                if (tile instanceof IPowerReceptor && !(tile instanceof IMTGenerator)) {
                    ForgeDirection dir = RotationHelper.getOrientationFromTile(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord), tile).getOpposite();
                    PowerReceiver power = ((IPowerReceptor) tile).getPowerReceiver(dir);
                    if (power != null) {
                        if (power.powerRequest() > 0 && this.useEnergy(2, true))
                            power.receiveEnergy(Type.ENGINE, powerHandler.useEnergy(1F, power.powerRequest(), true), dir);
                    }
                }else if(tile instanceof IMTCable){
                    if(((IMTCable)tile).getAmountNeeded() > 0)
                        ((IMTCable)tile).addEnergy(powerHandler.useEnergy(1F, ((IMTCable)tile).getEnergyCarry(), true), false);
                }
            }
        }
    }

    private void produceEnergy() {
        if (++tick >= 20) {
            this.addEnergy(1.21F, false);
            tick = 0;
        }
    }

    private void init() {
        powerHandler = new PowerHandler(this, Type.ENGINE);
        powerHandler.configurePowerPerdition(2, 100);
        powerHandler.configure(1, 200, 0.1F, 600);
    }

    @Override
    public float getMaxEnergy() {
        return powerHandler.getMaxEnergyStored();
    }

    @Override
    public float getEnergy() {
        return powerHandler.getEnergyStored();
    }

    @Override
    public float getAmountNeeded() {
        return (powerHandler.getMaxEnergyStored() - powerHandler.getEnergyStored());
    }

    @Override
    public boolean isActive() {
        return (!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && this.burnTime != 0);
    }

    @Override
    public void setMaxEnergy(float max) {
        powerHandler.configure(2, 200, 1, max);
    }

    @Override
    public void setEnergy(float energy) {
        powerHandler.setEnergy(energy);
    }

    @Override
    public float addEnergy(float energy, boolean simulated) {
        if (!simulated) {
            if (energy > 1 && this.getAmountNeeded() < 1)
                powerHandler.addEnergy(this.getAmountNeeded());
            else
                powerHandler.addEnergy(energy);
            return getEnergy();
        }
        return getEnergy() + energy;
    }

    @Override
    public boolean useEnergy(float energy, boolean simulated) {
        if (!simulated) {
            if (useEnergy(energy, true))
                powerHandler.useEnergy(energy - 1, energy, simulated);
        }
        return powerHandler.getEnergyStored() >= energy;
    }

    @Override
    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    @Override
    public float getProvide(ForgeDirection arg0) {
        return 16;
    }

    @Override
    public float getRequest(ForgeDirection arg0) {
        return 0;
    }

    @Override
    public float getMaxEnergyStored() {
        return powerHandler.getMaxEnergyStored();
    }

    @Override
    public boolean canEmitPowerFrom(ForgeDirection side) {
        return true;
    }

    @Override
    public PowerReceiver getPowerReceiver(ForgeDirection side) {
        return powerHandler.getPowerReceiver();
    }

    @Override
    public void doWork(PowerHandler workProvider) {
    }

    @Override
    public World getWorld() {
        return this.worldObj;
    }

    @Override
    public double getOfferedEnergy() {
        return 16;
    }

    @Override
    public void drawEnergy(double amount) {
        this.powerHandler.useEnergy(16, (float) amount, true);
    }

    @Override
    public void closeChest() {
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount)
                setInventorySlotContents(slot, null);
            else {
                itemStack.splitStack(amount);
                if (itemStack.stackSize == 0)
                    setInventorySlotContents(slot, null);
            }
        }
        return itemStack;
    }

    @Override
    public String getInvName() {
        return null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory[i];
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        if (getStackInSlot(slot) != null)
            return (itemstack.itemID == getStackInSlot(slot).itemID) && (itemstack.getItemDamage() == getStackInSlot(slot).getItemDamage());
        return TileEntityFurnace.getItemBurnTime(itemstack) > 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        inventory[slot] = itemstack;
        this.onInventoryChanged();
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        powerHandler.readFromNBT(data);
        this.currentBurnTime = data.getInteger("currentBurnTime");
        this.burnTime = data.getInteger("burnTime");
        this.tick = data.getInteger("energyTick");
        NBTTagList tagList = data.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < this.inventory.length)
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        writeNBT(data);
    }

    public void writeNBT(NBTTagCompound data) {
        powerHandler.writeToNBT(data);
        data.setInteger("burnTime", this.burnTime);
        data.setInteger("currentBurnTime", this.currentBurnTime);
        data.setInteger("energyTick", tick);
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
            if (inventory[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);

                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        data.setTag("Inventory", tagList);
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

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1) {
        if (this.currentBurnTime == 0)
            this.currentBurnTime = 800;
        return this.burnTime * par1 / this.currentBurnTime;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        this.useEnergy(maxExtract, simulate);
        return (int) this.getEnergy();
    }

    @Override
    public boolean canInterface(ForgeDirection from) {
        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return (int) this.getEnergy();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) this.getMaxEnergy();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getEnergyScaled(int i) {
        return ((this.getEnergy() / this.getMaxEnergy()) * i);
    }

    @Override
    public float drainEnergy(float energy, boolean simulated) {
        if (simulated)
            return this.getEnergy() >= energy ? energy : this.getEnergy();
        if (this.getEnergy() >= energy) {
            this.useEnergy(energy, false);
            return energy;
        } else {
            float energyLeft = this.getEnergy();
            this.setEnergy(0);
            return energyLeft;
        }
    }
}
