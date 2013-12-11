package net.lomeli.mt.addon;

import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Cuboid6;
import codechicken.microblock.IHollowConnect;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartConverter;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.minecraft.McMetaPart;

public class MultiPart {
    public static void loadMultiPart() {
        if (ModLoaded.isModInstalled("ForgeMultipart")) {
            RegisterPartCable cableRegister = new RegisterPartCable();
            cableRegister.init();
        }
    }

    private static class CablePart extends McMetaPart implements IHollowConnect {

        public CablePart(){
            super(0);
        }
        
        public CablePart(int meta){
            super(meta);
        }
        
        @Override
        public Cuboid6 getBounds() {
            return new Cuboid6(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
        }

        @Override
        public int getHollowSize() {
            return 4;
        }

        @Override
        public Block getBlock() {
            return ModBlocks.cable;
        }

        @Override
        public String getType() {
            return Strings.MOD_ID.toLowerCase() + ":cable";
        }

        @Override
        public int getMetadata(){
            return world().getBlockMetadata(x(), y(), z());
        }
    }

    private static class RegisterPartCable implements IPartConverter, IPartFactory {

        public void init() {
            MultiPartRegistry.registerConverter(this);
            MultiPartRegistry.registerParts(this, new String[] { (Strings.MOD_ID.toLowerCase() + ":cable") });
        }

        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return arg0.equals(Strings.MOD_ID.toLowerCase() + ":cable") ? new CablePart() : null;
        }

        @Override
        public boolean canConvert(int blockID) {
            return blockID == ModBlocks.cable.blockID;
        }

        @Override
        public TMultiPart convert(World world, BlockCoord pos) {
            return canConvert(world.getBlockId(pos.x, pos.y, pos.z)) ? new CablePart(world.getBlockMetadata(pos.x, pos.y, pos.z)) : null;
        }

    }
}
