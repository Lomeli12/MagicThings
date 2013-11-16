package net.lomeli.mt.api.item;

import net.minecraft.item.ItemStack;

public interface IChargeable {

    public int getCurrentEnergy(ItemStack stack);

    public int getMaxEnergy(ItemStack stack);

    public void chargeItem(int amount, ItemStack stack);

    public void useCharge(int amount, ItemStack stack);

    public boolean canAcceptMoreEnergy(ItemStack stack);

    public boolean givesOffEnergy();

    public boolean canCompleteTask(int amount, ItemStack stack);
}
