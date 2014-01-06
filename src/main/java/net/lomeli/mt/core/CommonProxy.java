package net.lomeli.mt.core;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.mt.core.handler.CraftingHandler;
import net.lomeli.mt.core.handler.EntityLivingHandler;
import net.lomeli.mt.core.handler.GuiHandler;
import net.lomeli.mt.core.handler.PlayerEntityInteractionHandler;
import net.lomeli.mt.core.handler.PlayerItemHandler;
import net.lomeli.mt.tile.TileEntityAquaticManipulator;
import net.lomeli.mt.tile.TileEntityClearTank;
import net.lomeli.mt.tile.TileEntityCompactCobGen;
import net.lomeli.mt.tile.TileEntityMagmaFurnace;
import net.lomeli.mt.tile.TileEntityUnwinder;
import net.lomeli.mt.tile.energy.TileEntityBattery;
import net.lomeli.mt.tile.energy.TileEntityCable;
import net.lomeli.mt.tile.energy.TileEntityCoalGen;

public class CommonProxy {
    public void registerTickHandlers() {
    }

    public void registerTileEntites() {
        String tile = "net.lomeli.mt.tile.";
        GameRegistry.registerTileEntity(TileEntityCompactCobGen.class, tile + "CobGenCompact");
        GameRegistry.registerTileEntity(TileEntityAquaticManipulator.class, tile + "AquaManip");
        GameRegistry.registerTileEntity(TileEntityClearTank.class, tile + "ClearTank");
        GameRegistry.registerTileEntity(TileEntityMagmaFurnace.class, tile + "MagmaFurnace");
        GameRegistry.registerTileEntity(TileEntityUnwinder.class, tile + "Unwinder");
        GameRegistry.registerTileEntity(TileEntityCoalGen.class, tile + "CoalGen");
        GameRegistry.registerTileEntity(TileEntityCable.class, tile + "Cable");
        GameRegistry.registerTileEntity(TileEntityBattery.class, tile + "BatBox");
    }

    public void registerRenders() {
    }

    public void registerSounds() {
    }

    public void registerIcons() {
    }
    
    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new PlayerItemHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEntityInteractionHandler());
        MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());
        
        GameRegistry.registerCraftingHandler(new CraftingHandler());
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
    }
}
