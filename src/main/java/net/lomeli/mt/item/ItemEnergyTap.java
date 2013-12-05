package net.lomeli.mt.item;

import java.util.List;

import net.lomeli.mt.api.item.IChargeable;
import net.lomeli.mt.core.helper.RechargeHelper;
import net.lomeli.mt.lib.Ints;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnergyTap extends ItemMT implements IChargeable {
    private int tick;

    public ItemEnergyTap(int id) {
        super(id, "energytap");
        this.setMaxDamage(60000);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player != null) {
            if (!player.isSneaking()) {
                RechargeHelper.rechargeOtherItems(itemStack, player);
            } else {
                if (!world.isRemote) {
                    if (!world.isDaytime() || world.isRaining() || world.isThundering())
                        player.addChatMessage("Cannot collect energy under these conditions!");
                    else
                        player.addChatMessage("The Sun's warmth fuels the gem.");
                }
            }
        }
        return itemStack;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if (entity != null && itemStack != null) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                tick++;
                if (tick > Ints.energyTapTick) {
                    if (this.canAcceptMoreEnergy(itemStack) && world.isDaytime() && !world.isRemote && !world.isRaining() && !world.isThundering())
                        itemStack.damageItem(-5, player);
                    tick = 0;
                }
            }
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if (itemStack != null) {
            int unit = this.getCurrentEnergy(itemStack);
            infoList.add("Energy: " + unit + "/" + itemStack.getMaxDamage());
        }
    }

    public boolean hasEnergy(ItemStack stack) {
        return (stack.getItemDamage() < 0);
    }

    @Override
    public int getCurrentEnergy(ItemStack itemStack) {
        return (itemStack.getMaxDamage() - itemStack.getItemDamage());
    }

    public int getEnergyNeeded(ItemStack stack) {
        return (stack.getMaxDamage() - stack.getItemDamage());
    }

    @Override
    public int getMaxEnergy(ItemStack stack) {
        return stack.getMaxDamage();
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
