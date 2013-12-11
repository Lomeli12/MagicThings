package net.lomeli.mt.tile;

import net.lomeli.mt.api.recipes.MTRecipeHandlers;
import net.lomeli.mt.api.tile.ICrankable;
import net.lomeli.mt.api.tile.IMTTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityUnwinder extends TileEntity implements ISidedInventory, ICrankable, IMTTile {

    private ItemStack[] inventory;
    private int cranks;

    public TileEntityUnwinder() {
        inventory = new ItemStack[7];
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            for (int i = 0; i < 4; i++) {
                if (inventory[i] != null && MTRecipeHandlers.unwinderManager.isItemValid(inventory[i])) {
                    if (!hasWork()) {
                        setInventorySlotContents(6, inventory[i]);
                        setInventorySlotContents(i, null);
                    } else {
                        if (isItemValidForSlot(6, inventory[i])) {
                            if (inventory[6].stackSize < 64 && inventory[i].stackSize > 0) {
                                inventory[6].stackSize++;
                                inventory[i].stackSize--;
                            }
                        }
                    }
                }
            }
            doWork();
            super.onInventoryChanged();
        }
        super.updateEntity();
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
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null)
            setInventorySlotContents(slot, null);
        return itemStack;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
            itemstack.stackSize = getInventoryStackLimit();
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
        return 64;
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
        return (inventory[i] == null) ? true : (inventory[i].itemID == itemstack.itemID && inventory[i].getItemDamage() == itemstack.getItemDamage());
    }

    @Override
    public void doWork() {
        if (hasWork()) {
            if (MTRecipeHandlers.unwinderManager.isItemValid(inventory[6])) {
                if (getSteps() >= MTRecipeHandlers.unwinderManager.getCranksRequired(inventory[6])) {
                    addToOutputSlots(MTRecipeHandlers.unwinderManager.getOutput(inventory[6]));
                    decrStackSize(6, 1);
                    setSteps(0);
                    super.onInventoryChanged();
                }
            }
        }
    }

    public void addToOutputSlots(ItemStack stack) {
        for (int i = 3; i < 6; i++) {
            if (stack != null && isItemValidForSlot(i, stack)) {
                if (inventory[i] == null) {
                    this.setInventorySlotContents(i, stack);
                    return;
                } else {
                    int j = inventory[i].stackSize + stack.stackSize;
                    this.setInventorySlotContents(i, new ItemStack(stack.getItem(), j, stack.getItemDamage()));
                    return;
                }

            }
        }
    }

    public boolean hasWork() {
        return inventory[6] != null;
    }

    @Override
    public void incrementStep() {
        if (hasWork() && !worldObj.isRemote) {
            cranks++;
        }
    }

    @Override
    public int getSteps() {
        return cranks;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return (var1 == 1) ? new int[] { 0, 1, 2 } : new int[] { 3, 4, 5 };
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return true;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return true;
    }

    @Override
    public void setSteps(int steps) {
        this.cranks = steps;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        this.cranks = data.getInteger("Cranks");
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
        data.setInteger("Cranks", this.cranks);
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
}
