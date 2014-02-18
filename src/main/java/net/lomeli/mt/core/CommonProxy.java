package net.lomeli.mt.core;

import net.lomeli.mt.core.handler.EntityLivingHandler;
import net.lomeli.mt.core.handler.GuiHandler;
import net.lomeli.mt.core.handler.PlayerEntityInteractionHandler;
import net.lomeli.mt.core.handler.PlayerItemHandler;
import net.lomeli.mt.core.world.WorldMTGen;
import net.lomeli.mt.tile.TileAquaticManipulator;
import net.lomeli.mt.tile.TileClearTank;
import net.lomeli.mt.tile.TileCompactCobGen;
import net.lomeli.mt.tile.TileMagmaFurnace;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void registerTickHandlers() {
    }

    public void registerTileEntites() {
        String tile = "net.lomeli.mt.tile.";
        GameRegistry.registerTileEntity(TileCompactCobGen.class, tile + "CobGenCompact");
        GameRegistry.registerTileEntity(TileAquaticManipulator.class, tile + "AquaManip");
        GameRegistry.registerTileEntity(TileClearTank.class, tile + "ClearTank");
        GameRegistry.registerTileEntity(TileMagmaFurnace.class, tile + "MagmaFurnace");
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

        GameRegistry.registerWorldGenerator(new WorldMTGen());
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
    }
}
