package net.lomeli.mt.addon;

import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.api.recipes.MTRecipeHandlers;
import net.lomeli.mt.block.InfectedBlockList;
import net.lomeli.mt.recipes.FlyingRingFuelRegistry;
import net.lomeli.mt.recipes.MagmaFurnaceRecipe;
import net.lomeli.mt.recipes.UnwinderManger;

public class Addons {
    public static void initAddons() {
        MTRecipeHandlers.flyingRingFuel = new FlyingRingFuelRegistry();

        MTRecipeHandlers.magmaFurnace = new MagmaFurnaceRecipe();

        MTRecipeHandlers.unwinderManager = new UnwinderManger();

        MTRecipeHandlers.infectedWhitelist = new InfectedBlockList();
    }
    
    public static void loadAddons() {
        if (ModLoaded.isModInstalled("Morph"))
            MorphAddon.registerAbilities();
        if (ModLoaded.isModInstalled("ForgeMultipart"))
            MultiPart.loadMultiPart();
        if (ModLoaded.isModInstalled("EE3"))
            EE3Addon.loadAddon();
    }
}
