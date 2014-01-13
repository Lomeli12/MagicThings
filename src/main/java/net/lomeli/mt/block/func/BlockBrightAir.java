package net.lomeli.mt.block.func;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.mt.block.BlockMT;
import net.lomeli.mt.block.ModBlocks;

public class BlockBrightAir extends BlockMT {

    public BlockBrightAir(int id) {
        super(id, Material.air, "blank");
        this.setLightValue(15F);
        this.setLightOpacity(0);
        this.setCreativeTab(null);
        this.setLightOpacity(0);
        this.setBlockBounds(0F, 0F, 0F, 0F, 0F, 0F);
    }

    @Override
    public int idPicked(World par1World, int par2, int par3, int par4) {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isAirBlock(World world, int x, int y, int z) {
        return true;
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
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    public static class ItemAirBlock extends ItemBlock {

        public ItemAirBlock(int par1) {
            super(par1);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public Icon getIconFromDamage(int par1) {
            return ModBlocks.air.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int meta) {
            return meta;
        }
    }

}
