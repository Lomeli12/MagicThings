package net.lomeli.mt.item.special;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

import net.lomeli.mt.MThings;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumHelper;

public class ItemRunningShoes extends ItemArmor {
    public static UUID speedID = UUID.randomUUID();
    public static AttributeModifier speedBoost = (new AttributeModifier(speedID, "Running Shoe Speed boost", 0.2, 2));

    public ItemRunningShoes(int par1) {
        super(par1, EnumHelper.addArmorMaterial(Strings.MOD_ID.toLowerCase() + ":runnningShoes", -1, new int[] { 0, 0, 0, 0 }, 0), 0, 3);
        this.setCreativeTab(MThings.modtab);
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":runningShoes");
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":runningShoes");
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if (entity != null && itemStack != null) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                boolean flag = (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem().getUnlocalizedName()
                        .equals(ModItems.runningShoes.getUnlocalizedName()));
                ItemRunningShoes.applyModifier(player, flag);
            }
        }
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
        return (Strings.MOD_ID.toLowerCase() + ":textures/model/runningShoes.png");
    }

    public static void applyModifier(EntityLivingBase entity, boolean par1) {
        AttributeInstance attributeinstance = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

        if (attributeinstance.getModifier(speedID) != null)
            attributeinstance.removeModifier(speedBoost);

        if (par1)
            attributeinstance.applyModifier(speedBoost);
        else
            attributeinstance.removeModifier(speedBoost);
    }

}
