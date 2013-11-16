package net.lomeli.mt.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lomeli.mt.api.interfaces.IInfectedBlockWhitelist;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class InfectedBlockList implements IInfectedBlockWhitelist {
    private List<Integer> blockList = new ArrayList<Integer>();
    private Map<Integer, Integer> metaBlockList = new HashMap<Integer, Integer>();
    
    public InfectedBlockList() {
        addBlock(Block.dirt.blockID);
        addBlock(Block.grass.blockID);
        addBlock(Block.gravel.blockID);
        addBlock(Block.wood.blockID);
        addBlock(Block.leaves.blockID);
        for(ItemStack stack : OreDictionary.getOres("plankWood")) {
            addMetaBlock(stack.itemID, stack.getItemDamage());
        }
        for(ItemStack stack : OreDictionary.getOres("logWood")) {
            addMetaBlock(stack.itemID, stack.getItemDamage());
        }
        for(ItemStack stack : OreDictionary.getOres("leavesTree")) {
            addMetaBlock(stack.itemID, stack.getItemDamage());
        }

    }

    public void addBlock(int blockID) {
        blockList.add(blockID);
    }

    public void addMetaBlock(int blockID, int meta) {
        metaBlockList.put(blockID, meta);
    }

    public boolean isBlockInList(int blockID, int meta) {
        boolean inList = false;
        if(blockList.contains(blockList))
            inList = true;
        else if(metaBlockList.containsKey(blockID)) {
            if(metaBlockList.get(blockID) == meta)
                inList = true;
        }
        return inList;
    }

    public boolean isBlockInList(int blockID) {
        return isBlockInList(blockID, 0);
    }

    public List<Integer> getBlockList() {
        return blockList;
    }

    public Map<Integer, Integer> getMetaBlockList() {
        return metaBlockList;
    }
}
