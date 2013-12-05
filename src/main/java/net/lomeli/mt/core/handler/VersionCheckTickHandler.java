package net.lomeli.mt.core.handler;

import java.util.EnumSet;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import net.lomeli.lomlib.util.ToolTipUtil;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

public class VersionCheckTickHandler implements ITickHandler {

    private static boolean initialized = false;

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        if (!MThings.updater.isUpdate()) {
            if (!initialized) {
                for (TickType tickType : type) {
                    if (tickType == TickType.CLIENT) {
                        if (FMLClientHandler.instance().getClient().currentScreen == null) {
                            initialized = true;
                            FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(
                                    ToolTipUtil.BLUE + "[" + ToolTipUtil.ORANGE + Strings.MOD_NAME + ToolTipUtil.BLUE + "]: There is a new version available at "
                                            + MThings.updater.getDownloadURL());
                        }
                    }
                }
            }
        }
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return Strings.MOD_NAME + ": " + this.getClass().getSimpleName();
    }

}
