package net.lomeli.mt.block.func;

import net.lomeli.lomlib.item.ItemUtil;

import net.lomeli.mt.block.BlockMT;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.tile.TileClearTank;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class BlockClearTank extends BlockMT implements ITileEntityProvider {
    private Icon[] iconArray;

    public BlockClearTank(int id) {
        super(id, Material.glass, "tank/tank_");
        setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 1F, 0.875F);
        setHardness(0.5F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        TileClearTank tile = (TileClearTank) world.getBlockTileEntity(x, y, z);
        if (tile != null) {
            if (!player.isSneaking()) {
                ItemStack item = player.getCurrentEquippedItem();
                if (item != null) {
                    FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(item);
                    if (liquid != null) {
                        int qty = tile.fill(ForgeDirection.UP, liquid, true);
                        if (qty != 0 && !player.capabilities.isCreativeMode) {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtil.consumeItem(item));
                        }
                        return true;
                    } else {
                        FluidStack available = tile.getTankInfo(null)[0].fluid;
                        if (available != null) {
                            ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, item);
                            liquid = FluidContainerRegistry.getFluidForFilledItem(filled);

                            if (liquid != null) {
                                if (!player.capabilities.isCreativeMode) {
                                    if (item.stackSize > 1) {
                                        if (!player.inventory.addItemStackToInventory(filled))
                                            return false;
                                        else
                                            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtil.consumeItem(item));
                                    } else {
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtil.consumeItem(item));
                                        player.inventory.setInventorySlotContents(player.inventory.currentItem, filled);
                                    }
                                }
                                tile.drain(ForgeDirection.UNKNOWN, liquid.amount, true);
                                return true;
                            }
                        }
                    }
                }
                world.markBlockForUpdate(x, y, z);
                world.markBlockForRenderUpdate(x, y, z);
            }
        }
        return false;
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[5];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public Icon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? iconArray[1] : (meta == 1 ? iconArray[2] : meta == 2 ? iconArray[3] : meta == 3 ? iconArray[4] : iconArray[0]);
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        if (side <= 1)
            return world.getBlockId(x, y, z) != blockID;
        return super.shouldSideBeRendered(world, x, y, z, side);
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
    public TileEntity createNewTileEntity(World world) {
        return new TileClearTank();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if (tile != null && tile instanceof TileClearTank) {
            FluidTankInfo tank = ((TileClearTank) tile).getTankInfo(null)[0];
            if (tank != null && tank.fluid != null && tank.fluid.getFluid() != null)
                return tank.fluid.getFluid().getLuminosity();
        }
        return super.getLightValue(world, x, y, z);
    }
}
