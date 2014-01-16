package net.lomeli.mt.block.func;

import java.util.Random;

import net.lomeli.lomlib.item.ItemUtil;

import net.lomeli.mt.MThings;
import net.lomeli.mt.block.BlockMT;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.tile.TileEntityCompactCobGen;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockCobbleGen extends BlockMT implements ITileEntityProvider {

    private Icon[] iconArray;

    public BlockCobbleGen(int id) {
        super(id, Material.iron, "cobGen/cobGen_");
        this.setHardness(0.5F);
        this.setResistance(20F);
        this.setStepSound(soundMetalFootstep);
        this.setCreativeTab(MThings.modtab);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[5];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public Icon getIcon(int side, int meta) {
        int active = meta & 0b0011;
        if (side == 1)
            return iconArray[4];
        else if (side == 0)
            return iconArray[3];
        else {
            side -= 2;
            int frontSide = (meta & 0b1100) >> 2;
            if (frontSide == side)
                return iconArray[active];
        }
        return iconArray[2];
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if (!world.isRemote) {
            TileEntityCompactCobGen tile = (TileEntityCompactCobGen) world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                if (player != null) {
                    ItemStack current = player.inventory.getCurrentItem();
                    if (!player.isSneaking()) {
                        if (current != null) {
                            if (current.getUnlocalizedName().equals(Block.lever.getUnlocalizedName()))
                                tile.flipRedstoneSettings();
                            else if (current.getUnlocalizedName().equals(Item.bucketEmpty.getUnlocalizedName()))
                                tile.produceObsidian(player);
                            else if (current.getUnlocalizedName().equals(Item.stick.getUnlocalizedName()))
                                tile.giveCobble(player, 1);
                            else {
                                FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(current);
                                if (liquid != null) {
                                    if (liquid.getFluid().equals(FluidRegistry.LAVA) || liquid.getFluid().equals(FluidRegistry.WATER)) {
                                        int qty = tile.fill(null, liquid, true);
                                        if (qty != 0 && !player.capabilities.isCreativeMode)
                                            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtil.consumeItem(current));
                                    }
                                } else
                                    return false;
                            }
                        } else
                            tile.giveCobble(player, 64);
                        return true;
                    } else {
                        tile.produceStone(player);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityCompactCobGen();
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    private void setDefaultDirection(World par1World, int par2, int par3, int par4) {
        if (!par1World.isRemote) {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
                b0 = 3;

            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
                b0 = 2;

            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
                b0 = 5;

            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
                b0 = 4;

            int meta = par1World.getBlockMetadata(par2, par3, par4);
            meta &= ~0b1100;
            meta |= (b0 - 2) << 2;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, meta, 2);
        }
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        int meta = par1World.getBlockMetadata(par2, par3, par4);
        meta &= ~0b1100;
        if (l == 0)
            meta |= (2 - 2) << 2;

        if (l == 1)
            meta |= (5 - 2) << 2;

        if (l == 2)
            meta |= (3 - 2) << 2;

        if (l == 3)
            meta |= (4 - 2) << 2;

        par1World.setBlockMetadataWithNotify(par2, par3, par4, meta, 2);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
        world.removeBlockTileEntity(x, y, z);
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

    @Override
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }
}
