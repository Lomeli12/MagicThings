package net.lomeli.mt.tile;

import java.util.Random;

import net.lomeli.lomlib.entity.FakePlayer;
import net.lomeli.lomlib.util.InventoryUtil;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;

public class TileStoneFisherman extends TileEntity implements IInventory {
    private ItemStack[] inventory;
    private FakePlayer player;
    private boolean startFishing, bobberOut;
    private int fishingTime;
    private Random rand = new Random();

    public TileStoneFisherman() {
        inventory = new ItemStack[3];
        player = new FakePlayer(worldObj);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (player.getCurrentEquippedItem() != null) {
                ItemStack stack = player.getCurrentEquippedItem();
                if (stack.getUnlocalizedName().equals(Item.fishingRod.getUnlocalizedName()))
                    startFishing = (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && stack.getItemDamage() >= (stack.getMaxDamage() - 2));
            }

            if (startFishing) {
                if (player.getCurrentEquippedItem() != null) {
                    ItemStack stack = player.getCurrentEquippedItem();
                    if (bobberOut) {
                        if (--fishingTime <= 0) {
                            bobberOut = false;
                            addFish();
                        }
                    } else {
                        bobberOut = true;
                        worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
                        worldObj.spawnEntityInWorld(new EntityFishHook(worldObj, player));
                        stack.damageItem(2, player);
                        fishingTime = 100 + rand.nextInt(900);
                    }
                }
            }

        }
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
    public ItemStack getStackInSlotOnClosing(int i) {
        if (inventory[i] != null) {
            ItemStack returnStack = inventory[i].copy();
            inventory[i] = null;
            return returnStack;
        }
        return null;
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
        return 1;
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
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        player.readFromNBT(data);
        startFishing = data.getBoolean("startFishing");
        bobberOut = data.getBoolean("bobberOut");
        fishingTime = data.getInteger("fishingTime");
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
        player.writeToNBT(data);
        data.setBoolean("startFishing", startFishing);
        data.setBoolean("bobberOut", bobberOut);
        data.setInteger("fishingTime", fishingTime);
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

    private void addFish() {
        int slot = inventory.length;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                slot = i;
                break;
            } else if (inventory[i].getUnlocalizedName().equals(Item.fishRaw.getUnlocalizedName())) {
                if (inventory[i].stackSize < inventory[i].getMaxStackSize()) {
                    slot = i;
                    break;
                }
            }
        }

        if (slot != inventory.length) {
            if (inventory[slot] == null)
                setInventorySlotContents(slot, new ItemStack(Item.fishRaw));
            else
                inventory[slot].stackSize += 1;
        } else {
            EntityItem fish = new EntityItem(worldObj, xCoord, yCoord, zCoord, new ItemStack(Item.fishRaw));
            worldObj.spawnEntityInWorld(fish);
        }
    }

    public void addFishingRod(EntityPlayer entityPlayer) {
        ItemStack stack = entityPlayer.getCurrentEquippedItem();
        if (stack != null && stack.getUnlocalizedName().equals(Item.fishingRod.getUnlocalizedName())) {
            if (player.getCurrentEquippedItem() != null) {
                EntityItem oldRod = new EntityItem(worldObj, xCoord, yCoord, zCoord, player.getCurrentEquippedItem());
                worldObj.spawnEntityInWorld(oldRod);
                player.setItemInHand(null);
            }
            player.setItemInHand(stack);
            entityPlayer.getCurrentEquippedItem().stackSize--;
        }
    }

    public void insertIntoAdjacentInventory() {
        TileEntity[] adjacentTiles = new TileEntity[] { getTileAt(xCoord + 1, yCoord, zCoord), getTileAt(xCoord - 1, yCoord, zCoord), getTileAt(xCoord, yCoord, zCoord + 1),
                getTileAt(xCoord, yCoord, zCoord - 1) };
        TileEntity firstOut = null;
        for (TileEntity tile : adjacentTiles) {
            if (tile != null && tile instanceof IInventory) {
                firstOut = tile;
            }
        }

        if (firstOut != null) {
            IInventory tileInv = ((IInventory) firstOut);
            for (int i = 0; i < this.getSizeInventory(); i++) {
                ItemStack stack = inventory[i];
                if (stack != null) {
                    TileEntityHopper.insertStack(tileInv, stack, 0);
                }
            }
        }
    }
    
    public int getNextSlot(IInventory inv) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack == null)
                return i;
            else {
                if (stack.stackSize < stack.getMaxStackSize())
                    return i;
            }
        }
        return inv.getSizeInventory();
    }

    public TileEntity getTileAt(int x, int y, int z) {
        return worldObj.getBlockTileEntity(x, y, z);
    }
}
