package net.lomeli.mt.addon;

import net.lomeli.lomlib.util.ModLoaded;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import thaumcraft.common.config.ConfigItems;

public class ThaumCraftAddon {
    public static boolean doesPlayerHaveBoTAndEnchant(EntityPlayer player) {
        if (ModLoaded.isModInstalled("Thaumcraft")) {
            if (player.inventory.armorItemInSlot(0) != null) {
                ItemStack itemStack = player.inventory.armorItemInSlot(0);
                return (itemStack.itemID == ConfigItems.itemBootsTraveller.itemID);
            }
        }
        return false;
    }
}
