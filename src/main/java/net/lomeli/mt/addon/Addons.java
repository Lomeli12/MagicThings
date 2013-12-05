package net.lomeli.mt.addon;

import net.lomeli.mt.api.MTRecipeHandlers;
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
}
