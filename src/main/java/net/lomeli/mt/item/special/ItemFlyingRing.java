package net.lomeli.mt.item.special;

import java.util.List;

import net.lomeli.lomlib.item.NBTUtil;
import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.addon.GraviSuiteAddon;
import net.lomeli.mt.api.item.IChargeable;
import net.lomeli.mt.core.helper.RechargeHelper;
import net.lomeli.mt.item.ItemMT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import morph.api.Api;
import morph.common.ability.AbilityHandler;

public class ItemFlyingRing extends ItemMT implements IChargeable {

    public ItemFlyingRing(int id) {
        super(id, "flyingring");
        this.setMaxDamage(40001);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.rare;
    }

    public void setWalkHeight(ItemStack itemStack) {
        boolean bool = NBTUtil.getBoolean(itemStack, "walk");
        NBTUtil.setBoolean(itemStack, "walk", !bool);
    }

    public boolean getWalkHeight(ItemStack itemStack) {
        return NBTUtil.getBoolean(itemStack, "walk");
    }

    private int tick;

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player != null) {
            if (player.isSneaking()) {
                this.setWalkHeight(itemStack);
                boolean walk = this.getWalkHeight(itemStack);
                tick++;
                if (tick > 1) {
                    player.addChatMessage("Walk over single blocks: " + walk);
                    tick = 0;
                }
                if (walk && this.hasEnergy(itemStack))
                    player.stepHeight = 1F;
                else
                    player.stepHeight = 0.5F;
            } else
                RechargeHelper.manualRecharge(player, itemStack);
        }
        return itemStack;
    }

    private boolean modSupport(EntityPlayer player) {
        if (ModLoaded.isModInstalled("Morph")) {
            EntityLivingBase morph = Api.getMorphEntity(player.username, true);
            if (morph != null)
                return AbilityHandler.hasAbility(morph.getClass(), "fly");
        }
        if (ModLoaded.isModInstalled("GraviSuite"))
            return GraviSuiteAddon.isFlyModOn(player);
        return false;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if (entity != null && itemStack != null) {
            if (entity instanceof EntityPlayer && itemStack.getItem() instanceof IChargeable) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.capabilities.isCreativeMode == false) {
                    if (this.canCompleteTask(1, itemStack)) {
                        if (player.capabilities.allowFlying == false)
                            player.capabilities.allowFlying = true;

                        if (player.capabilities.isFlying == true && !modSupport(player))
                            itemStack.damageItem(1, player);
                        else if (player.fallDistance >= 4F && !modSupport(player))
                            itemStack.damageItem((int) (player.fallDistance / 4), player);
                    } else {
                        if (RechargeHelper.autoRecharge(player, itemStack)) {
                            if (player.capabilities.allowFlying == true)
                                player.capabilities.allowFlying = false;

                            player.stepHeight = 0.5F;

                            if (player.capabilities.isFlying == true)
                                player.capabilities.isFlying = false;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if (itemStack != null) {
            int unit = this.getCurrentEnergy(itemStack);
            infoList.add("Energy: " + unit + "/" + (itemStack.getMaxDamage() - 1));
        }
    }

    public boolean hasEnergy(ItemStack stack) {
        return (stack.getItemDamage() < stack.getMaxDamage());
    }

    @Override
    public int getCurrentEnergy(ItemStack itemStack) {
        return (itemStack.getMaxDamage() - itemStack.getItemDamage() - 1);
    }

    public int getEnergyNeeded(ItemStack stack) {
        return (stack.getMaxDamage() - stack.getItemDamage() - 1);
    }

    @Override
    public int getMaxEnergy(ItemStack stack) {
        return (stack.getMaxDamage() - 1);
    }

    @Override
    public void chargeItem(int amount, ItemStack stack) {
        stack.setItemDamage(stack.getItemDamage() - amount);
    }

    @Override
    public void useCharge(int amount, ItemStack stack) {
        stack.setItemDamage(stack.getItemDamage() + amount);
    }

    @Override
    public boolean canAcceptMoreEnergy(ItemStack stack) {
        return (stack.getItemDamage() > 0);
    }

    @Override
    public boolean givesOffEnergy() {
        return false;
    }

    @Override
    public boolean canCompleteTask(int amount, ItemStack stack) {
        return this.getCurrentEnergy(stack) > amount;
    }
}
