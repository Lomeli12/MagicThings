package net.lomeli.mt.block.func;

import java.util.Random;

import net.lomeli.lomlib.block.BlockUtil;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.IPlantable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFertileBush extends Block {

    public BlockFertileBush(int id) {
        super(id, Material.leaves);
        this.setHardness(0.5F);
        this.setResistance(1F);
        this.setStepSound(soundGrassFootstep);
        this.setTickRandomly(true);
        this.setCreativeTab(MThings.modtab);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        blockIcon = Block.leaves.getBlockTextureFromSide(0);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        this.growCrops(world, x, y, z, rand, BlockInfo.fertileBushRange);
    }

    public void growCrops(World world, int x, int y, int z, Random rand, int range) {
        for (int xi = (x - range); xi < (x + range); xi++) {
            for (int zi = (z - range); zi < (z + range); zi++) {
                Block block = BlockUtil.getBlock(world, xi, y, zi);
                if (block != null && block.blockID != this.blockID && block instanceof IPlantable) {
                    block.updateTick(world, xi, y, zi, rand);
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        double d0 = 0.5D;
        double d1 = 1.0D;
        return ColorizerFoliage.getFoliageColor(d0, d1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int par1) {
        return (par1 & 3) == 1 ? ColorizerFoliage.getFoliageColorPine() : ((par1 & 3) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

        if ((l & 3) == 1) {
            return ColorizerFoliage.getFoliageColorPine();
        } else if ((l & 3) == 2) {
            return ColorizerFoliage.getFoliageColorBirch();
        } else {
            int i1 = 0;
            int j1 = 0;
            int k1 = 0;

            for (int l1 = -1; l1 <= 1; ++l1) {
                for (int i2 = -1; i2 <= 1; ++i2) {
                    int j2 = par1IBlockAccess.getBiomeGenForCoords(par2 + i2, par4 + l1).getBiomeFoliageColor();
                    i1 += (j2 & 16711680) >> 16;
                    j1 += (j2 & 65280) >> 8;
                    k1 += j2 & 255;
                }
            }

            return (i1 / 9 & 255) << 16 | (j1 / 9 & 255) << 8 | k1 / 9 & 255;
        }
    }

    @Override
    public Block setUnlocalizedName(String str) {
        super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + str);
        return this;
    }

}
