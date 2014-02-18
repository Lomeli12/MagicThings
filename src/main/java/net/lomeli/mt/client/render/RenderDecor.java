package net.lomeli.mt.client.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.lomeli.lomlib.client.render.RenderUtils;

import net.lomeli.mt.block.BlockDecor;
import net.lomeli.mt.lib.BlockInfo;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderDecor implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
        block.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        renderer.setRenderBoundsFromBlock(block);

        float red = 0, green = 0, blue = 0;
        Color blockColor = BlockDecor.getBlockColor(meta);

        red = ((float) blockColor.getRed()) / 255f;
        green = ((float) blockColor.getGreen()) / 255f;
        blue = ((float) blockColor.getBlue()) / 255f;

        GL11.glColor3f(red, green, blue);
        RenderUtils.drawBlockFaces(renderer, block, ((BlockDecor) block).effect);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        RenderUtils.drawBlockFaces(renderer, block, ((BlockDecor) block).brick);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        block.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        float red = 0, green = 0, blue = 0;
        Color blockColor = BlockDecor.getBlockColor(meta);

        red = ((float) blockColor.getRed()) / 255f;
        green = ((float) blockColor.getGreen()) / 255f;
        blue = ((float) blockColor.getBlue()) / 255f;

        Tessellator tessellator = Tessellator.instance;
        tessellator.setColorOpaque_I(new Color(red, green, blue).getRGB());
        tessellator.setBrightness(300);
        RenderUtils.renderBlock(world, x, y, z, block, renderer, ((BlockDecor) block).effect);

        renderer.clearOverrideBlockTexture();
        block.setBlockBounds(0f, 0f, 0f, 1f, 1f, 1f);
        renderer.setRenderBoundsFromBlock(block);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return BlockInfo.decorRenderID;
    }

}
