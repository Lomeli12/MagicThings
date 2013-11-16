package net.lomeli.mt.item;

import net.lomeli.mt.api.item.ICrank;
import net.lomeli.mt.api.tile.ICrankable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemCrank extends ItemMT implements ICrank {

    public ItemCrank(int id, String Texture) {
        super(id, Texture);
        this.setMaxDamage(150);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
            float hitY, float hitZ) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(!world.isRemote && tile != null && stack != null) {
            if(tile instanceof ICrankable) {
                player.swingItem();
                ((ICrankable) tile).incrementStep();
                if(takesDamage())
                    stack.damageItem(1, player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean takesDamage() {
        return true;
    }

}
