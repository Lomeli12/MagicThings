package net.lomeli.mt.block;

import java.util.ArrayList;
import java.util.List;

import net.lomeli.mt.lib.BlockInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

public class BlockTreatedWool extends BlockMT {

    public Icon[] iconArray;

    public BlockTreatedWool(int id) {
        super(id, Material.cloth, "cloth_");
        this.setResistance(500F);
        this.setHardness(0.5F);
        this.setStepSound(soundClothFootstep);
        this.setUnlocalizedName("treatedWool");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if(!world.isRemote) {
            ItemStack currentItem = player.getCurrentEquippedItem();
            if(currentItem != null) {
                if(currentItem.getItem() instanceof ItemDye) {
                    if((15 - currentItem.getItemDamage()) != world.getBlockMetadata(x, y, z)) {
                        world.setBlockMetadataWithNotify(x, y, z, 15 - currentItem.getItemDamage(), 2);
                        if(!player.capabilities.isCreativeMode)
                            currentItem.stackSize--;
                    }
                }else {
                    for(int j = 15; j >= 0; j--) {
                        ArrayList<ItemStack> dictionaryDye = OreDictionary.getOres(dyes[j]);
                        if(!dictionaryDye.isEmpty()) {
                            if(dictionaryDye.contains(currentItem)) {
                                if((15 - i) != world.getBlockMetadata(x, y, z)) {
                                    world.setBlockMetadataWithNotify(x, y, z, 15 - i, 2);
                                    if(!player.capabilities.isCreativeMode)
                                        currentItem.stackSize--;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String[] dyes = { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray",
            "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    public Icon getIcon(int par1, int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }

    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int j = 0; j < 16; ++j) {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }

    public static int getBlockFromDye(int par0) {
        return ~par0 & 15;
    }

    public static int getDyeFromBlock(int par0) {
        return ~par0 & 15;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[16];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = cloth.getIcon(0, i);
        }
    }

    public static class ItemTreatedWool extends ItemBlock {

        public ItemTreatedWool(int par1) {
            super(par1);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @SideOnly(Side.CLIENT)
        public Icon getIconFromDamage(int par1) {
            return cloth.getIcon(0, getBlockFromDye(par1));
        }

        public int getMetadata(int par1) {
            return par1;
        }

        @Override
        public String getItemDisplayName(ItemStack par1ItemStack) {
            ItemStack wool = new ItemStack(cloth, 1, par1ItemStack.getItemDamage());
            String name = super.getItemDisplayName(par1ItemStack).substring(0,
                    (super.getItemDisplayName(par1ItemStack).length() - 5));
            return par1ItemStack.getItemDamage() == 0 ? name : wool.getDisplayName().substring(0,
                    wool.getDisplayName().length() - 4)
                    + name;
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
            if(itemStack != null) {
                infoList.add(StatCollector.translateToLocal(BlockInfo.woolText + "1"));
                infoList.add(StatCollector.translateToLocal(BlockInfo.woolText + "2"));
            }
        }

    }
}
