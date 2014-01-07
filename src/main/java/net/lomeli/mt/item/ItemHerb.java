package net.lomeli.mt.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemHerb extends ItemMetaData {

    public Icon[] iconArray;

    public ItemHerb(int par1) {
        super(par1, "herb_", 4);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player != null && !player.capabilities.isCreativeMode) {
            int meta = itemStack.getItemDamage();
            switch (meta) {
            case 0:
                player.heal(2F);
                break;
            case 1:
                player.removePotionEffect(Potion.poison.id);
                break;
            case 3:
                player.addPotionEffect(new PotionEffect(22, 500, 1));
                break;
            }
            itemStack.stackSize--;
        }
        return itemStack;
    }
}
