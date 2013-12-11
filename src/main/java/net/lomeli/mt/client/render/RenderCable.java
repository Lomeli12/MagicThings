package net.lomeli.mt.client.render;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.lomeli.lomlib.util.ResourceUtil;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;

import net.lomeli.mt.client.model.ModelCable;
import net.lomeli.mt.core.helper.EnergyHelper;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.tile.energy.TileEntityCable;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import universalelectricity.core.vector.Vector3;
import universalelectricity.core.vector.VectorHelper;

@SideOnly(Side.CLIENT)
public class RenderCable extends TileEntitySpecialRenderer {
    private ModelCable model;

    public RenderCable() {
        model = new ModelCable();
    }

    @Override
    public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
        this.renderModel((TileEntityCable) tileentity, d0, d1, d2, f);
    }

    public void renderModel(TileEntityCable tileEntity, double d, double d1, double d2, float f) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(ResourceUtil.getModelTexture(Strings.MOD_ID.toLowerCase(),
                "cable_" + tileEntity.worldObj.getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord) + ".png"));
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);

        List<TileEntity> adjecentConnections = new ArrayList<TileEntity>();

        for (byte i = 0; i < 6; i++) {
            ForgeDirection side = ForgeDirection.getOrientation(i);
            TileEntity adjacentTile = VectorHelper.getTileEntityFromSide(tileEntity.worldObj, new Vector3(tileEntity), side);
            if (EnergyHelper.isValidMachine(adjacentTile))
                adjecentConnections.add(i, adjacentTile);
            else
                adjecentConnections.add(i, null);
        }
        if (!adjecentConnections.isEmpty()) {
            if (adjecentConnections.toArray()[0] != null)
                model.Bottom.render(0.0625F);

            if (adjecentConnections.toArray()[1] != null)
                model.Top.render(0.0625F);

            if (adjecentConnections.toArray()[2] != null)
                model.Back.render(0.0625F);

            if (adjecentConnections.toArray()[3] != null)
                model.Front.render(0.0625F);

            if (adjecentConnections.toArray()[4] != null)
                model.Right.render(0.0625F);

            if (adjecentConnections.toArray()[5] != null)
                model.Left.render(0.0625F);
        }

        model.Center.render(0.0625F);

        GL11.glPopMatrix();
    }

}
