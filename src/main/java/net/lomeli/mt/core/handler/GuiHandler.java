package net.lomeli.mt.core.handler;

import cpw.mods.fml.common.network.IGuiHandler;

import net.lomeli.mt.client.gui.GuiCondenseBag;
import net.lomeli.mt.client.gui.GuiUnwinder;
import net.lomeli.mt.client.gui.GuiMagmaFurnace;
import net.lomeli.mt.client.gui.energy.GuiCoalGen;
import net.lomeli.mt.inventory.ContainerCondenseBag;
import net.lomeli.mt.inventory.InventoryCondenseBag;
import net.lomeli.mt.inventory.ContainerMagmaFurnace;
import net.lomeli.mt.inventory.ContainerPortaCraft;
import net.lomeli.mt.inventory.ContainerUnwinder;
import net.lomeli.mt.inventory.energy.ContainerCoalGen;
import net.lomeli.mt.tile.TileEntityMagmaFurnace;
import net.lomeli.mt.tile.TileEntityUnwinder;
import net.lomeli.mt.tile.energy.TileEntityCoalGen;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 99) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                if (tile instanceof TileEntityMagmaFurnace)
                    return new ContainerMagmaFurnace(player.inventory, (TileEntityMagmaFurnace) tile);
                else if (tile instanceof TileEntityUnwinder)
                    return new ContainerUnwinder(player.inventory, (TileEntityUnwinder) tile);
                else if (tile instanceof TileEntityCoalGen)
                    return new ContainerCoalGen(player.inventory, (TileEntityCoalGen) tile);
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
                if (tile instanceof TileEntityMagmaFurnace)
                    return new GuiMagmaFurnace(player.inventory, (TileEntityMagmaFurnace) tile);
                else if (tile instanceof TileEntityUnwinder)
                    return new GuiUnwinder(player.inventory, (TileEntityUnwinder) tile);
                else if (tile instanceof TileEntityCoalGen)
                    return new GuiCoalGen(player.inventory, (TileEntityCoalGen) tile);
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
