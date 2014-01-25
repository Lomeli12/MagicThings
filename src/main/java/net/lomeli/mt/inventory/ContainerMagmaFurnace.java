package net.lomeli.mt.inventory;

import net.lomeli.mt.api.recipes.MTRecipeHandlers;
import net.lomeli.mt.tile.TileEntityMagmaFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMagmaFurnace extends Container {

    private TileEntityMagmaFurnace furnace;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
    private int lastFluidAmount;

    public ContainerMagmaFurnace(InventoryPlayer inventoryPlayer, TileEntityMagmaFurnace tile) {
        this.furnace = tile;
        this.addSlotToContainer(new Slot(this.furnace, 0, 56, 17));
        this.addSlotToContainer(new Slot(this.furnace, 1, 56, 53));

        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.furnace.cookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.furnace.currentBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 3, this.furnace.getMainTank().getFluidAmount());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.lastCookTime != this.furnace.cookTime)
                icrafting.sendProgressBarUpdate(this, 0, this.furnace.cookTime);

            if (this.lastBurnTime != this.furnace.furnaceBurnTime)
                icrafting.sendProgressBarUpdate(this, 1, this.furnace.furnaceBurnTime);

            if (this.lastItemBurnTime != this.furnace.currentBurnTime)
                icrafting.sendProgressBarUpdate(this, 2, this.furnace.currentBurnTime);

            if (this.lastFluidAmount != this.furnace.getMainTank().getFluidAmount())
                icrafting.sendProgressBarUpdate(this, 3, this.furnace.getMainTank().getFluidAmount());
        }

        this.lastCookTime = this.furnace.cookTime;
        this.lastBurnTime = this.furnace.furnaceBurnTime;
        this.lastItemBurnTime = this.furnace.currentBurnTime;
        this.lastFluidAmount = this.furnace.getMainTank().getFluidAmount();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);

        if (par1 == 0)
            this.furnace.cookTime = par2;

        if (par1 == 1)
            this.furnace.furnaceBurnTime = par2;

        if (par1 == 2)
            this.furnace.currentBurnTime = par2;

        if (par1 == 3)
            this.furnace.getMainTank().getFluid().amount = par2;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();
            
            if (slotIndex < furnace.getSizeInventory()){
                if (!this.mergeItemStack(slotItemStack, furnace.getSizeInventory(), inventorySlots.size(), false))
                    return null;
            }else {
                if (TileEntityFurnace.isItemFuel(slotItemStack)) {
                    if (!this.mergeItemStack(slotItemStack, 1, 1, false))
                        return null;
                } else if(MTRecipeHandlers.magmaFurnace.isItemValid(slotItemStack)) {
                    if (!this.mergeItemStack(slotItemStack, 0, 1, false))
                        return null;
                } else
                    return null;
            }
            
            if (slotItemStack.stackSize == 0)
                slot.putStack(null);
            else
                slot.onSlotChanged();
        }
        return itemStack;
    }

}
