package net.lomeli.mt.block;

import net.lomeli.lomlib.block.BlockGeneric;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMT extends BlockGeneric {

    public BlockMT(int id, Material material, String texture) {
        super(id, material, Strings.MOD_ID.toLowerCase(), texture);
        this.setCreativeTab(MThings.modtab);
    }

    public BlockMT setDropID(int id) {
        this.drop = id;
        return this;
    }

    @Override
    public Block setUnlocalizedName(String str) {
        super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + str);
        return this;
    }

}
