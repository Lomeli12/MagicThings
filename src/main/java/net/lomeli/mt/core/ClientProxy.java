package net.lomeli.mt.core;

import net.lomeli.mt.client.render.RenderStamaticGlass;
import net.lomeli.mt.client.render.RenderTank;
import net.lomeli.mt.core.handler.ItemTickHandler;
import net.lomeli.mt.core.handler.SoundHandler;
import net.lomeli.mt.core.handler.VersionCheckTickHandler;
import net.lomeli.mt.entity.EntityClicker;
import net.lomeli.mt.entity.render.RenderClicker;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.tile.TileEntityClearTank;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

    public static int renderPass;

    @Override
    public void registerTickHandlers() {
        TickRegistry.registerTickHandler(new ItemTickHandler(), Side.CLIENT);
        TickRegistry.registerTickHandler(new VersionCheckTickHandler(), Side.CLIENT);
    }

    @Override
    public void registerTileEntites() {
        super.registerTileEntites();
    }

    @Override
    public void registerRenders() {
        BlockInfo.clearTankRenderID = RenderingRegistry.getNextAvailableRenderId();
        BlockInfo.decorRenderID = RenderingRegistry.getNextAvailableRenderId();
        BlockInfo.glassRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerEntityRenderingHandler(EntityClicker.class, new RenderClicker());
        RenderingRegistry.registerBlockHandler(new RenderStamaticGlass().setRenderID(BlockInfo.glassRenderID));

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityClearTank.class, new RenderTank());
    }

    @Override
    public void registerSounds() {
        SoundHandler.getInstance();
    }

    @Override
    public void registerIcons() {
    }

    @Override
    public void registerEvents() {
        super.registerEvents();

        MinecraftForge.EVENT_BUS.register(new SoundHandler());

    }
}
