package net.lomeli.mt.item.special;

import net.lomeli.lomlib.util.KeyBoardUtil;

import net.lomeli.mt.item.ItemMT;
import net.lomeli.mt.lib.Ints.KeyConfigs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class ItemFluidReader extends ItemMT {

    public ItemFluidReader(int id) {
        super(id, "liquidReader");
        this.setMaxStackSize(1);
    }

    private int tick;

    public void changeMode(EntityPlayer player, ItemStack stack) {
        int damage = stack.getItemDamage();
        if (damage >= 7) {
            stack.setItemDamage(0);
        } else
            stack.setItemDamage(damage + 1);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int par4, boolean par5) {
        if (entity != null && itemStack != null) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;

                if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().equals(itemStack)
                        && KeyBoardUtil.isKeyDown(KeyConfigs.KEY_LIQUID_READER_MODE_CHANGE)) {
                    changeMode(player, itemStack);
                    tick++;
                    if (tick > 1) {
                        tick = 0;
                        if (getDirection(itemStack) != null)
                            player.addChatMessage("[FluidReader]: " + getDirection(itemStack).name() + " Mode");
                        else
                            player.addChatMessage("[FluidReader]: Null Mode");
                    }
                }
            }
        }
    }

    private ForgeDirection[] directions = { null, ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.UP, ForgeDirection.DOWN,
            ForgeDirection.UNKNOWN };

    public ForgeDirection getDirection(ItemStack stack) {
        ForgeDirection dir = null;
        try {
            dir = directions[stack.getItemDamage()];
        } catch (IndexOutOfBoundsException e) {
        }
        return dir;
    }

    public void getInfoBaseOnMode(TileEntity tile, EntityPlayer player, ItemStack stack) {
        if (((IFluidHandler) tile).getTankInfo(getDirection(stack)) != null) {
            for (int i = 0; i < ((IFluidHandler) tile).getTankInfo(getDirection(stack)).length; i++) {
                try {
                    FluidTankInfo tankInfo = ((IFluidHandler) tile).getTankInfo(getDirection(stack))[i];
                    if (tankInfo != null && tankInfo.fluid.getFluid().getName() != null) {
                        int fluidAmount = tankInfo.fluid.amount, capacity = tankInfo.capacity;

                        player.addChatMessage(tankInfo.fluid.getFluid().getLocalizedName() + ": " + fluidAmount + "mB /" + capacity + "mB");
                    }
                } catch (NullPointerException e) {
                }
            }
        }
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (itemstack != null && player != null && world != null && !world.isRemote) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                if (tile instanceof IFluidHandler) {
                    getInfoBaseOnMode(tile, player, itemstack);
                }
            }
        }
        return false;
    }

}
