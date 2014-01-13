package net.lomeli.mt.addon;

import net.lomeli.lomlib.util.ModLoaded;

import net.minecraft.item.ItemStack;

import net.lomeli.mt.item.ModItems;

import cpw.mods.fml.common.event.FMLInterModComms;

import com.pahimar.ee3.api.StackValueMapping;
import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class EE3Addon {
    public static void loadAddon() {
        if (ModLoaded.isModInstalled("EE3")) {
            FMLInterModComms.sendMessage("EE3", "emc-assign-value-post", new StackValueMapping(new ItemStack(ModItems.ingots, 1, 0), new EmcValue(165, EmcType.DEFAULT)).toJson());
        }
    }
}
