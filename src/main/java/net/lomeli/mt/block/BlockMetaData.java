package net.lomeli.mt.block;

import java.util.List;

import net.lomeli.mt.lib.Strings;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMetaData extends BlockMT {

    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    protected int n;

    public BlockMetaData(int id, Material material, String texture, int number) {
        super(id, material, texture);
        this.n = number;
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        iconArray = new Icon[n];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < n; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public Icon getIcon(int par1, int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return this.damageDropped(world.getBlockMetadata(x, y, z));
    }
}
