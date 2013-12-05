package net.lomeli.mt.client.render;

import org.lwjgl.opengl.GL11;

import net.lomeli.lomlib.fluid.FluidRender;

import net.lomeli.mt.tile.TileEntityClearTank;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.FluidStack;

public class RenderTank extends TileEntitySpecialRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        TileEntityClearTank tank = (TileEntityClearTank) tileentity;

        FluidStack liquid = tank.tank.getFluid();

        if (liquid == null || liquid.amount <= 0) {
            return;
        }

        int[] displayList = FluidRender.getFluidDisplayLists(liquid, tileentity.worldObj, false);
        if (displayList == null) {
            return;
        }

        GL11.glPushMatrix();
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        bindTexture(FluidRender.getFluidSheet(liquid));
        FluidRender.setColorForFluidStack(liquid);

        GL11.glTranslatef((float) x + 0.125F, (float) y, (float) z + 0.125F);
        GL11.glScalef(0.75F, 0.999F, 0.75F);

        GL11.glCallList(displayList[(int) ((float) liquid.amount / (float) (tank.tank.getCapacity()) * (FluidRender.DISPLAY_STAGES - 1))]);

        GL11.glPopAttrib();
        GL11.glPopMatrix();
    }

}
