package net.lomeli.mt.addon;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.item.ModItems;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.event.FMLInterModComms;

import com.pahimar.ee3.api.StackValueMapping;
import com.pahimar.ee3.emc.EmcValue;

public class EE3Addon {
    public static void loadAddon() {
        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 0), new EmcValue(165)));
        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 1), new EmcValue(240)));
        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 2), new EmcValue(257)));
        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 3), new EmcValue(4096)));
        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.material, 1, 0), new EmcValue(35)));
        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.material, 1, 1), new EmcValue(6595)));
        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.material, 1, 2), new EmcValue(39532)));

        sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[0], new EmcValue(137.142f)));
        sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[1], new EmcValue(145.142f)));
        sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[2], new EmcValue(145.142f)));
        sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[3], new EmcValue(145.142f)));

        sendPreValueAssignment(new StackValueMapping(ModBlocks.frames[0], new EmcValue(2353)));

        sendPreValueAssignment(new StackValueMapping(new ItemStack(ModBlocks.treatedWool, 1, OreDictionary.WILDCARD_VALUE), new EmcValue(78)));

    }

    public static void sendPreValueAssignment(StackValueMapping stackValueMapping) {
        if (stackValueMapping != null)
            FMLInterModComms.sendMessage("EE3", "emc-assign-value-post", stackValueMapping.toJson());
    }
}
