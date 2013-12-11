package net.lomeli.mt.block.energy;

import java.util.List;
import java.util.Random;

import net.lomeli.mt.block.BlockMT;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.tile.energy.TileEntityCable;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCable extends BlockMT implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockCable(int id) {
        super(id, Material.cloth, "cable");
        setHardness(0.5F);
        setResistance(10F);
        setUnlocalizedName("cable");
        setBlockBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
        setCreativeTab(null);
       
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityCable();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[4];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":cable/cable_" + i);
        }
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int j = 0; j < 4; ++j) {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3){
        return ModItems.cable.itemID;
    }

    @Override
    public Icon getIcon(int par1, int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }

    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public int idPicked(World par1World, int par2, int par3, int par4){
        return ModItems.cable.itemID;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderBlockPass() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public static class ItemCable extends ItemBlock {
        
        @SideOnly(Side.CLIENT)
        private Icon[] iconArray;

        public ItemCable(int arg0) {
            super(arg0);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }
        
        @Override
        public void registerIcons(IconRegister iconRegister) {
            iconArray = new Icon[4];
            for (int i = 0; i < iconArray.length; i++) {
                iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":cable/cable_" + i);
            }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public Icon getIconFromDamage(int par1) {
            return iconArray[par1];
        }

        @Override
        public int getMetadata(int par1) {
            return par1;
        }

        @Override
        public String getItemDisplayName(ItemStack par1ItemStack) {
            String name = super.getItemDisplayName(par1ItemStack);
            switch (par1ItemStack.getItemDamage()) {
            case 0:
                return StatCollector.translateToLocal("string.magicthings:Basic") + " " + name;
            case 1:
                return StatCollector.translateToLocal("string.magicthings:Adv") + " " + name;
            case 2:
                return StatCollector.translateToLocal("string.magicthings:Glass") + " " + name;
            case 3:
                return StatCollector.translateToLocal("string.magicthings:Fiber") + " " + name;
            default:
                return StatCollector.translateToLocal("string.magicthings:Basic") + " " + name;
            }
        }

    }
}
