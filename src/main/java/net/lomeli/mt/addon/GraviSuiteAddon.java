package net.lomeli.mt.addon;

import net.lomeli.lomlib.util.ModLoaded;

import net.minecraft.entity.player.EntityPlayer;

public class GraviSuiteAddon {
    public static boolean isFlyModOn(EntityPlayer player) {
        if (ModLoaded.isModInstalled("GraviSuite")) {
            try {
                return (Boolean) Class.forName("gravisuite.ServerTickHandler").getDeclaredMethod("checkFlyActiveByMode", EntityPlayer.class).invoke(null, player);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static void flying(EntityPlayer player) {
        if (!isFlyModOn(player)) {
            player.capabilities.isFlying = false;
            player.capabilities.allowFlying = false;
            player.stepHeight = 0.5F;
        }
    }
}
