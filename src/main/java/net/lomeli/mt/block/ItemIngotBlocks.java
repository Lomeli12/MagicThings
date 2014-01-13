package net.lomeli.mt.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class ItemIngotBlocks extends ItemBlock {

    public ItemIngotBlocks(int par1) {
        super(par1);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public Icon getIconFromDamage(int par1) {
        return ModBlocks.ingotBlock.getIcon(0, par1);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getItemDisplayName(ItemStack stack) {
        return StatCollector.translateToLocal(stack.getUnlocalizedName() + "." + stack.getItemDamage());
    }
}
