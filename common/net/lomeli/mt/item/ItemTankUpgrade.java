package net.lomeli.mt.item;

import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.tile.TileEntityClearTank;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemTankUpgrade extends ItemMT {

    public ItemTankUpgrade(int id) {
        super(id, "tankUpgrade");
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
            float hitY, float hitZ) {
        if(itemstack != null && player != null && world != null && !world.isRemote && !player.isSneaking()
                && ModLoaded.isModInstalled("BuildCraft|Factory")) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if(tile != null) {
                try {
                    if(Class.forName("buildcraft.factory.TileTank").isInstance(tile)) {
                        FluidStack tank = ((IFluidHandler) tile).getTankInfo(null)[0].fluid;
                        world.setBlock(x, y, z, ModBlocks.clearTank.blockID);
                        TileEntityClearTank tileTank = (TileEntityClearTank) world.getBlockTileEntity(x, y, z);
                        if(tank != null) {
                            if(tank.amount > 16000)
                                tank.amount = 16000;
                            tileTank.fill(null, tank, true);
                        }
                        --itemstack.stackSize;
                        return true;
                    }
                }catch(ClassNotFoundException e) {
                }
            }
        }
        return false;
    }

}
