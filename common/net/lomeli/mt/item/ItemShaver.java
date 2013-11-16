package net.lomeli.mt.item;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.api.item.IChargeable;
import net.lomeli.mt.core.helper.ShaveableHelper;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

import net.minecraftforge.common.IShearable;

public class ItemShaver extends ItemMT implements IChargeable, IElectricItem {

    public ItemShaver(int id) {
        super(id, "shaver");
        this.setMaxStackSize(1);
        this.setMaxDamage(10001);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity) {
        if(!entity.worldObj.isRemote && itemstack.getItemDamage() < itemstack.getMaxDamage()) {
            player.closeScreen();
            Random rand = new Random();
            if(entity instanceof IShearable) {
                IShearable target = (IShearable) entity;
                if(target.isShearable(itemstack, entity.worldObj, (int) entity.posX, (int) entity.posY, (int) entity.posZ)) {
                    ArrayList<ItemStack> drops = target.onSheared(itemstack, entity.worldObj, (int) entity.posX,
                            (int) entity.posY, (int) entity.posZ,
                            EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));

                    for(ItemStack stack : drops) {
                        EntityItem ent = entity.entityDropItem(stack, 1.0F);
                        ent.motionY += rand.nextFloat() * 0.05F;
                        ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                        ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    }
                    itemstack.damageItem(10, entity);
                    if(ModLoaded.isModInstalled("IC2", false))
                        ElectricItem.manager.discharge(itemstack, 10, 1, true, false);
                }
                return true;
            }else if(ShaveableHelper.isShavableEntity(entity)) {
                ShaveableHelper.dropEntityItem(rand, player, entity);
                ShaveableHelper.getEntityEffect(rand, entity);
                itemstack.damageItem(ShaveableHelper.damageToShaver(entity), entity);
                player.closeScreen();
                if(ModLoaded.isModInstalled("IC2", false))
                    ElectricItem.manager.discharge(itemstack, ShaveableHelper.damageToShaver(entity), 1, true, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isItemTool(ItemStack par1ItemStack) {
        return true;
    }

    @Override
    public int getItemEnchantability() {
        return 10;
    }

    public boolean isItemEnchantable() {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
        return true;
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return block.blockID == Block.web.blockID || block.blockID == Block.redstoneWire.blockID
                || block.blockID == Block.tripWire.blockID;
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block, int metadata) {
        return block.blockID != Block.web.blockID && block.blockID != Block.leaves.blockID ? (block.blockID == Block.cloth.blockID ? 5.0F
                : super.getStrVsBlock(itemstack, block))
                : 15.0F;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6,
            EntityLivingBase par7EntityLivingBase) {
        if(par3 != Block.leaves.blockID && par3 != Block.web.blockID && par3 != Block.tallGrass.blockID
                && par3 != Block.vine.blockID && par3 != Block.tripWire.blockID
                && !(Block.blocksList[par3] instanceof IShearable))
            return super.onBlockDestroyed(par1ItemStack, par2World, par3, par4, par5, par6, par7EntityLivingBase);
        else
            return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        if(!player.worldObj.isRemote) {
            int id = player.worldObj.getBlockId(x, y, z);
            if(Block.blocksList[id] instanceof IShearable) {
                IShearable target = (IShearable) Block.blocksList[id];
                if(target.isShearable(itemstack, player.worldObj, x, y, z)) {
                    ArrayList<ItemStack> drops = target.onSheared(itemstack, player.worldObj, x, y, z,
                            EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
                    Random rand = new Random();
                    for(ItemStack stack : drops) {
                        float f = 0.7F;
                        double d = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                        double d1 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                        double d2 = rand.nextFloat() * f + (1.0F - f) * 0.5D;
                        EntityItem entityitem = new EntityItem(player.worldObj, x + d, y + d1, z + d2, stack);
                        entityitem.delayBeforeCanPickup = 10;
                        player.worldObj.spawnEntityInWorld(entityitem);
                    }
                }
                itemstack.damageItem(10, player);
                if(ModLoaded.isModInstalled("IC2", false))
                    ElectricItem.manager.discharge(itemstack, 10, 1, true, false);
                player.addStat(StatList.mineBlockStatArray[id], 1);
            }
        }
        return false;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if(entity != null && itemStack != null) {
            if(entity instanceof EntityPlayer) {
                if(ModLoaded.isModInstalled("IC2", false) && itemStack.getItem() instanceof IElectricItem) {
                    int i = ElectricItem.manager.getCharge(itemStack);
                    itemStack.setItemDamage(itemStack.getMaxDamage() - i);
                }
            }
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return true;
    }

    @Override
    public int getCurrentEnergy(ItemStack stack) {
        if(ModLoaded.isModInstalled("IC2", false) && stack.getItem() instanceof IElectricItem) {
            return ElectricItem.manager.getCharge(stack);
        }
        return (stack.getMaxDamage() - 1) - stack.getItemDamage();
    }

    @Override
    public int getMaxEnergy(ItemStack stack) {
        return (stack.getMaxDamage() - 1);
    }

    @Override
    public boolean canAcceptMoreEnergy(ItemStack stack) {
        if(ModLoaded.isModInstalled("IC2", false) && stack.getItem() instanceof IElectricItem) {
            return ElectricItem.manager.getCharge(stack) < (stack.getMaxDamage() - 1);
        }
        return stack.getItemDamage() > 0;
    }

    @Override
    public void chargeItem(int amount, ItemStack stack) {
        stack.setItemDamage(stack.getItemDamage() - amount);
        if(stack.getItemDamage() < 0)
            stack.setItemDamage(0);

        if(ModLoaded.isModInstalled("IC2", false) && stack.getItem() instanceof IElectricItem) {
            ElectricItem.manager.charge(stack, 1, 1, true, false);
        }
    }

    @Override
    public void useCharge(int amount, ItemStack stack) {
        stack.setItemDamage(stack.getItemDamage() + amount);
        if(stack.getItemDamage() > (stack.getMaxDamage() - 1))
            stack = null;

        if(ModLoaded.isModInstalled("IC2", false) && stack.getItem() instanceof IElectricItem) {
            ElectricItem.manager.discharge(stack, 1, 1, true, false);
        }
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public int getChargedItemId(ItemStack itemStack) {
        return this.itemID;
    }

    @Override
    public int getEmptyItemId(ItemStack itemStack) {
        return this.itemID;
    }

    @Override
    public int getMaxCharge(ItemStack itemStack) {
        return (this.getMaxDamage() - 1);
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack itemStack) {
        return 13;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if(itemStack != null && !ModLoaded.isModInstalled("IC2", false)) {
            infoList.add("Charge: " + getCurrentEnergy(itemStack) + "/" + getMaxCharge(itemStack));
        }
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
