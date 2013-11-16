package net.lomeli.mt.inventory;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class InventoryCondenseBag implements IInventory {
    private ItemStack[] inventory;
    protected String uniqueID;

    public InventoryCondenseBag(ItemStack stack, EntityPlayer player) {
        int size = 22;
        if(stack.getItemDamage() != 0)
            size += 22;
        inventory = new ItemStack[size];
        if(!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();

            uniqueID = UUID.randomUUID().toString();
        }
        readFromNBT(stack.stackTagCompound);
    }

    public void setUniqueID(String id) {
        uniqueID = id;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        NBTTagCompound contentTag = ((NBTTagCompound) nbtTagCompound.getTag("divingGearInv"));
        if(contentTag == null)
            return;

        if("".equals(this.uniqueID)) {
            this.uniqueID = nbtTagCompound.getString("uniqueID");
            if("".equals(this.uniqueID))
                this.uniqueID = UUID.randomUUID().toString();
        }
        NBTTagList list = contentTag.getTagList("indexList");
        for(int i = 0; i < list.tagCount() && i < this.inventory.length; i++) {
            NBTTagCompound indexTag = (NBTTagCompound) list.tagAt(i);
            int index = indexTag.getInteger("index");
            try {
                this.inventory[index] = ItemStack.loadItemStackFromNBT(indexTag);
            }catch(NullPointerException e) {
                this.inventory[index] = null;
            }
        }
    }

    public void writeToNBT(NBTTagCompound myCompound, String id) {
        NBTTagList myList = new NBTTagList();

        for(int i = 0; i < this.inventory.length; i++) {
            if(this.inventory[i] != null && this.inventory[i].stackSize > 0) {
                NBTTagCompound indexTag = new NBTTagCompound();
                myList.appendTag(indexTag);
                indexTag.setInteger("index", i);
                inventory[i].writeToNBT(indexTag);
            }
        }
        NBTTagCompound contentTag = new NBTTagCompound();
        contentTag.setTag("indexList", myList);
        myCompound.setTag("divingGearInv", contentTag);
        myCompound.setString("uniqueID", id);
    }

    public void writeToNBT(NBTTagCompound myCompound) {
        writeToNBT(myCompound, this.uniqueID);
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
        if(itemStack != null) {
            if(itemStack.stackSize <= amount)
                setInventorySlotContents(slot, null);
            else {
                itemStack.splitStack(amount);
                if(itemStack.stackSize == 0)
                    setInventorySlotContents(slot, null);
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory[i];
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
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
    public void onInventoryChanged() {
        for(int i = 0; i < inventory.length; i++) {
            ItemStack temp = getStackInSlot(i);
            if(temp != null && temp.stackSize == 0)
                setInventorySlotContents(i, null);
        }
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
        if(itemstack != null) {
            if(inventory[i].itemID == itemstack.itemID && inventory[i].getItemDamage() == itemstack.getItemDamage()) {
                return true;
            }
        }
        return false;
    }

}
