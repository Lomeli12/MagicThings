package net.lomeli.mt.core.helper;

import java.util.Random;

import net.lomeli.mt.lib.Ints;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class ShaveableHelper {

    public static boolean isShavableEntity(EntityLivingBase entity) {
        boolean canShave = false;
        if (entity != null) {
            if (entity instanceof EntityCow)
                canShave = true;
            else if (entity instanceof EntityChicken)
                canShave = true;
            else if (entity instanceof EntitySquid)
                canShave = true;
            else if (entity instanceof EntityVillager)
                canShave = true;
            else if (entity instanceof EntityCreeper)
                canShave = true;
            else if (entity instanceof EntityPigZombie)
                canShave = true;
            else if (entity instanceof EntitySkeleton)
                canShave = true;
        }
        return canShave;
    }

    private static ItemStack getDropItem(EntityLivingBase entity) {
        ItemStack stack = null;
        if (entity != null && entity instanceof EntityLiving) {
            if (entity instanceof EntityCow)
                stack = new ItemStack(Item.leather);
            else if (entity instanceof EntityChicken)
                stack = new ItemStack(Item.feather);
            else if (entity instanceof EntitySquid)
                stack = new ItemStack(Item.dyePowder);
            else if (entity instanceof EntityVillager)
                stack = new ItemStack(Item.emerald);
            else if (entity instanceof EntityCreeper)
                stack = new ItemStack(Item.gunpowder);
            else if (entity instanceof EntityPigZombie) {
                if (entity.getCurrentItemOrArmor(0) != null) {
                    stack = entity.getCurrentItemOrArmor(0);
                    entity.setCurrentItemOrArmor(0, null);
                } else
                    stack = new ItemStack(Item.goldNugget);
            } else if (entity instanceof EntitySkeleton) {
                if (entity.getCurrentItemOrArmor(0) != null) {
                    stack = entity.getCurrentItemOrArmor(0);
                    entity.setCurrentItemOrArmor(0, null);
                } else {
                    for (int i = 1; i < 5; i++) {
                        ItemStack temp = entity.getCurrentItemOrArmor(i);
                        if (temp != null) {
                            stack = temp;
                            entity.setCurrentItemOrArmor(i, null);
                            break;
                        }
                    }
                }
            }
        }
        return stack;
    }

    private static float damageToEntity(EntityLivingBase entity) {
        float damage = 1F;
        if (entity != null)
            damage = (entity.getMaxHealth() / 5);
        return damage;
    }

    public static int damageToShaver(EntityLivingBase entity) {
        if (entity != null) {
            if (entity instanceof EntityVillager || entity instanceof EntityPigZombie || entity instanceof EntitySkeleton)
                return 50;
        }
        return 10;
    }

    public static void dropEntityItem(Random rand, EntityPlayer player, EntityLivingBase entity) {
        if (entity != null && entity.hurtTime <= 0 && !entity.isDead) {
            int chance = 0;
            if (entity instanceof EntityVillager || entity instanceof EntityPigZombie || entity instanceof EntitySkeleton)
                chance = rand.nextInt(100);

            if (chance < Ints.shaverLootChance) {
                ItemStack stack = getDropItem(entity);
                if (stack != null) {
                    EntityItem ent = entity.entityDropItem(stack, 1F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
            }
            entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damageToEntity(entity));
        }
    }

    /*
     * private static boolean isVillagerSuspicious(EntityLivingBase entity){
     * boolean suspicious = false; if(entity != null && entity instanceof
     * EntityVillager){ entity.getEntityData().getBoolean("Susp"); } return
     * suspicious; }
     */

    public static void getEntityEffect(Random rand, EntityLivingBase entity) {
        if (entity != null) {
            if (entity instanceof EntityCreeper) {
                boolean flag = entity.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                int power = 1;
                if (((EntityCreeper) entity).getPowered())
                    power = 2;
                if (rand.nextInt(100) < Ints.shaverExplodeChance) {
                    entity.worldObj.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 3 * power, flag);
                    entity.setDead();
                }
            }
        }
    }
}
