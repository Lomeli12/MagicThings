package net.lomeli.mt.client.render;

import org.lwjgl.opengl.GL11;

import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.lib.BlockInfo;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.client.IItemRenderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.IconTransformation;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.render.TextureUtils;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Translation;

public class RenderDecor implements ISimpleBlockRenderingHandler, IItemRenderer {

    @Override
    public int getRenderId() {
        return BlockInfo.decorRenderID;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        renderInventory(metadata, 0f, -0.05f, 0f, 0.95f);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        //int meta = world.getBlockMetadata(x, y, z);
        /*int glowColour = 8;
        switch(meta){
            case 1: glowColour = 14;
            case 2: glowColour = 13;
            case 3: glowColour = 11;
            default: glowColour = 8;
        }*/
        
        Tessellator.instance.setBrightness(0x00F000F0);
        renderer.renderStandardBlock(block, x, y, z);
        
        //RenderUtil.addGlow(x, y, z, glowColour, Cuboid6.full.expand(0.05D));
        
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        switch (type) {
        case ENTITY:
            renderInventory(item.getItemDamage(), -0.15f, 0f, -0.15f, 1f);
            return;
        case EQUIPPED:
            renderInventory(item.getItemDamage(), 0f, 0f, 0f, 1f);
            return;
        case EQUIPPED_FIRST_PERSON:
            renderInventory(item.getItemDamage(), 0f, 0f, 0f, 1f);
            return;
        case INVENTORY:
            renderInventory(item.getItemDamage(), 0f, -0.05f, 0f, 0.95f);
            return;
        default:
            return;
        }
    }
    
    private void renderInventory(int meta, double x, double y, double z, double scale) {
        Icon icon = ModBlocks.decor.getIcon(0, meta);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glScaled(scale, scale, scale);
        TextureUtils.bindAtlas(0);
        CCRenderState.reset();
        CCRenderState.useNormals(true);
        CCRenderState.pullLightmap();
        CCRenderState.startDrawing(7);
        RenderUtils.renderBlock(Cuboid6.full, 0, new Translation(x, y, z), new IconTransformation(icon), null);
        CCRenderState.draw();
        /*RenderUtil.prepareRenderState();
        int glowColour = 8;
        switch(meta){
            case 1: glowColour = 14;
            case 2: glowColour = 13;
            case 3: glowColour = 11;
            default: glowColour = 8;
        }*/
        //RenderUtil.renderGlow(Tessellator.instance, Cuboid6.full.expand(0.05D), glowColour, new Translation(x, y, z));
        //RenderUtil.restoreRenderState();
        GL11.glPopMatrix();
    }


}
