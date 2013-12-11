package net.lomeli.mt.block;

import java.util.List;

import net.lomeli.mt.lib.Strings;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends BlockMT {
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockDecor(int id) {
        super(id, Material.rock, "decor_");
        this.setResistance(20F);
        this.setHardness(4.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("decor");
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[4];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":decor/decor_" + i);
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
    public Icon getIcon(int par1, int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }

    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }

    public static class ItemDecor extends ItemBlock {

        public ItemDecor(int par1) {
            super(par1);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public Icon getIconFromDamage(int par1) {
            return ModBlocks.decor.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int par1) {
            return par1;
        }

        @Override
        public String getItemDisplayName(ItemStack par1ItemStack) {
            String name = super.getItemDisplayName(par1ItemStack);
            switch (par1ItemStack.getItemDamage()) {
            case 1:
                ItemStack red = new ItemStack(cloth, 1, 14);
                return red.getDisplayName().substring(0, red.getDisplayName().length() - 4) + name;
            case 2:
                ItemStack green = new ItemStack(cloth, 1, 13);
                return green.getDisplayName().substring(0, green.getDisplayName().length() - 4) + name;
            case 3:
                ItemStack blue = new ItemStack(cloth, 1, 11);
                return blue.getDisplayName().substring(0, blue.getDisplayName().length() - 4) + name;
            default:
                return name;
            }
        }
    }
}
