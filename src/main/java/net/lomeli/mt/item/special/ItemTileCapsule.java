package net.lomeli.mt.item.special;

import java.util.List;

import net.lomeli.lomlib.item.NBTUtil;
import net.lomeli.lomlib.util.ToolTipUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.mt.item.ItemMT;

public class ItemTileCapsule extends ItemMT {

    public ItemTileCapsule(int id) {
        super(id, "tileCapsule");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
        this.setUnlocalizedName("tileCapsule");
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        int id = world.getBlockId(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (!world.isRemote) {
            if (stack.getItemDamage() == 0) {
                if (tile != null) {
                    player.closeScreen();
                    player.swingItem();
                    if (stack.getTagCompound() == null)
                        stack.stackTagCompound = new NBTTagCompound();
                    tile.writeToNBT(stack.stackTagCompound);
                    stack.setItemDamage(id);
                    NBTUtil.setInteger(stack, "capsuleMeta", meta);
                    if (tile instanceof IInventory) {
                        for (int i = 0; i < ((IInventory) tile).getSizeInventory(); i++) {
                            ((IInventory) tile).setInventorySlotContents(i, null);
                        }
                    }
                    world.setBlockToAir(x, y, z);
                    return true;
                }
            } else {
                int newID = stack.getItemDamage();
                int newMeta = NBTUtil.getInt(stack, "capsuleMeta");
                if (world.isAirBlock(x, y + 1, z)) {
                    world.setBlock(x, y + 1, z, newID, newMeta, 2);
                    world.setBlockMetadataWithNotify(x, y + 1, z, newMeta, 3);
                    world.markBlockForUpdate(x, y + 1, z);
                    TileEntity newTile = world.getBlockTileEntity(x, y + 1, z);
                    if (newTile != null) {
                        newTile.readFromNBT(stack.stackTagCompound);
                        world.setBlockTileEntity(x, y + 1, z, newTile);
                        world.markBlockForUpdate(x, y + 1, z);
                        world.markBlockForRenderUpdate(x, y + 1, z);
                        stack.stackSize--;
                    }
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if (itemStack != null && itemStack.getItemDamage() != 0) {
            ItemStack possibleItem = new ItemStack(itemStack.getItemDamage(), 1, NBTUtil.getInt(itemStack, "capsuleMeta"));
            infoList.add(ToolTipUtil.toolTipInfo(ToolTipUtil.YELLOW, "Contains a " + possibleItem.getDisplayName()));
        }
    }

}
