package net.lomeli.mt.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockItem extends BlockMetaData {

    public BlockItem(int id, String texture, int i) {
        super(id, Material.air, texture, i);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return false;
    }
}
