package net.lomeli.mt.addon;

import net.lomeli.mt.addon.microblocks.ConnectedTextureBlock;
import net.lomeli.mt.addon.microblocks.DecorBlock;
import net.lomeli.mt.block.ModBlocks;

import codechicken.microblock.MicroMaterialRegistry;

public class ForgeMultipartAddon {
    public static void loadAddon() {
        MicroMaterialRegistry.registerMaterial(new ConnectedTextureBlock(ModBlocks.glass, 0), ModBlocks.glass.getUnlocalizedName() + "." + 0);
        MicroMaterialRegistry.registerMaterial(new ConnectedTextureBlock(ModBlocks.glass, 1), ModBlocks.glass.getUnlocalizedName() + "." + 1);

        for (int i = 0; i < 16; i++) {
            MicroMaterialRegistry.registerMaterial(new DecorBlock(ModBlocks.decor, i), ModBlocks.decor.getUnlocalizedName() + "." + i);
        }
    }
}
