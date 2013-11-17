package net.lomeli.mt.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemEverlastingToast extends ItemMT {

    public ItemEverlastingToast(int id) {
        super(id, "toast");
        this.setMaxDamage(1000);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if(player != null) {
            if(!player.isSneaking()) {
                player.getFoodStats().addStats((ItemFood) Item.appleRed);
                itemStack.damageItem(5, player);
            }
        }
        return itemStack;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if(itemStack != null) {
            infoList.add(StatCollector.translateToLocal("subtext.magicthings:toast"));
        }
    }

}
