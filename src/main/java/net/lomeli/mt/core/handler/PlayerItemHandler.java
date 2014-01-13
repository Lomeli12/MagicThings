package net.lomeli.mt.core.handler;

import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.item.special.ItemRunningShoes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PlayerItemHandler {

    @ForgeSubscribe
    @SideOnly(Side.CLIENT)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.entityPlayer != null && !event.entityPlayer.capabilities.isCreativeMode) {
            if (!event.entityPlayer.inventory.hasItem(ModItems.flyingRing.itemID)) {
                event.entityPlayer.stepHeight = 0.5F;
                event.entityPlayer.capabilities.allowFlying = false;
                event.entityPlayer.capabilities.isFlying = false;
            }
        }
    }

    @ForgeSubscribe
    public void onItemTossEvent(ItemTossEvent event) {
        if (event.player instanceof EntityPlayer) {
            EntityPlayer player = event.player;
            boolean flag = (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem().getUnlocalizedName()
                    .equals(ModItems.runningShoes.getUnlocalizedName()));
            ItemRunningShoes.applyModifier(player, flag);
        }
        if (event.entityItem.getEntityItem() != null) {
            if (event.entityItem.getEntityItem().itemID == ModItems.flyingRing.itemID) {
                if (event.player != null && !event.player.capabilities.isCreativeMode) {
                    event.player.stepHeight = 0.5F;
                    event.player.capabilities.allowFlying = false;
                    event.player.capabilities.isFlying = false;
                }
            }
        }
    }

    @ForgeSubscribe
    public void onPlayerDrop(PlayerDropsEvent event) {
        if (event.entityPlayer instanceof EntityPlayer) {
            EntityPlayer player = event.entityPlayer;
            boolean flag = (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem().getUnlocalizedName()
                    .equals(ModItems.runningShoes.getUnlocalizedName()));
            ItemRunningShoes.applyModifier(player, flag);
        }
        if (event.entityPlayer != null && !event.entityPlayer.capabilities.isCreativeMode) {
            for (EntityItem drops : event.drops) {
                if (drops.getEntityItem() != null) {
                    if (drops.getEntityItem().itemID == ModItems.flyingRing.itemID) {
                        event.entityPlayer.stepHeight = 0.5F;
                        event.entityPlayer.capabilities.allowFlying = false;
                        event.entityPlayer.capabilities.isFlying = false;
                    }
                }
            }
        }
    }
}
