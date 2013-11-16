package net.lomeli.mt.inventory;

import net.lomeli.mt.tile.TileEntityUnwinder;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerUnwinder extends Container {
    private TileEntityUnwinder tileEntity;

    public ContainerUnwinder(InventoryPlayer inventoryPlayer, TileEntityUnwinder tile) {
        this.tileEntity = tile;

        this.addSlotToContainer(new Slot(tile, 0, 12, 17));
        this.addSlotToContainer(new Slot(tile, 1, 30, 17));
        this.addSlotToContainer(new Slot(tile, 2, 48, 17));

        this.addSlotToContainer(new SlotOutput(tile, 6, 79, 35));

        this.addSlotToContainer(new SlotOutput(tile, 3, 112, 53));
        this.addSlotToContainer(new SlotOutput(tile, 4, 130, 53));
        this.addSlotToContainer(new SlotOutput(tile, 5, 148, 53));

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }

        }

        for(int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

    @SuppressWarnings("unused")
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(par2 < this.tileEntity.getSizeInventory()) {
                if(!this.mergeItemStack(itemstack1, this.tileEntity.getSizeInventory(), this.inventorySlots.size(), true))
                    return null;
            }else if(!this.mergeItemStack(itemstack1, 0, this.tileEntity.getSizeInventory(), false))
                return null;

            if(itemstack1.stackSize == 0)
                slot.putStack(itemstack1);
            else
                slot.onSlotChanged();
        }

        return null;
    }

}
