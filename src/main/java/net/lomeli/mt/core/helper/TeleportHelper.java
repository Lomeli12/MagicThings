package net.lomeli.mt.core.helper;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

public class TeleportHelper {
    public static boolean teleportRandomly(Entity entity, World worldObj, Random rand) {
        double d0 = entity.posX + (rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = entity.posY + (rand.nextInt(64) - 32);
        double d2 = entity.posZ + (rand.nextDouble() - 0.5D) * 64.0D;
        return teleportTo(entity, worldObj, d0, d1, d2, rand);
    }

    public static boolean teleportToEntity(Entity par1Entity, World worldObj, double posX, double posY, double posZ, Random rand) {
        Vec3 vec3 = worldObj.getWorldVec3Pool().getVecFromPool(posX - par1Entity.posX,
                par1Entity.boundingBox.minY + par1Entity.height / 2.0F - par1Entity.posY + par1Entity.getEyeHeight(), posZ - par1Entity.posZ);
        vec3 = vec3.normalize();
        double d0 = 16.0D;
        double d1 = posX + (rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
        double d2 = posY + (rand.nextInt(16) - 8) - vec3.yCoord * d0;
        double d3 = posZ + (rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
        return teleportTo(par1Entity, worldObj, d1, d2, d3, rand);
    }

    public static boolean teleportItemToEntity(EntityItem entity, World world, Random rand, Entity par2Entity) {
        double d1 = par2Entity.posX;
        double d2 = par2Entity.posY;
        double d3 = par2Entity.posZ;
        return teleportItemTo(entity, world, d1, d2, d3, rand);
    }

    public static boolean teleportItemTo(EntityItem entity, World worldObj, double par1, double par3, double par5, Random rand) {

        double d3 = entity.posX;
        double d4 = entity.posY;
        double d5 = entity.posZ;
        entity.posX = par1;
        entity.posY = par3;
        entity.posZ = par5;
        boolean flag = false;
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l;

        if (worldObj.blockExists(i, j, k)) {
            boolean flag1 = false;

            while (!flag1 && j > 0) {
                l = worldObj.getBlockId(i, j - 1, k);

                if (l != 0 && Block.blocksList[l].blockMaterial.blocksMovement()) {
                    flag1 = true;
                } else {
                    --entity.posY;
                    --j;
                }
            }

            if (flag1) {
                entity.setPosition(entity.posX, entity.posY, entity.posZ);

                if (entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty()) {
                    flag = true;
                }
            }
        }

        if (!flag) {
            entity.setPosition(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for (l = 0; l < short1; ++l) {
                double d6 = l / (short1 - 1.0D);
                float f = (rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (entity.posX - d3) * d6 + (rand.nextDouble() - 0.5D) * entity.width * 2.0D;
                double d8 = d4 + (entity.posY - d4) * d6 + rand.nextDouble() * entity.height;
                double d9 = d5 + (entity.posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * entity.width * 2.0D;
                worldObj.spawnParticle("portal", d7, d8, d9, f, f1, f2);
            }

            worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            entity.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }

    public static boolean teleportTo(Entity entity, World worldObj, double par1, double par3, double par5, Random rand) {
        EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase) entity, par1, par3, par5, 0);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return false;
        }

        double d3 = entity.posX;
        double d4 = entity.posY;
        double d5 = entity.posZ;
        entity.posX = event.targetX;
        entity.posY = event.targetY;
        entity.posZ = event.targetZ;
        boolean flag = false;
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l;

        if (worldObj.blockExists(i, j, k)) {
            boolean flag1 = false;

            while (!flag1 && j > 0) {
                l = worldObj.getBlockId(i, j - 1, k);

                if (l != 0 && Block.blocksList[l].blockMaterial.blocksMovement()) {
                    flag1 = true;
                } else {
                    --entity.posY;
                    --j;
                }
            }

            if (flag1) {
                entity.setPosition(entity.posX, entity.posY, entity.posZ);

                if (entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox).isEmpty()) {
                    if (entity instanceof EntityEnderman) {
                        if (!worldObj.isAnyLiquid(entity.boundingBox))
                            flag = true;
                    } else
                        flag = true;
                }
            }
        }

        if (!flag) {
            entity.setPosition(d3, d4, d5);
            return false;
        } else {
            short short1 = 128;

            for (l = 0; l < short1; ++l) {
                double d6 = l / (short1 - 1.0D);
                float f = (rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (entity.posX - d3) * d6 + (rand.nextDouble() - 0.5D) * entity.width * 2.0D;
                double d8 = d4 + (entity.posY - d4) * d6 + rand.nextDouble() * entity.height;
                double d9 = d5 + (entity.posZ - d5) * d6 + (rand.nextDouble() - 0.5D) * entity.width * 2.0D;
                worldObj.spawnParticle("portal", d7, d8, d9, f, f1, f2);
            }

            worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            entity.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }
}
