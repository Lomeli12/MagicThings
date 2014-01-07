package net.lomeli.mt.inventory;

import java.util.UUID;

import net.lomeli.mt.item.special.ItemCondenseBag;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerCondenseBag extends Container {
    public InventoryCondenseBag bagInventory;
    public boolean updateNotification;
    private int rows;

    public ContainerCondenseBag(ItemStack stack, InventoryCondenseBag inventory, InventoryPlayer player) {
        if (!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();

            inventory.setUniqueID(UUID.randomUUID().toString());
        }

        this.bagInventory = inventory;
        this.updateNotification = false;
        int y = 20;
        int y_change = 23;

        rows = 2;
        if (stack.getItemDamage() != 0) {
            rows += 2;
            y_change = 0;
            y = 6;
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < rows; j++) {
                this.addSlotToContainer(new SlotBag(inventory, i + j * 11, 8 + i * 18, y + j * 18));
            }
        }

        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(player, i1 + l * 9 + 9, 26 + i1 * 18, (84 - y_change) + l * 18));
            }
        }

        for (int j1 = 0; j1 < 9; j1++) {
            this.addSlotToContainer(new Slot(player, j1, 26 + j1 * 18, (142 - y_change)));
        }
    }

    @Override
    public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player) {
        Slot tmpSlot;
        if (slotID >= 0 && slotID < inventorySlots.size())
            tmpSlot = (Slot) inventorySlots.get(slotID);
        else
            tmpSlot = null;

        if (tmpSlot != null && tmpSlot.isSlotInInventory(player.inventory, player.inventory.currentItem))
            return tmpSlot.getStack();

        updateNotification = true;
        return super.slotClick(slotID, buttonPressed, flag, player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            try {
                if (itemstack1.getItem() instanceof ItemCondenseBag || (Class.forName("forestry.storage.items.ItemBackpack").isInstance(itemstack1.getItem())))
                    return null;
            } catch (ClassNotFoundException e) {
            }

            if (par2 < rows * 11) {
                if (!this.mergeItemStack(itemstack1, rows * 12, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, rows * 12, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

}
