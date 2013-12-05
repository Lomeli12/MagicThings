package net.lomeli.mt.tile;

import ic2.api.energy.tile.IEnergySink;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.tile.TileEntityElectrical;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

import net.lomeli.mt.api.item.IChargeable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

public class TileEntityChargeStation extends TileEntityElectrical implements IEnergySink, IPowerReceptor, ISidedInventory {

    private ItemStack[] inventory;
    private int[] charging = new int[] { 0, 1, 2 }, finished = new int[] { 3, 4, 5 };

    public static final int[] sidedTop = new int[] { 1 };
    public static final int[] sidedOther = new int[] { 2, 3, 4, 5 };

    private PowerHandler powerHandler;

    private int maxInput = 32, energy, maxEnergy = 4000;

    public TileEntityChargeStation() {
        powerHandler = new PowerHandler(this, PowerHandler.Type.MACHINE);
        initPowerProvider();
        inventory = new ItemStack[7];
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!this.worldObj.isRemote) {
            if (this.powerHandler.getEnergyStored() > 16F)
                convertMJtoEU();
            if (isActive()) {
                for (int i = 0; i < 3; i++) {
                    chargeItems(charging[i], finished[i]);
                }
            }
        }
    }

    private void chargeItems(int slot1, int slot2) {
        if (this.inventory[slot2] != null)
            return;
        else {
            ItemStack stack = this.inventory[slot1];
            if (stack != null) {
                if (stack.getItem() instanceof IElectricItem) {
                    if (ElectricItem.manager.getCharge(stack) < ((IElectricItem) stack.getItem()).getMaxCharge(stack)) {
                        if (canCompleteTask(1)) {
                            repairItem(0, stack, 1);
                            placeItem(slot1, slot2);
                        }
                    }
                }
                if (stack.getItem() instanceof IPowerReceptor) {
                    if (((IPowerReceptor) stack.getItem()).getPowerReceiver(null).powerRequest() > 0F) {
                        repairItem(1, stack, 1);
                        placeItem(slot1, slot2);
                    }
                }
                if (stack.getItem() instanceof IChargeable) {
                    if (((IChargeable) stack.getItem()).canAcceptMoreEnergy(stack)) {
                        repairItem(2, stack, 1);
                        placeItem(slot1, slot2);
                    }
                }
                if (stack.getItem() instanceof IItemElectric) {
                    if (((IItemElectric) stack.getItem()).getElectricityStored(stack) < ((IItemElectric) stack.getItem()).getMaxElectricityStored(stack)) {
                        repairItem(3, stack, 1);
                        placeItem(slot1, slot2);
                    }
                }
            }
        }
    }

    private void placeItem(int slot1, int slot2) {
        ItemStack stack1 = this.inventory[slot1];
        ItemStack stack2 = this.inventory[slot2];
        ItemStack charged = null;
        boolean transfer = false;
        if (stack1.getItem() instanceof IElectricItem) {
            if (stack1.getItemDamage() == 0) {
                int id = ((IElectricItem) stack1.getItem()).getChargedItemId(stack1);
                charged = new ItemStack(Item.itemsList[id]);
                transfer = true;
            }
        } else if (stack1.getItem() instanceof IPowerReceptor) {
            if (!(((IPowerReceptor) stack1.getItem()).getPowerReceiver(null).powerRequest() > 0F)) {
                charged = stack1;
                transfer = true;
            }
        } else if (stack1.getItem() instanceof IChargeable) {
            if (((IChargeable) stack1.getItem()).canAcceptMoreEnergy(stack1)) {
                charged = stack1;
                transfer = true;
            }
        } else if (stack1.getItem() instanceof IItemElectric) {
            if (((IItemElectric) stack1.getItem()).getElectricityStored(stack1) >= ((IItemElectric) stack1.getItem()).getMaxElectricityStored(stack1)) {
                charged = stack1;
                transfer = true;
            }
        }

        if (transfer && charged != null) {
            if (stack2 == null && this.isItemValidForSlot(slot2, charged)) {
                this.setInventorySlotContents(slot1, null);
                this.setInventorySlotContents(slot2, charged);
            }
        }
    }

    private void repairItem(int repairType, ItemStack stack, int repair) {
        switch (repairType) {
        case 0:
            int tier = ((IElectricItem) stack.getItem()).getTier(stack);
            ElectricItem.manager.charge(stack, repair, tier, true, false);
            break;
        case 1:
            ((IPowerReceptor) stack.getItem()).getPowerReceiver(null).receiveEnergy(Type.MACHINE, (repair * 4), null);
            break;
        case 2:
            ((IChargeable) stack.getItem()).chargeItem(repair, stack);
            break;
        case 3:
            ((IItemElectric) stack.getItem()).recharge(stack, repair, true);
        }
        useEnergy(repair);
    }

    private boolean canCompleteTask(int required) {
        return (this.energy > 0 && this.energy >= required);
    }

    private void useEnergy(int amount) {
        this.energy -= amount;
        if (this.energy < 0)
            this.energy = 0;
    }

    private void convertMJtoEU() {
        addEnergy((int) (this.powerHandler.useEnergy(16, 32, false) / 4));
    }

    private void addEnergy(int amount) {
        this.energy += amount;
        if (this.energy > this.maxEnergy)
            this.energy = this.maxEnergy;
    }

    private void initPowerProvider() {
        if (powerHandler != null) {
            powerHandler.configure(8F, 16F, 20F, 150F);
            powerHandler.configurePowerPerdition(2, 1);
        }
    }

    public boolean isActive() {
        return energy > 100;
    }

    @Override
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        if (emitter != null)
            return true;
        return false;
    }

    @Override
    public double demandedEnergyUnits() {
        return this.maxEnergy - this.energy;
    }

    @Override
    public double injectEnergyUnits(ForgeDirection directionFrom, double amount) {
        if (demandedEnergyUnits() > 0)
            return 0D;
        if (this.energy >= this.maxEnergy)
            return amount;
        this.energy += amount;
        return 0D;
    }

    @Override
    public int getMaxSafeInput() {
        return this.maxInput;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        return this.inventory[i].splitStack(j);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return this.inventory[i];
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        inventory[slot] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
            itemStack.stackSize = getInventoryStackLimit();
    }

    @Override
    public String getInvName() {
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void onInventoryChanged() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        ItemStack slotItem = this.inventory[i];
        if (slotItem == null)
            return true;
        else {
            if (slotItem.isItemEqual(itemstack))
                return true;
            else
                return false;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return var1 == 1 ? sidedTop : sidedOther;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        if (j == 1) {
            return this.isItemValidForSlot(i, itemstack);
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        if (j > 1)
            return true;
        return false;
    }

    @Override
    public PowerReceiver getPowerReceiver(ForgeDirection side) {
        return this.powerHandler.getPowerReceiver();
    }

    @Override
    public void doWork(PowerHandler workProvider) {
    }

    @Override
    public World getWorld() {
        return this.worldObj;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.loadNBT(par1NBTTagCompound);
    }

    public void loadNBT(NBTTagCompound nbtTag) {
        this.powerHandler.readFromNBT(nbtTag);
        this.energy = nbtTag.getInteger("Energy");
        NBTTagList tagList = nbtTag.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inventory.length)
                inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        this.addToNBT(par1NBTTagCompound);
    }

    public void addToNBT(NBTTagCompound nbtTag) {
        this.powerHandler.writeToNBT(nbtTag);
        nbtTag.setInteger("Energy", this.energy);
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
            if (inventory[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);

                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTag.setTag("Inventory", tagList);
    }

    @Override
    public Packet getDescriptionPacket() {
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound tag = packet != null ? packet.data : new NBTTagCompound();
        this.addToNBT(tag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
        super.onDataPacket(networkManager, packet);
        NBTTagCompound nbtTag = packet.data;
        this.loadNBT(nbtTag);
    }

    @Override
    public float getRequest(ForgeDirection direction) {
        return (float) this.maxEnergy - this.energy;
    }

    @Override
    public float getProvide(ForgeDirection direction) {
        return 0;
    }

    @Override
    public float getMaxEnergyStored() {
        return this.maxEnergy;
    }
}
