package net.lomeli.mt.block.energy;

import java.util.Random;

import net.lomeli.mt.MThings;
import net.lomeli.mt.block.BlockMT;
import net.lomeli.mt.tile.energy.TileEntityCoalGen;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCoalGenerator extends BlockMT implements ITileEntityProvider {

    public BlockCoalGenerator(int id) {
        super(id, Material.iron, "coal_");
        this.setResistance(20F);
        this.setHardness(4.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("CoalGen");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if (player.isSneaking())
            return false;
        else {
            if (!world.isRemote) {
                TileEntityCoalGen tile = (TileEntityCoalGen) world.getBlockTileEntity(x, y, z);
                if (tile != null)
                    player.openGui(MThings.instance, 99, world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityCoalGen();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }

    private Random rand = new Random();

    private void dropInventory(World world, int x, int y, int z) {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
}
