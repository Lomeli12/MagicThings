package net.lomeli.mt.block.func;

import net.lomeli.mt.block.BlockMT;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.tile.TileAquaticManipulator;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockAquaticManipulator extends BlockMT implements ITileEntityProvider {

    private Icon[] iconArray;

    public BlockAquaticManipulator(int id) {
        super(id, Material.anvil, "aquaManip/aquaManip");
        this.setHardness(0.5F);
        this.setResistance(20F);
        this.setStepSound(soundMetalFootstep);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if (!world.isRemote) {
            TileAquaticManipulator tile = (TileAquaticManipulator) world.getBlockTileEntity(x, y, z);
            if (tile != null && player != null) {
                ItemStack current = player.getCurrentEquippedItem();
                if (!player.isSneaking()) {
                    if(current != null && current.getUnlocalizedName().equals(Block.lever.getUnlocalizedName())){
                        tile.flipRedstoneSettings();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[4];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return side == 0 ? iconArray[0] : side == 1 ? iconArray[2] : (meta == 0 ? iconArray[3] : iconArray[1]);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileAquaticManipulator();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

}
