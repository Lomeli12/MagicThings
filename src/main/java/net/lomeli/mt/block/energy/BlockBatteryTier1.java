package net.lomeli.mt.block.energy;

import net.lomeli.lomlib.block.BlockUtil;

import net.lomeli.mt.block.BlockMT;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.tile.energy.TileEntityBattery;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBatteryTier1 extends BlockMT implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private Icon in, out;

    public BlockBatteryTier1(int id) {
        super(id, Material.wood, "batTier1");
        this.setResistance(20F);
        this.setHardness(4.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("BatTier1");
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        in = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + "/batTierIn");
        out = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + "/batTierOut");
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return (side == meta) ? out : in;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityBattery();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        TileEntityBattery tile = (TileEntityBattery) world.getBlockTileEntity(x, y, z);

        if (tile != null) {
            if (tile.getEnergy() > ((tile.getMaxEnergy() * 3) / 4)) {
                // TODO
            }
        }
        super.breakBlock(world, x, y, z, id, meta);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
        world.setBlockMetadataWithNotify(x, y, z, BlockUtil.determineOrientation(world, x, y, z, entity), 2);
    }
}
