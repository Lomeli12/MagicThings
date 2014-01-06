package net.lomeli.mt.block;

import java.util.Random;

import net.lomeli.lomlib.render.IconConnected;
import net.lomeli.lomlib.render.IconConnectedReverse;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFancyGlass extends BlockGlass {

    @SideOnly(Side.CLIENT)
    private Icon basicIcon;
    @SideOnly(Side.CLIENT)
    private IconConnected connectedIcon;

    public BlockFancyGlass(int par1) {
        super(par1, Material.glass, true);
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":fancyGlass");
        this.setCreativeTab(MThings.modtab);
        this.setHardness(0.5F);
        this.setResistance(500F);
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }

    @Override
    public int getRenderType() {
        return BlockInfo.glassRenderID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return connectedIcon != null ? new IconConnectedReverse(connectedIcon) : basicIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side){
        return (!world.isBlockNormalCube(x, y, z)) && (world.getBlockId(x, y, z) != this.blockID) ? true : false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) {
        basicIcon = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":Connected/fancyGlass_corners");
        connectedIcon = new IconConnected(iconRegister, "Connected/fancyGlass", Strings.MOD_ID.toLowerCase());
    }
}
