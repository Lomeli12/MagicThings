package net.lomeli.mt.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockItem extends BlockMT {

    public BlockItem(int id, String texture) {
        super(id, Material.air, texture);
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
