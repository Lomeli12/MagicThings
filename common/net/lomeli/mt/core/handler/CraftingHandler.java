package net.lomeli.mt.core.handler;

import net.lomeli.mt.block.BlockTreatedWool;

import cpw.mods.fml.common.ICraftingHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingHandler implements ICraftingHandler {

    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
        if(item != null) {
            if(item.getItem() instanceof BlockTreatedWool.ItemTreatedWool) {
                player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
        }
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item) {

    }
}
