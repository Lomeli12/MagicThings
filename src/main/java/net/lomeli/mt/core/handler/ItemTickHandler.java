package net.lomeli.mt.core.handler;

import java.util.EnumSet;

import net.lomeli.lomlib.util.ModLoaded;

import net.minecraft.entity.player.EntityPlayer;

import net.lomeli.mt.addon.GraviSuiteAddon;
import net.lomeli.mt.addon.MorphAddon;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.lib.Strings;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemTickHandler implements ITickHandler {

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        for (TickType tickType : type) {
            if (tickType == TickType.PLAYER) {
                EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
                if (!player.inventory.hasItem(ModItems.flyingRing.itemID)) {
                    if (!player.capabilities.isCreativeMode) {
                        if (ModLoaded.isModInstalled("Morph")) {
                            MorphAddon.flying(player);
                        } else if (ModLoaded.isModInstalled("GraviSuite")) {
                            GraviSuiteAddon.flying(player);
                        } else {
                            player.capabilities.isFlying = false;
                            player.capabilities.allowFlying = false;
                            player.stepHeight = 0.5F;
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
