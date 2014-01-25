package net.lomeli.mt.addon;

//import com.pahimar.ee3.api.StackValueMapping;
//import com.pahimar.ee3.emc.EmcValue;

//import net.lomeli.lomlib.recipes.FluidRecipeEMC;
import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.item.ModItems;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class EE3Addon {
    public static void loadAddon() {
        if (ModLoaded.isModInstalled("EE3")) {
            /*FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 0), new EmcValue(165)));
            FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 1), new EmcValue(240)));
            FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 2), new EmcValue(257)));
            FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.ingots, 1, 3), new EmcValue(4096)));
            FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.material, 1, 0), new EmcValue(35)));
            //FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.material, 1, 1), new EmcValue(6595)));
            //FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModItems.material, 1, 2), new EmcValue(39532)));
            
            //FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[0], new EmcValue(137.142f)));
            //FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[1], new EmcValue(145.142f)));
            //FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[2], new EmcValue(145.142f)));
            //FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(ModBlocks.decorBlocks[3], new EmcValue(145.142f)));
            
            FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(ModBlocks.frames[0], new EmcValue(2353)));
            
            FluidRecipeEMC.sendPreValueAssignment(new StackValueMapping(new ItemStack(ModBlocks.treatedWool, 1, OreDictionary.WILDCARD_VALUE), new EmcValue(78)));
            */
        }
    }
}
