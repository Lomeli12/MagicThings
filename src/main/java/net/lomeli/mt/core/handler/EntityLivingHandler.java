package net.lomeli.mt.core.handler;

import java.util.Random;

import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.addon.ThaumCraftAddon;
import net.lomeli.mt.entity.EntityClicker;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.item.special.ItemRunningShoes;
import net.lomeli.mt.potion.PotionInfection;

import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EntityLivingHandler {

    @ForgeSubscribe
    public void onEntityUpdate(LivingUpdateEvent event) {
        if (event.entityLiving instanceof EntityClicker) {
            EntityClicker clicker = (EntityClicker) event.entityLiving;
            if (clicker.getAttackTarget() != null && clicker.getAttackTarget() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) clicker.getAttackTarget();
                if (EntityUtil.isEntityMoving(player)) {
                    clicker.incrementTargetTick();
                    if (clicker.getTargetTick() >= 20) {
                        clicker.setTarget(null);
                        clicker.setTargetTick(0);
                    }
                } else {
                    clicker.setTargetTick(0);
                    clicker.setAttackTarget(player);
                }
            }
        }

        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
            boolean flag = (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem().getUnlocalizedName()
                    .equals(ModItems.runningShoes.getUnlocalizedName()));
            ItemRunningShoes.applyModifier(player, flag);
        }

        if (event.entityLiving.isPotionActive(PotionInfection.infect)) {
            if (event.entityLiving.getActivePotionEffect(PotionInfection.infect).getDuration() <= 0)
                event.entityLiving.removePotionEffect(PotionInfection.infect.id);
            else {
                if (!EntityUtil.isUndeadEntity(event.entityLiving) && event.entityLiving.worldObj.rand.nextInt(10) == 0)
                    event.entityLiving.attackEntityFrom(PotionInfection.infection, 3F);
            }
        }
    }

    @ForgeSubscribe
    public void onEntityLivingDeath(LivingDeathEvent event) {
        Random rand = new Random();
        if (EntityUtil.wasEntityKilledByPlayer(event.source))
            EntityUtil.entityDropItem(event.entityLiving, new ItemStack(ModItems.error, 1), 0.01d, true);

        if (event.source.equals(PotionInfection.infection)) {
            if (event.entityLiving instanceof EntityPlayer) {
                if (rand.nextInt(100) < 25 && event.entityLiving.isBurning()) {
                    EntityClicker newClicker = new EntityClicker(event.entityLiving.worldObj);
                    newClicker.copyLocationAndAnglesFrom(event.entityLiving);
                    newClicker.onSpawnWithEgg((EntityLivingData) null);
                    event.entityLiving.worldObj.spawnEntityInWorld(newClicker);
                }
            }
        }

        if (event.source.getSourceOfDamage() instanceof EntityClicker) {
            if (event.entityLiving instanceof EntityPlayer) {
                if (rand.nextInt(100) < 35 && !event.source.getSourceOfDamage().isBurning() && !event.entityLiving.isBurning()) {
                    EntityClicker newClicker = new EntityClicker(event.entityLiving.worldObj);
                    newClicker.copyLocationAndAnglesFrom(event.entityLiving);
                    newClicker.onSpawnWithEgg((EntityLivingData) null);
                    event.entityLiving.worldObj.spawnEntityInWorld(newClicker);
                }
            }
        }
    }

    public boolean isThaumcraftInstalled(EntityPlayer player) {
        if (ModLoaded.isModInstalled("Thaumcraft"))
            return ThaumCraftAddon.doesPlayerHaveBoTAndEnchant(player);
        return false;
    }

}
