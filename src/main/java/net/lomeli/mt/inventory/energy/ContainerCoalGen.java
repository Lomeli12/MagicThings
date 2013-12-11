package net.lomeli.mt.inventory.energy;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.mt.tile.energy.TileEntityCoalGen;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerCoalGen extends Container {

    private TileEntityCoalGen gen;
    private int lastBurnTime, lastCurrentBurnTime;
    private float energy;

    public ContainerCoalGen(InventoryPlayer inventoryPlayer, TileEntityCoalGen tile) {
        this.gen = tile;
        this.addSlotToContainer(new Slot(this.gen, 0, 56, 53));

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
        par1ICrafting.sendProgressBarUpdate(this, 0, this.gen.burnTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.gen.currentBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, (int) this.gen.getEnergy());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (this.lastBurnTime != this.gen.burnTime)
                icrafting.sendProgressBarUpdate(this, 0, this.gen.burnTime);

            if (this.lastCurrentBurnTime != this.gen.currentBurnTime)
                icrafting.sendProgressBarUpdate(this, 1, this.gen.currentBurnTime);

            if (this.energy != this.gen.getEnergy())
                icrafting.sendProgressBarUpdate(this, 2, (int) this.gen.getEnergy());
        }

        this.lastBurnTime = this.gen.burnTime;
        this.lastCurrentBurnTime = this.gen.currentBurnTime;
        this.energy = this.gen.getEnergy();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);

        if (par1 == 0)
            this.gen.burnTime = par2;

        if (par1 == 1)
            this.gen.currentBurnTime = par2;

        if (par1 == 2)
            this.gen.setEnergy(par2);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (TileEntityFurnace.isItemFuel(itemstack1)) {
                if (this.mergeItemStack(itemstack1, 0, 0, true))
                    return null;
            }
            slot.onSlotChange(itemstack1, itemstack);

            if (itemstack1.stackSize == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();

            if (itemstack1.stackSize == itemstack.stackSize)
                return null;

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

}
