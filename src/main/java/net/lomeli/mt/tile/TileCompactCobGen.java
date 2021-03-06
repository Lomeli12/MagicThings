package net.lomeli.mt.tile;

import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.util.FluidUtil;
import net.lomeli.lomlib.util.InventoryUtil;

import net.lomeli.mt.api.tile.IMTTile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

public class TileCompactCobGen extends TileEntity implements IFluidHandler, IInventory, IMTTile {
    private FluidTank lava;
    private FluidTank water;
    private ItemStack[] inventory;
    private int updateTicks;
    private boolean active, redstone;

    public TileCompactCobGen() {
        lava = new FluidTank(40000);
        water = new FluidTank(40000);
        inventory = new ItemStack[2];
        redstone = true;
    }

    public void flipRedstoneSettings() {
        redstone = !redstone;
    }

    public boolean respondsToRedStone() {
        return redstone ? !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) : true;
    }
    
    public void genCobble() {
        if (inventory[0] == null)
            inventory[0] = new ItemStack(Block.cobblestone, 1);
        else
            inventory[0].stackSize++;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            if (lava.getFluidAmount() >= 1000 && water.getFluidAmount() >= 1000) {
                if (respondsToRedStone()) {
                    active = true;
                    updateTicks++;
                    if (updateTicks > this.getProductionSpeed()) {
                        genCobble();
                        updateTicks = 0;
                    }
                } else
                    active = false;
            } else {
                active = false;
            }
            updateMeta();
            injectItems();
        }
    }

    public void updateMeta() {
        int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        meta &= ~0b0011;
        if (active)
            meta |= 1;
        else
            meta |= 0;
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
    }

    public int getProductionSpeed() {
        int x = lava.getFluidAmount() + water.getFluidAmount();
        return x > 24000 ? ((80000 / x) * 15) : 50;
    }

    public void spawnEntityItem(EntityPlayer player, ItemStack stack) {
        if (!this.worldObj.isRemote)
            EntityUtil.spawnItemOnPlayer(this.worldObj, player, stack);
    }

    public void produceObsidian(EntityPlayer player) {
        if (water.getFluidAmount() >= 1000 && lava.getFluidAmount() >= 1000) {
            water.drain(1000, true);
            lava.drain(1000, true);
            this.spawnEntityItem(player, new ItemStack(Block.obsidian));
        }
    }

    public void produceStone(EntityPlayer player) {
        if (water.getFluidAmount() >= 100 && lava.getFluidAmount() >= 5) {
            water.drain(100, true);
            lava.drain(5, true);
            this.spawnEntityItem(player, new ItemStack(Block.stone));
        }
    }

    public void injectItems() {
        if (hasCobble()) {
            TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
            if (tile == null)
                return;
            try {
                if (Class.forName("factorization.common.TileEntityBarrel").isInstance(tile)) {
                    for (int i = 0; i < ((ISidedInventory) tile).getSizeInventory(); i++) {
                        if (((ISidedInventory) tile).canInsertItem(i, this.getStackInSlot(0), 1)) {
                            ItemStack stack = ((ISidedInventory) tile).getStackInSlot(i), newStack = this.getStackInSlot(0);
                            if (stack != null)
                                newStack.stackSize += stack.stackSize;
                            ((ISidedInventory) tile).setInventorySlotContents(i, newStack);
                            this.setInventorySlotContents(0, null);
                            worldObj.markBlockForRenderUpdate(xCoord, yCoord - 1, zCoord);
                            return;
                        }
                    }
                    return;
                }
            } catch (Exception e) {
            }

            if (tile instanceof ISidedInventory) {
                for (int i = 0; i < ((ISidedInventory) tile).getSizeInventory(); i++) {
                    if (((ISidedInventory) tile).canInsertItem(InventoryUtil.getFirstEmptyStack((ISidedInventory) tile), this.getStackInSlot(0), 1)
                            && ((ISidedInventory) tile).isItemValidForSlot(i, this.getStackInSlot(0))) {
                        InventoryUtil.addItemsToInventory(this.getStackInSlot(0), ((ISidedInventory) tile));
                    }
                }
            } else if (tile instanceof IInventory) {
                for (int i = 0; i < ((IInventory) tile).getSizeInventory(); i++) {
                    if (((IInventory) tile).isItemValidForSlot(i, this.getStackInSlot(0))) {
                        InventoryUtil.addItemsToInventory(this.getStackInSlot(0), ((IInventory) tile));
                    }
                }
            }
        }
    }

    public void giveCobble(EntityPlayer player, int want) {
        if (hasCobble() && !this.worldObj.isRemote) {
            int amount = inventory[0].stackSize;
            if (amount > want) {
                for (int i = 0; i < want; i++) {
                    this.spawnEntityItem(player, new ItemStack(Block.cobblestone, 1));
                }
                inventory[0].stackSize -= want;
            } else {
                for (int i = 0; i < amount; i++) {
                    this.spawnEntityItem(player, new ItemStack(Block.cobblestone, 1));
                }
                inventory[0] = null;
            }
        }
    }

    public boolean hasCobble() {
        return (this.getStackInSlot(0) != null && this.getStackInSlot(0).itemID == Block.cobblestone.blockID);
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        lava.readFromNBT(data);
        water.readFromNBT(data);
        NBTTagList tagList = data.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < inventory.length)
                inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
        }
        redstone = data.getBoolean("redStone");
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        writeNBT(data);
    }

    public void writeNBT(NBTTagCompound data) {
        lava.writeToNBT(data);
        water.writeToNBT(data);
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
        data.setBoolean("redStone", redstone);
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
        if (FluidUtil.isFluidWater(resource))
            return water.fill(resource, true);
        else if (FluidUtil.isFluidLava(resource))
            return lava.fill(resource, true);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (doDrain) {
            if (from == ForgeDirection.NORTH || from == ForgeDirection.SOUTH)
                return new FluidStack(lava.getFluid(), maxDrain);
            else
                return new FluidStack(water.getFluid(), maxDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return FluidUtil.isFluidLava(fluid) || FluidUtil.isFluidLava(fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { lava.getInfo(), water.getInfo() };
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
        if(inventory[i] != null){
            ItemStack returnStack = inventory[i].copy();
            inventory[i] = null;
            return returnStack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        if (itemstack != null)
            inventory[i] = itemstack;
    }

    @Override
    public String getInvName() {
        return "tile.magicthings:CobGen.name";
    }

    @Override
    public boolean isInvNameLocalized() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 320;
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

}
