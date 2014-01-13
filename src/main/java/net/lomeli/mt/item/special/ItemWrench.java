package net.lomeli.mt.item.special;

import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.item.CustomBookUtil;

import net.lomeli.mt.api.tile.IMTTile;
import net.lomeli.mt.core.Config;
import net.lomeli.mt.item.ItemMT;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.tools.IToolWrench;
import buildcraft.api.transport.IPipe;
import buildcraft.api.transport.IPipeTile;

import ic2.api.item.Items;
import ic2.api.tile.IWrenchable;

public class ItemWrench extends ItemMT implements IToolWrench {

    public ItemWrench(int id) {
        super(id, "wrench");
        this.setMaxStackSize(1);
        if (Config.date)
            this.itemTexture = "x" + this.itemTexture;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity) {
        if (!player.worldObj.isRemote) {

        }
        return false;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        ItemStack bookManual = CustomBookUtil.createNewBook(
                "Lomeli12",
                StatCollector.translateToLocal("book.magicthings:wrench"),
                new String[] { (StatCollector.translateToLocal(Strings.wrenchPage1ln1) + "\n\n" + StatCollector.translateToLocal(Strings.wrenchPage1ln2)),
                        StatCollector.translateToLocal(Strings.wrenchPage2), StatCollector.translateToLocal(Strings.wrenchPage3) });
        if (EntityUtil.transformEntityItem(world, x, y, z, player, book.itemID, bookManual, itemstack, true)) {
            double d3 = 0.2199999988079071D;
            double d4 = 0.27000001072883606D;
            world.spawnParticle("smoke", x + d4, y + d3, z, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("smoke", x + d4, y + d3, z, 0.0D, 0.0D, 0.0D);
        }
        return false;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        int id = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);

        Block block = Block.blocksList[id];

        if (block == null)
            return false;
        world.markBlockForUpdate(x, y, z);
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                try {
                    if (tile != null) {
                        if (block.blockID == Block.chest.blockID || block.blockID == Block.chestTrapped.blockID
                                || Class.forName("com.pahimar.ee3.block.BlockAlchemicalChest").isInstance(block)
                                || Class.forName("cpw.mods.ironchest.BlockIronChest").isInstance(block) || tile instanceof TileEntityFurnace) {
                            block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side).getOpposite());
                            player.closeScreen();
                            player.swingItem();
                            return true;
                        }
                    }
                } catch (ClassNotFoundException e) {
                }
                if (tile != null) {
                    if (tile instanceof IWrenchable) {
                        if (((IWrenchable) tile).wrenchCanSetFacing(player, side)) {
                            ((IWrenchable) tile).setFacing((short) side);
                            player.closeScreen();
                            player.swingItem();
                            world.markBlockForUpdate(x, y, z);
                            return true;
                        }
                    }
                }
            } else {
                if (tile != null) {
                    try {
                        if (tile instanceof IMTTile || tile instanceof IPowerReceptor || tile instanceof IWrenchable || tile instanceof TileEntityHopper
                                || Class.forName("buildcraft.core.IMachine").isInstance(tile) || Class.forName("buildcraft.factory.TileTank").isInstance(tile)) {
                            if (tile instanceof IWrenchable) {
                                EntityItem item = null;
                                if (Class.forName("ic2.core.block.wiring.TileEntityCable").isInstance(tile))
                                    item = new EntityItem(world, x, y, z, new ItemStack(Items.getItem("copperCableItem").itemID, 1, meta));
                                else
                                    item = new EntityItem(world, x, y, z, ((IWrenchable) tile).getWrenchDrop(player));
                                if (item != null) {
                                    world.spawnEntityInWorld(item);
                                    world.setBlockToAir(x, y, z);
                                }
                            } else {
                                if (tile instanceof IPipe || tile instanceof IPipeTile)
                                    id = Block.blocksList[world.getBlockId(x, y, z)].idPicked(world, x, y, z);
                                ItemStack itemBlock = new ItemStack(id, 1, meta);
                                EntityItem item = new EntityItem(world, x, y, z, itemBlock);
                                world.spawnEntityInWorld(item);
                                world.setBlockToAir(x, y, z);
                            }
                            player.swingItem();
                            return true;
                        }
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canWrench(EntityPlayer player, int x, int y, int z) {
        return true;
    }

    @Override
    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
        player.swingItem();
    }

    @Override
    public boolean shouldPassSneakingClickToBlock(World par2World, int par4, int par5, int par6) {
        return true;
    }

}
