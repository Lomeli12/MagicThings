package net.lomeli.mt.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class BlockMagicSand extends BlockMT {

    public BlockMagicSand(int id) {
        super(id, Material.sand, "sand");
        this.setHardness(0.5F);
        this.setStepSound(soundSandFootstep);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if(world.getBlockId(x, y + 1, z) == cactus.blockID) {
            for(int i = 2; i < 7; i++) {
                if(!cactus.canBlockStay(world, x, y + i, z)) {
                    world.destroyBlock(x, y + i, z, true);
                }
                if(world.isAirBlock(x, y + i, z)) {
                    world.setBlock(x, y + i, z, cactus.blockID);
                    world.markBlockForUpdate(x, y + i, z);
                    if(!cactus.canBlockStay(world, x, y + i, z)) {
                        world.destroyBlock(x, y + i, z, true);
                    }
                    break;
                }
                if(!cactus.canBlockStay(world, x, y + i, z)) {
                    world.destroyBlock(x, y + i, z, true);
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant) {
        int plantID = plant.getPlantID(world, x, y + 1, z);
        EnumPlantType plantType = plant.getPlantType(world, x, y + 1, z);
        if(plantID == cactus.blockID)
            return true;
        switch(plantType) {
        case Desert:
            return blockID == sand.blockID;
        default:
            return false;
        }
    }

}
