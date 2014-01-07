package net.lomeli.mt.core.helper;

import net.lomeli.mt.api.item.IChargeable;
import net.lomeli.mt.api.recipes.MTRecipeHandlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class RechargeHelper {

    public static void rechargeOtherItems(ItemStack itemStack, EntityPlayer player) {
        if (itemStack != null && player != null && itemStack.getItem() instanceof IChargeable && ((IChargeable) itemStack.getItem()).givesOffEnergy()
                && ((IChargeable) itemStack.getItem()).canCompleteTask(10, itemStack)) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack item = player.inventory.getStackInSlot(i);
                if (item != null && item.getItem() instanceof IChargeable && !((IChargeable) item.getItem()).givesOffEnergy()) {
                    if (((IChargeable) item.getItem()).canAcceptMoreEnergy(item)) {
                        ((IChargeable) item.getItem()).chargeItem(10, item);
                        ((IChargeable) itemStack.getItem()).useCharge(10, itemStack);
                    }
                }
            }
        }
    }

    private static void useFuel(int itemID, EntityPlayer player, ItemStack item, int energy) {
        if (item != null && item.getItem() instanceof IChargeable) {
            player.inventory.consumeInventoryItem(itemID);
            ((IChargeable) item.getItem()).chargeItem(energy, item);
        }
    }

    public static boolean manualRecharge(EntityPlayer player, ItemStack stack) {
        boolean charged = false;
        if (player != null && stack != null && stack.getItem() instanceof IChargeable) {
            if (((IChargeable) stack.getItem()).canAcceptMoreEnergy(stack)) {
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    ItemStack item = player.inventory.getStackInSlot(i);
                    if (item != null) {
                        if (MTRecipeHandlers.flyingRingFuel.isFuelRegistered(item)) {
                            useFuel(item.itemID, player, stack, MTRecipeHandlers.flyingRingFuel.getFuelValue(item));
                            charged = true;
                        }
                    }
                }
            }
        }
        return charged;
    }

    public static boolean autoRecharge(EntityPlayer player, ItemStack stack) {
        if (player != null && stack != null && stack.getItem() instanceof IChargeable)
            return manualRecharge(player, stack);
        return false;
    }
}
