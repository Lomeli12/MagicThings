package net.lomeli.mt.item;

import net.lomeli.lomlib.util.KeyBoardUtil;

import net.lomeli.mt.lib.Ints.KeyConfigs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMountTool extends ItemMT {

    private EntityLivingBase mount;
    private int tick;

    public ItemMountTool(int id) {
        super(id, "saddle");
        this.setMaxDamage(75);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity) {
        if(itemstack != null && player != null && entity != null) {
            if(!player.isSneaking()) {
                if(entity.riddenByEntity == null && !entity.isRiding()) {
                    player.mountEntity(entity);
                    itemstack.damageItem(1, player);
                }
            }else {
                if(entity instanceof EntityHorse || entity instanceof EntityGhast)
                    return false;
                if(mount != null && !mount.equals(entity) && !mount.isRiding() && !entity.isRiding()
                        && mount.riddenByEntity == null && entity.riddenByEntity == null) {
                    entity.mountEntity(mount);
                    tick++;
                    if(tick > 1) {
                        player.addChatMessage(entity.getTranslatedEntityName() + " is now riding a "
                                + mount.getTranslatedEntityName());
                        tick = 0;
                    }
                    itemstack.damageItem(5, player);
                    mount = null;
                }else {
                    mount = entity;
                    tick++;
                    if(tick > 1) {
                        player.addChatMessage("Set rideable entity.");
                        tick = 0;
                    }
                }

                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if(itemStack != null && world != null && player != null) {
            if(KeyBoardUtil.isKeyDown(KeyConfigs.KEY_MOUNT_TOOL_CLEAR) && mount != null) {
                mount = null;
                player.addChatMessage("Cleared saved entity.");
            }
        }
        return itemStack;
    }

}
