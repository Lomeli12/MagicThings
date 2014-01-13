package net.lomeli.mt.block;

import java.util.Random;

import net.lomeli.lomlib.client.render.IconConnected;
import net.lomeli.lomlib.client.render.IconConnectedReverse;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFancyGlass extends BlockMetaData {

    @SideOnly(Side.CLIENT)
    private Icon basicIcon;
    @SideOnly(Side.CLIENT)
    private IconConnected connectedIcon;

    public BlockFancyGlass(int par1) {
        super(par1, Material.glass, "fancyGlass", 2);
        this.setUnlocalizedName("fancyGlass");
        this.setCreativeTab(MThings.modtab);
        this.setHardness(0.5F);
        this.setResistance(500F);
        this.setLightOpacity(0);
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
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == 1 ? 15 : 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta) {
        return connectedIcon != null ? new IconConnectedReverse(connectedIcon) : basicIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
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

    @Override
    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    public static class ItemGlassBlock extends ItemBlock {

        public ItemGlassBlock(int par1) {
            super(par1);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @Override
        public Icon getIconFromDamage(int par1) {
            return ModBlocks.glass.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int meta) {
            return meta;
        }

        @Override
        public String getItemDisplayName(ItemStack stack) {
            return StatCollector.translateToLocal(stack.getUnlocalizedName() + "." + stack.getItemDamage());
        }
    }
}
