package net.lomeli.mt.block;

import java.util.List;
import java.awt.Color;

import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecor extends BlockMT {
    public Icon effect, brick;

    public BlockDecor(int id) {
        super(id, Material.rock, "decor");
        this.setResistance(20F);
        this.setHardness(4.0F);
        this.setStepSound(soundStoneFootstep);
        this.setUnlocalizedName("decor");
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        brick = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":brick");
        effect = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":brickEffect");
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int j = 0; j < 16; ++j) {
            par3List.add(new ItemStack(par1, 1, j));
        }
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    public Icon getIcon(int par1, int par2) {
        return brick;
    }

    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return this.damageDropped(par1World.getBlockMetadata(par2, par3, par4));
    }

    @Override
    public int getRenderType() {
        return BlockInfo.decorRenderID;
    }

    @SideOnly(Side.CLIENT)
    public static Color getBlockColor(int meta) {
        Color blockColor = null;

        switch (meta) {
        case 0:
            blockColor = Color.WHITE;
            break;
        case 1:
            blockColor = new Color(255, 110, 0);
            break;
        case 2:
            blockColor = Color.MAGENTA;
            break;
        case 3:
            blockColor = new Color(0, 153, 255);
            break;
        case 4:
            blockColor = Color.YELLOW;
            break;
        case 5:
            blockColor = new Color(30, 255, 0);
            break;
        case 6:
            blockColor = new Color(255, 75, 190);
            break;
        case 7:
            blockColor = Color.DARK_GRAY;
            break;
        case 8:
            blockColor = Color.GRAY;
            break;
        case 9:
            blockColor = Color.CYAN;
            break;
        case 10:
            blockColor = new Color(190, 0, 255);
            break;
        case 11:
            blockColor = Color.BLUE;
            break;
        case 12:
            blockColor = new Color(135, 70, 0);
            break;
        case 13:
            blockColor = new Color(10, 135, 0);
            break;
        case 14:
            blockColor = Color.RED;
            break;
        case 15:
            blockColor = new Color(30, 30, 30);
            break;
        default:
            blockColor = Color.WHITE;
            break;
        }

        return blockColor;
    }

    public static class ItemDecor extends ItemBlock {

        public ItemDecor(int par1) {
            super(par1);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public Icon getIconFromDamage(int par1) {
            return ModBlocks.decor.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int par1) {
            return par1;
        }

        @Override
        public String getItemDisplayName(ItemStack stack) {
            return StatCollector.translateToLocal(stack.getUnlocalizedName() + "." + stack.getItemDamage());
        }
    }
}
