package net.lomeli.mt.core;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import net.lomeli.mt.client.render.RenderTank;
import net.lomeli.mt.core.handler.SoundHandler;
import net.lomeli.mt.core.handler.VersionCheckTickHandler;
import net.lomeli.mt.entity.EntityClicker;
import net.lomeli.mt.entity.render.RenderClicker;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.tile.TileEntityClearTank;

public class ClientProxy extends CommonProxy {

    public static int renderPass;

    @Override
    public void registerTickHandlers() {
        TickRegistry.registerTickHandler(new VersionCheckTickHandler(), Side.CLIENT);
    }

    @Override
    public void registerTileEntites() {
        super.registerTileEntites();
    }

    @Override
    public void registerRenders() {
        BlockInfo.clearTankRenderID = RenderingRegistry.getNextAvailableRenderId();

        RenderingRegistry.registerEntityRenderingHandler(EntityClicker.class, new RenderClicker());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityClearTank.class, new RenderTank());
    }

    @Override
    public void registerSounds() {
        SoundHandler.getInstance();
    }

    @Override
    public void registerIcons() {
    }
}
