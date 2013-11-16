package net.lomeli.mt.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemPeaceTreaty extends ItemMT {

    public ItemPeaceTreaty(int id) {
        super(id, "peace");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if(itemStack != null)
            infoList.add("Calms angry Pigmen and Wolves.");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity) {
        if(itemstack != null && player != null && entity != null) {
            if(entity instanceof EntityPigZombie) {
                if(((EntityPigZombie) entity).getAttackTarget() != null) {
                    List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity,
                            entity.boundingBox.expand(32.0D, 32.0D, 32.0D));
                    for(int i = 0; i < list.size(); ++i) {
                        Entity ent = (Entity) list.get(i);
                        if(ent instanceof EntityPigZombie) {
                            ((EntityPigZombie) ent).angerLevel = 0;
                            ((EntityPigZombie) ent).entityToAttack = null;
                            
                        }
                    }
                    ((EntityPigZombie) entity).angerLevel = 0;
                    ((EntityPigZombie) entity).entityToAttack = null;
                    itemstack.stackSize--;
                    return true;
                }
            }
            if(entity instanceof EntityWolf) {
                if(((EntityWolf) entity).isAngry()) {
                    List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity,
                            entity.boundingBox.expand(32.0D, 32.0D, 32.0D));
                    for(int i = 0; i < list.size(); ++i) {
                        Entity ent = (Entity) list.get(i);
                        if(ent instanceof EntityWolf) {
                            if(((EntityWolf) ent).isAngry()) {
                                ((EntityWolf) ent).setAngry(false);
                                ((EntityWolf) ent).setAttackTarget(null);
                            }
                        }
                    }
                    ((EntityWolf) entity).setAngry(false);
                    ((EntityWolf) entity).setAttackTarget(null);
                    itemstack.stackSize--;
                    return true;
                }
            }
        }
        return false;
    }
}
