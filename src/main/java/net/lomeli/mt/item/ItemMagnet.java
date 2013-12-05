package net.lomeli.mt.item;

import java.util.List;

import net.lomeli.lomlib.item.NBTUtil;
import net.lomeli.mt.lib.ItemInfo;
import net.lomeli.mt.lib.Strings;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemMagnet extends ItemMT {

    private Icon[] iconArray;

    public ItemMagnet(int id) {
        super(id, "magnet_");
        this.setMaxStackSize(1);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[2];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.itemTexture + i);
        }
    }

    @Override
    public Icon getIconFromDamage(int i) {
        return iconArray[i];
    }

    private int tick;

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (itemStack != null && player != null && world != null) {
            if (player.isSneaking()) {
                NBTUtil.setBoolean(itemStack, "pull", !NBTUtil.getBoolean(itemStack, "pull"));
                if (NBTUtil.getBoolean(itemStack, "pull")) {
                    itemStack.setItemDamage(0);
                    tick++;
                    if (tick > 1) {
                        player.addChatMessage("[Magnet]: Attract Mode");
                        tick = 0;
                    }
                } else {
                    itemStack.setItemDamage(1);
                    tick++;
                    if (tick > 1) {
                        player.addChatMessage("[Magnet]: Repel Mode");
                        tick = 0;
                    }
                }
            } else {
                tick++;
                if (tick > 1) {
                    if (NBTUtil.getBoolean(itemStack, "pull"))
                        player.addChatMessage("[Magnet]: Attract Mode");
                    else
                        player.addChatMessage("[Magnet]: Repel Mode");
                    tick = 0;
                }
            }
        }
        return itemStack;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
        if (itemStack != null && world != null && player != null) {
            NBTUtil.setInteger(itemStack, "magnetDamage", 0);
            NBTUtil.setBoolean(itemStack, "pull", true);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if (itemStack != null)
            infoList.add((ItemInfo.magnetDurability - NBTUtil.getInt(itemStack, "magnetDamage")) + "/" + ItemInfo.magnetDurability);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if (entity != null && itemStack != null) {

            if (NBTUtil.getInt(itemStack, "magnetDamage") > ItemInfo.magnetDurability)
                itemStack.stackSize--;
            else {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    if (!world.isRemote) {
                        List entityList = world.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(15.0D, 15.0D, 15.0D));
                        for (int i = 0; i < entityList.size(); i++) {
                            Entity ent = (Entity) entityList.get(i);
                            if (ent != null && ent instanceof EntityItem) {
                                double xOff = 1, yOff = 1, zOff = 1;
                                if ((player.posX - ent.posX) < 0)
                                    xOff = xOff * -1;
                                if ((player.posY - ent.posY) < 0)
                                    yOff = yOff * -1;
                                if ((player.posZ - ent.posZ) < 0)
                                    zOff = zOff * -1;

                                if (!NBTUtil.getBoolean(itemStack, "pull") && ent.getDistanceToEntity(player) < 5F) {
                                    xOff = xOff * -1;
                                    yOff = yOff * -1;
                                    zOff = zOff * -1;
                                    ent.moveEntity(xOff, yOff, zOff);
                                } else if (ent.getDistanceToEntity(player) < 12F)
                                    ent.moveEntity(xOff, yOff, zOff);
                            }
                        }
                    }
                }
            }
        }
    }

}
