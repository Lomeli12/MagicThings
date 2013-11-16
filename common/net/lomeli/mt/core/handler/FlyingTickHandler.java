package net.lomeli.mt.core.handler;

import java.util.EnumSet;

import net.lomeli.lomlib.util.ModLoaded;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import net.lomeli.mt.addon.GraviSuiteAddon;
import net.lomeli.mt.addon.MorphAddon;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.lib.Strings;

public class FlyingTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        for(TickType tickType : type) {
            if(tickType == TickType.PLAYER) {
                if(!FMLClientHandler.instance().getClient().thePlayer.inventory.hasItem(ModItems.flyingRing.itemID)) {
                    if(!FMLClientHandler.instance().getClient().thePlayer.capabilities.isCreativeMode) {
                        if(ModLoaded.isModInstalled("Morph")) {
                            MorphAddon.flying(FMLClientHandler.instance().getClient().thePlayer);
                        }else if(ModLoaded.isModInstalled("GraviSuite")) {
                            GraviSuiteAddon.flying(FMLClientHandler.instance().getClient().thePlayer);
                        }else {
                            FMLClientHandler.instance().getClient().thePlayer.capabilities.isFlying = false;
                            FMLClientHandler.instance().getClient().thePlayer.capabilities.allowFlying = false;
                            FMLClientHandler.instance().getClient().thePlayer.stepHeight = 0.5F;
                        }
                    }
                }
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.PLAYER);
    }

    @Override
    public String getLabel() {
        return Strings.MOD_NAME + ": " + this.getClass().getSimpleName();
    }
}
