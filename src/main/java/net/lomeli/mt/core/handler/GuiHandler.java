package net.lomeli.mt.core.handler;

import net.lomeli.mt.client.gui.GuiCondenseBag;
import net.lomeli.mt.client.gui.GuiMagmaFurnace;
import net.lomeli.mt.inventory.ContainerCondenseBag;
import net.lomeli.mt.inventory.ContainerMagmaFurnace;
import net.lomeli.mt.inventory.ContainerPortaCraft;
import net.lomeli.mt.inventory.InventoryCondenseBag;
import net.lomeli.mt.tile.TileMagmaFurnace;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 99) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                if (tile instanceof TileMagmaFurnace)
                    return new ContainerMagmaFurnace(player.inventory, (TileMagmaFurnace) tile);
            }
        } else if (ID == 1)
            return new ContainerPortaCraft(player.inventory, world, x, y, z);
        else if (ID == 2) {
            InventoryCondenseBag inventory = new InventoryCondenseBag(player.getCurrentEquippedItem(), player);
            return new ContainerCondenseBag(player.getCurrentEquippedItem(), inventory, player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 99) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                if (tile instanceof TileMagmaFurnace)
                    return new GuiMagmaFurnace(player.inventory, (TileMagmaFurnace) tile);
            }
        } else if (ID == 1)
            return new GuiCrafting(player.inventory, world, x, y, z);
        else if (ID == 2) {
            InventoryCondenseBag inventory = new InventoryCondenseBag(player.getCurrentEquippedItem(), player);
            return new GuiCondenseBag(player.getCurrentEquippedItem(), inventory, player.inventory);
        }
        return null;
    }

}
