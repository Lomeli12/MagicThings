package net.lomeli.mt.addon.microblocks;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

import net.lomeli.mt.block.BlockDecor;

import codechicken.lib.lighting.LC;
import codechicken.lib.lighting.LightMatrix;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.MultiIconTransformation;
import codechicken.lib.render.UV;
import codechicken.lib.render.Vertex5;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.IMicroMaterialRender;

public class DecorBlock extends BlockMicroMaterial {

    public DecorBlock(Block block, int meta) {
        super(block, meta);
    }

    @Override
    public void renderMicroFace(Vertex5[] verts, int side, Vector3 pos, LightMatrix lightMatrix, IMicroMaterialRender part) {
        UV uv = new UV();
        Tessellator tes = Tessellator.instance;
        int i = 0;

        MultiIconTransformation mit = new MultiIconTransformation(new Icon[] { ((BlockDecor) this.block()).effect, ((BlockDecor) this.block()).effect,
                ((BlockDecor) this.block()).effect, ((BlockDecor) this.block()).effect, ((BlockDecor) this.block()).effect, ((BlockDecor) this.block()).effect });

        float red = 0, green = 0, blue = 0;
        Color blockColor = BlockDecor.getBlockColor(this.meta());

        red = ((float) blockColor.getRed()) / 255f;
        green = ((float) blockColor.getGreen()) / 255f;
        blue = ((float) blockColor.getBlue()) / 255f;

        super.renderMicroFace(verts, side, pos, lightMatrix, part);
        while (i < 4) {
            if (CCRenderState.useNormals()) {
                Vector3 n = Rotation.axes[side % 6];
                tes.setNormal((float) n.x, (float) n.y, (float) n.z);
            }
            Vertex5 vert = verts[i];
            if (lightMatrix != null) {
                LC lc = LC.computeO(vert.vec, side);
                if (CCRenderState.useModelColours())
                    lightMatrix.setColour(tes, lc, getColour(part));
                lightMatrix.setBrightness(tes, lc);
            } else {
                if (CCRenderState.useModelColours())
                    CCRenderState.vertexColour(getColour(part));
            }

            tes.setColorOpaque_I(new Color(red, green, blue).getRGB());
            tes.setBrightness(300);
            mit.transform(uv.set(vert.uv));

            tes.addVertexWithUV(vert.vec.x + pos.x, vert.vec.y + pos.y, vert.vec.z + pos.z, uv.u, uv.v);
            i += 1;
        }

    }
}
