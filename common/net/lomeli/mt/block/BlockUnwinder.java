package net.lomeli.mt.block;

import java.util.Random;

import net.lomeli.mt.MThings;
import net.lomeli.mt.api.item.ICrank;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.tile.TileEntityUnwinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockUnwinder extends BlockContainer {

    public BlockUnwinder(int id) {
        super(id, Material.wood);
        this.setHardness(0.5F);
        this.setResistance(20F);
        this.setStepSound(soundMetalFootstep);
        this.setCreativeTab(MThings.modtab);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        blockIcon = iconRegister.registerIcon(Strings.MOD_ID + ":unwind");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if(player.isSneaking())
            return false;
        else {
            if(!world.isRemote) {
                TileEntityUnwinder tile = (TileEntityUnwinder) world.getBlockTileEntity(x, y, z);
                if(tile != null) {
                    if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ICrank) {
                        if(((ICrank) player.getCurrentEquippedItem().getItem()).takesDamage() && tile.hasWork())
                            player.getCurrentEquippedItem().damageItem(1, player);
                    }else
                        player.openGui(MThings.instance, 99, world, x, y, z);
                }
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityUnwinder();
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

        if(!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for(int i = 0; i < inventory.getSizeInventory(); i++) {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if(itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID,
                        itemStack.stackSize, itemStack.getItemDamage()));

                if(itemStack.hasTagCompound()) {
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

    @Override
    public Block setUnlocalizedName(String str) {
        super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + str);
        return this;
    }
}
