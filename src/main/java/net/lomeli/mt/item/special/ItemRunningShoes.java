package net.lomeli.mt.item.special;

import net.minecraft.entity.Entity;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumHelper;

public class ItemRunningShoes extends ItemArmor {
    public ItemRunningShoes(int par1) {
        super(par1, EnumHelper.addArmorMaterial(Strings.MOD_ID.toLowerCase() + ":runningShoes", -1, new int[] { 0, 0, 0, 0 }, 0), 0, 3);
        this.setCreativeTab(MThings.modtab);
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":runningShoes");
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":runningShoes");
    }

    @Override
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack) {
        if (player != null && itemStack != null) {
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1, 0));
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
        return (Strings.MOD_ID.toLowerCase() + ":textures/models/runningShoes.png");
    }

}
