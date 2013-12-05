package net.lomeli.mt.inventory;

import net.lomeli.mt.item.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

public class SlotSpecialCrafting extends SlotCrafting {

    public SlotSpecialCrafting(EntityPlayer par1EntityPlayer, IInventory par2iInventory, IInventory par3iInventory, int par4, int par5, int par6) {
        super(par1EntityPlayer, par2iInventory, par3iInventory, par4, par5, par6);
    }

    @Override
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
        if (!par1EntityPlayer.capabilities.isCreativeMode) {
            if (par1EntityPlayer.inventory.getCurrentItem().itemID == ModItems.portaCraft.itemID) {
                par1EntityPlayer.inventory.getCurrentItem().setItemDamage(par1EntityPlayer.inventory.getCurrentItem().getItemDamage() + 1);
            }
        }
    }

}
