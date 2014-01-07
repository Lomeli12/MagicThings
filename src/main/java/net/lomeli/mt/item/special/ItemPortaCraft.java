package net.lomeli.mt.item.special;

import net.lomeli.mt.MThings;
import net.lomeli.mt.item.ItemMT;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPortaCraft extends ItemMT {

    public ItemPortaCraft(int id) {
        super(id, "portaCraft");
        this.setMaxDamage(500);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (itemStack != null && player != null && world != null) {
            if (!player.isSneaking() && !world.isRemote) {
                player.openGui(MThings.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }
        return itemStack;
    }
}
