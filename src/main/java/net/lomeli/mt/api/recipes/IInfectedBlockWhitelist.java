package net.lomeli.mt.api.recipes;

import java.util.List;
import java.util.Map;

public interface IInfectedBlockWhitelist {

    public void addBlock(int blockID);

    public void addMetaBlock(int blockID, int meta);

    public boolean isBlockInList(int blockID, int meta);

    public boolean isBlockInList(int blockID);

    public List<Integer> getBlockList();

    public Map<Integer, Integer> getMetaBlockList();
}
