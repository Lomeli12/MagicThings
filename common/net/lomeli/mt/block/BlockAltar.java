package net.lomeli.mt.block;

import java.util.List;

import net.lomeli.lomlib.block.BlockUtil;
import net.lomeli.lomlib.util.CustomBookUtil;

import net.lomeli.mt.item.ItemWrench;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockAltar extends BlockMT {

    public BlockAltar(int id) {
        super(id, Material.anvil, "altar");
        this.setBlockBounds(0F, 0F, 0F, 1F, 0.015625F, 1F);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if(player.isSneaking())
            return false;
        else {
            if(!world.isRemote) {
                if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemWrench) {
                    boolean hasDiamondBlock = false, hasNetherBlock = false, hasNeoGem = false, hasNetherStar = false, hasSword = false, fireStick = false, book = false;
                    List entityList = world.getEntitiesWithinAABB(EntityItem.class, player.boundingBox.expand(15D, 15D, 15D));
                    for(int p = 0; p < entityList.size(); p++) {
                        Entity ent = (Entity) entityList.get(p);
                        if(ent != null && ent instanceof EntityItem) {
                            EntityItem item = (EntityItem) ent;
                            if(BlockUtil.isAboveBlock(item, x, y, z)) {
                                if(item.getEntityItem().itemID == blockDiamond.blockID)
                                    hasDiamondBlock = true;
                                else if(item.getEntityItem().itemID == blockNetherQuartz.blockID)
                                    hasNetherBlock = true;
                                else if(item.getEntityItem().itemID == ModItems.neoGem.itemID)
                                    hasNeoGem = true;
                                else if(item.getEntityItem().itemID == Item.netherStar.itemID)
                                    hasNetherStar = true;
                                else if(item.getEntityItem().itemID == Item.swordDiamond.itemID)
                                    hasSword = true;
                                else if(item.getEntityItem().itemID == Item.stick.itemID)
                                    fireStick = true;
                                else if(item.getEntityItem().itemID == Item.book.itemID)
                                    book = true;
                            }
                        }
                    }

                    if(hasDiamondBlock && hasNetherBlock && hasNeoGem && hasNetherStar && hasSword && world.isThundering()) {
                        world.spawnEntityInWorld(new EntityLightningBolt(world, x, y, z));
                        ItemStack rewardStack = new ItemStack(ModItems.ikenoBlade);
                        rewardStack.addEnchantment(Enchantment.looting, 2);
                        rewardStack.addEnchantment(Enchantment.sharpness, 4);
                        EntityItem reward = new EntityItem(world, x, y + 20, z, rewardStack);
                        world.spawnEntityInWorld(reward);
                    }else if(world.isDaytime() && !world.isThundering() && !world.isRaining()) {
                        for(int p = 0; p < entityList.size(); p++) {
                            Entity ent = (Entity) entityList.get(p);
                            if(ent != null && ent instanceof EntityItem) {
                                EntityItem item = (EntityItem) ent;
                                if(BlockUtil.isAboveBlock(item, x, y, z)) {
                                    if(fireStick && item.getEntityItem().itemID == Item.stick.itemID) {
                                        ent.setDead();
                                        ItemStack rewardStack = new ItemStack(Item.stick);
                                        rewardStack.addEnchantment(Enchantment.fireAspect, 1);
                                        rewardStack.setItemName(StatCollector.translateToLocal("item.magicthings:FireStick"));
                                        EntityItem reward = new EntityItem(world, x, y + 20, z, rewardStack);
                                        world.spawnEntityInWorld(reward);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        for(int p = 0; p < entityList.size(); p++) {
                            Entity ent = (Entity) entityList.get(p);
                            if(ent != null && ent instanceof EntityItem) {
                                EntityItem item = (EntityItem) ent;
                                if(BlockUtil.isAboveBlock(item, x, y, z)) {
                                    if(book && item.getEntityItem().itemID == Item.book.itemID) {
                                        ent.setDead();
                                        ItemStack bookInfo = CustomBookUtil.createNewBook(
                                                "Lomeli12",
                                                StatCollector.translateToLocal(Strings.altar),
                                                new String[] { StatCollector.translateToLocal(Strings.altarpg1),
                                                        StatCollector.translateToLocal(Strings.altarpg2),
                                                        StatCollector.translateToLocal(Strings.altarpg3) });
                                        EntityItem reward = new EntityItem(world, x, y + 20, z, bookInfo);
                                        world.spawnEntityInWorld(reward);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}
