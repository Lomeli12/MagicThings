package net.lomeli.mt.block;

import java.util.Random;

import net.lomeli.lomlib.entity.EntityUtil;

import net.lomeli.mt.api.MTRecipeHandlers;
import net.lomeli.mt.potion.PotionInfection;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockInfected extends BlockMT {
    public BlockInfected(int id) {
        super(id, Material.sand, "blank");
        this.setCreativeTab(null);
        this.setResistance(100);
        this.setTickRandomly(true);
        this.setBlockUnbreakable();
        this.drop = 0;
        float f = 0.2F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
        blockFireSpreadSpeed[id] = 5;
        blockFlammability[id] = 20;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        super.updateTick(world, x, y, z, rand);
        if(!this.canPlaceBlockAt(world, x, y, z)) {
            world.setBlockToAir(x, y, z);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        int id = world.getBlockId(x, y - 1, z);
        int meta = world.getBlockMetadata(x, y - 1, z);
        return MTRecipeHandlers.infectedWhitelist.getBlockList().contains(id) == true ? true
                : (MTRecipeHandlers.infectedWhitelist.getMetaBlockList().containsKey(id) == true && MTRecipeHandlers.infectedWhitelist
                        .getMetaBlockList().get(id) == meta) ? true : false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public int getRenderType() {
        return 1;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("minecraft:mushroom_brown");
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if(world != null) {
            double d3 = 0.2199999988079071D;
            double d4 = 0.27000001072883606D;
            if(rand.nextInt(50) == 0)
                world.spawnParticle("smoke", x - d4, y + d3, z, 0.0D, 0.0D, 0.0D);
        }

    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity par5Entity) {
        if(!this.canPlaceBlockAt(world, x, y, z)) {
            world.destroyBlock(x, y, z, false);
        }
        if(par5Entity != null && par5Entity instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) par5Entity;
            if(!EntityUtil.isUndeadEntity(entity)) {
                entity.addPotionEffect(new PotionEffect(PotionInfection.infect.id, 1000));
                world.setBlockToAir(x, y, z);
            }
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        float f = 0.7F;
        return AxisAlignedBB.getAABBPool().getAABB((double) ((float) par2 + f), (double) par3, (double) ((float) par4 + f),
                (double) ((float) (par2 + 1) - f), (double) ((float) (par3 + 1) - f), (double) ((float) (par4 + 1) - f));
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        float f = 1F;
        return AxisAlignedBB.getAABBPool().getAABB((double) ((float) par2 + f), (double) par3, (double) ((float) par4 + f),
                (double) ((float) (par2 + 1) - f), (double) (par3 + 1), (double) ((float) (par4 + 1) - f));
    }
}
