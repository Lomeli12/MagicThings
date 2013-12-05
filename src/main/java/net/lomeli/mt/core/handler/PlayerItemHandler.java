package net.lomeli.mt.core.handler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.lomeli.lomlib.item.NBTUtil;
import net.lomeli.mt.item.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

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
        if (event.entityItem.getEntityItem() != null && event.entityItem.getEntityItem().itemID == ModItems.flyingRing.itemID) {
            if (event.player != null && !event.player.capabilities.isCreativeMode) {
                event.player.stepHeight = 0.5F;
                event.player.capabilities.allowFlying = false;
                event.player.capabilities.isFlying = false;
            }
        }
    }

    @ForgeSubscribe
    public void onPlayerDrop(PlayerDropsEvent event) {
        if (event.entityPlayer != null && !event.entityPlayer.capabilities.isCreativeMode) {
            for (EntityItem drops : event.drops) {
                if (drops.getEntityItem() != null && drops.getEntityItem().itemID == ModItems.flyingRing.itemID) {
                    event.entityPlayer.stepHeight = 0.5F;
                    event.entityPlayer.capabilities.allowFlying = false;
                    event.entityPlayer.capabilities.isFlying = false;
                }
            }
        }
    }

    @ForgeSubscribe
    public void onItemPickUpEvent(EntityItemPickupEvent event) {
        if (event.item != null && event.entityPlayer != null) {
            EntityPlayer player = event.entityPlayer;
            EntityItem itemEntity = event.item;
            if (player.inventory.hasItem(ModItems.magnet.itemID)) {
                for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                    ItemStack item = player.inventory.getStackInSlot(i);
                    if (item != null) {
                        if (item.itemID == ModItems.magnet.itemID && !player.capabilities.isCreativeMode)
                            NBTUtil.setInteger(item, "magnetDamage", NBTUtil.getInt(item, "magnetDamage") + itemEntity.getEntityItem().stackSize);
                    }
                }
            }
        }
    }
}
