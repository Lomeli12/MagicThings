package net.lomeli.mt.tile;

import net.lomeli.mt.api.recipes.MTRecipeHandlers;
import net.lomeli.mt.api.tile.ITanks;
import net.lomeli.mt.lib.GuiInfo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMagmaFurnace extends TileEntity implements ITanks, ISidedInventory, IFluidHandler {
    public int furnaceBurnTime, currentBurnTime, cookTime;
    private FluidTank lavaTank;
    private ItemStack[] inventory;

    public TileEntityMagmaFurnace() {
        inventory = new ItemStack[2];
        lavaTank = new FluidTank(new FluidStack(FluidRegistry.LAVA, 0), 16000);
    }

    @SideOnly(Side.CLIENT)
    public int getLiquidScaled(int i) {
        return lavaTank.getFluid() != null ? (int) (((float) lavaTank.getFluid().amount / (float) (lavaTank.getCapacity())) * i) : 0;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (furnaceBurnTime > 0)
                furnaceBurnTime--;

            if (furnaceBurnTime == 0 && canSmelt()) {
                currentBurnTime = furnaceBurnTime = TileEntityFurnace.getItemBurnTime(inventory[1]);
                if (furnaceBurnTime > 0) {
                    if (inventory[1] != null) {
                        --inventory[1].stackSize;
                        if (inventory[1].stackSize == 0)
                            inventory[1] = inventory[1].getItem().getContainerItemStack(inventory[1]);
                    }
                }
            }
            if (canSmelt()) {
                ++cookTime;
                if (cookTime >= 800) {
                    cookTime = 0;
                    smeltItem();
                }
            } else
                cookTime = 0;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        this.cookTime = data.getInteger("cookTime");
        this.currentBurnTime = data.getInteger("currentCookTime");
        this.furnaceBurnTime = data.getInteger("burnTime");
        this.lavaTank.readFromNBT(data);
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
        data.setInteger("cookTime", cookTime);
        data.setInteger("currentCookTime", currentBurnTime);
        data.setInteger("burnTime", furnaceBurnTime);
        lavaTank.writeToNBT(data);
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
    public int getCookProgressScaled(int par1) {
        return cookTime * par1 / 800;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1) {
        if (this.currentBurnTime == 0)
            this.currentBurnTime = 800;

        return this.furnaceBurnTime * par1 / this.currentBurnTime;
    }

    private boolean canSmelt() {
        if (inventory[0] != null) {
            if (MTRecipeHandlers.magmaFurnace.isItemValid(inventory[0])) {
                int amount = MTRecipeHandlers.magmaFurnace.getResult(inventory[0]);
                if (lavaTank.getFluidAmount() < lavaTank.getCapacity())
                    return ((lavaTank.getFluidAmount() + amount) <= lavaTank.getCapacity());
            }
        }
        return false;
    }

    private void smeltItem() {
        if (canSmelt()) {
            int amount = MTRecipeHandlers.magmaFurnace.getResult(inventory[0]);
            FluidStack lava = new FluidStack(FluidRegistry.LAVA, amount);
            lavaTank.fill(lava, true);
            this.decrStackSize(0, 1);
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
        return inventory[i];
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
    }

    @Override
    public String getInvName() {
        return GuiInfo.magmaFurnaceTile;
    }

    @Override
    public boolean isInvNameLocalized() {
        return true;
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
        return inventory[i] != null ? (inventory[i].itemID == itemstack.itemID && inventory[i].getItemDamage() == itemstack.getItemDamage()) : true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int par1) {
        return par1 == 1 ? new int[] { 0 } : new int[] { 1 };
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return this.isItemValidForSlot(i, itemstack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return true;// i != 0 || j != 1 || itemstack.itemID ==
                    // Item.bucketEmpty.itemID;
    }

    @Override
    public FluidTank getMainTank() {
        return lavaTank;
    }

    @Override
    public boolean hasMultipleTanks() {
        return false;
    }

    @Override
    public FluidTank[] getTanks() {
        return new FluidTank[] {};
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(lavaTank.getFluid()))
            return null;
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return lavaTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return (from != ForgeDirection.DOWN || from != ForgeDirection.UP) ? true : false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { lavaTank.getInfo() };
    }

}
