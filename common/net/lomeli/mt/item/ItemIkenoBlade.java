package net.lomeli.mt.item;

import java.util.List;

import net.lomeli.lomlib.util.NBTUtil;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemIkenoBlade extends ItemSword {

    public ItemIkenoBlade(int par1) {
        super(par1, EnumToolMaterial.EMERALD);
        this.setCreativeTab(MThings.modtab);
        this.setMaxDamage(-1);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if(entity != null && itemStack != null && !world.isRemote) {
            int tick = NBTUtil.getInt(itemStack, "timedAblitiyTick");
            tick++;
            if(tick >= 30) {
                if(entity instanceof EntityLivingBase) {
                    EntityLivingBase entityLiving = (EntityLivingBase) entity;

                    @SuppressWarnings("rawtypes")
                    List entityList = world.getEntitiesWithinAABB(EntityLivingBase.class,
                            entityLiving.boundingBox.expand(15D, 15D, 15D));
                    for(int p = 0; p < entityList.size(); p++) {
                        Entity ent = (Entity) entityList.get(p);
                        if(ent != null && ent != entityLiving && ent instanceof EntityLivingBase) {
                            EntityLivingBase target = (EntityLivingBase) ent;
                            if(world.rand.nextInt(100) < 10) {
                                if(target.getHeldItem() != null) {
                                    EntityItem entItem = target.entityDropItem(target.getHeldItem(), 1F);
                                    if(entItem != null) {
                                        entItem.motionY += world.rand.nextFloat() * 0.05F;
                                        entItem.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.1F;
                                        entItem.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.1F;
                                        world.spawnEntityInWorld(entItem);
                                        target.setCurrentItemOrArmor(0, null);
                                    }
                                }
                            }
                        }
                    }
                    if(entityLiving instanceof EntityPlayer) {
                        if(world.rand.nextInt(1000000) < 10) {
                            if(((EntityPlayer) entityLiving).username.equalsIgnoreCase("drifterlord")) {
                                world.playSoundAtEntity(((EntityPlayer) entityLiving), "mob.endermen.stare", 1.0F, 1.0F);
                            }else if(((EntityPlayer) entityLiving).username.equalsIgnoreCase("ohaiiChun")) {
                                world.playSoundAtEntity(((EntityPlayer) entityLiving), "mob.pig.death", 1.0F, 1.0F);
                            }
                        }
                    }
                }
                tick = 0;
            }
            NBTUtil.setInteger(itemStack, "", tick);
        }
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":ikenoSword");
    }

    @Override
    public Item setUnlocalizedName(String str) {
        super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + str);
        return this;
    }

    @Override
    public String getItemDisplayName(ItemStack par1ItemStack) {
        return super.getItemDisplayName(par1ItemStack).substring(0, super.getItemDisplayName(par1ItemStack).length() - 5);
    }

}
