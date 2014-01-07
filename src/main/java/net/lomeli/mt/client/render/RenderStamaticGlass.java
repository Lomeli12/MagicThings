package net.lomeli.mt.client.render;

import net.lomeli.lomlib.client.render.RenderConnectedTextures;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class RenderStamaticGlass extends RenderConnectedTextures {
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return super.renderWorldBlock(world, x, y, z, block, modelId, renderer);
        /*
         * GL11.glPushMatrix(); GL11.glEnable(GL11.GL_BLEND);
         * GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         * GL11.glColor4f(1F, 226/255F, 64/255F, 0.5F); if(metadata == 1){
         * float[] ambient = { 0.0f, 1.0f, 1.0f, 1.0f }; ByteBuffer temp =
         * ByteBuffer.allocateDirect(16); temp.order(ByteOrder.nativeOrder());
         * GL11.glEnable(GL11.GL_LIGHT1); GL11.glEnable(GL11.GL_COLOR_MATERIAL);
         * GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT,
         * (FloatBuffer)temp.asFloatBuffer().put(ambient).flip());
         * GL11.glColorMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT); }
         * 
         * if(metadata == 1){ GL11.glDisable(GL11.GL_COLOR_MATERIAL);
         * GL11.glDisable(GL11.GL_LIGHT1); } GL11.glDisable(GL11.GL_BLEND);
         * GL11.glPopMatrix();
         */
    }
}
