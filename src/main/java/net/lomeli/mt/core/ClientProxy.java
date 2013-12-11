package net.lomeli.mt.core;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import net.lomeli.mt.client.render.RenderCable;
import net.lomeli.mt.client.render.RenderTank;
import net.lomeli.mt.core.handler.FlyingTickHandler;
import net.lomeli.mt.core.handler.GuiHandler;
import net.lomeli.mt.core.handler.SoundHandler;
import net.lomeli.mt.core.handler.VersionCheckTickHandler;
import net.lomeli.mt.entity.EntityClicker;
import net.lomeli.mt.entity.render.RenderClicker;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.tile.TileEntityClearTank;
import net.lomeli.mt.tile.energy.TileEntityCable;

public class ClientProxy extends CommonProxy {

    public static int renderPass;

    @Override
    public void registerTickHandlers() {
        TickRegistry.registerTickHandler(new FlyingTickHandler(), Side.CLIENT);
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

        RenderingRegistry.registerEntityRenderingHandler(EntityClicker.class, new RenderClicker());
        //RenderingRegistry.registerBlockHandler(BlockInfo.decorRenderID, new RenderDecor());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityClearTank.class, new RenderTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCable.class, new RenderCable());
        
        //MinecraftForgeClient.registerItemRenderer(ModBlocks.decor.blockID, new RenderDecor());
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
        
        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
    }
}
