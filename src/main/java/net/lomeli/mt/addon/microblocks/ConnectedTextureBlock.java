package net.lomeli.mt.addon.microblocks;

import scala.collection.Iterator;

import net.lomeli.lomlib.client.render.IconConnected;
import net.lomeli.lomlib.client.render.RenderConnectedTextures;
import net.lomeli.lomlib.client.render.RenderFakeBlock;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;

import codechicken.lib.lighting.LightMatrix;
import codechicken.lib.render.IUVTransformation;
import codechicken.lib.render.UVTranslation;
import codechicken.lib.render.Vertex5;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.FaceMicroblockClient;
import codechicken.microblock.IMicroMaterialRender;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;

public class ConnectedTextureBlock extends BlockMicroMaterial {
    public static final double[] u = { -1.0D, 1.0D, 1.0D, -1.0D };
    public static final double[] v = { 1.0D, 1.0D, -1.0D, -1.0D };

    public ConnectedTextureBlock(Block block, int meta) {
        super(block, meta);
    }
    
    @Override
    public int getLightValue() {
        if (meta() == 1)
            return 15;
        return 0;
    }

    @Override
    public void renderMicroFace(Vertex5[] vert, int side, Vector3 pos, LightMatrix lightMatrix, IMicroMaterialRender part) {
        int c = getColour(part);

        
        if (part.world() != null) {
            if ((part instanceof FaceMicroblockClient)) {
                int s = getSideFromBounds(part.getRenderBounds());

                if (s == side) {
                    RenderFakeBlock fb = RenderConnectedTextures.fakeBlock;
                    fb.setWorld(part.world());
                    Icon icon = block().getIcon(s, meta());
                    if ((icon instanceof IconConnected)) {
                        fb.curBlock = (block().blockID * 16 + meta());

                        double h = 0.001D;

                        switch (s) {
                        case 0:
                            renderHalfSide(block(), 0.5D, h, 0.5D, 1, 0, 0, 0, 0, -1, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 1:
                            renderHalfSide(block(), 0.5D, 1.0D - h, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 2:
                            renderHalfSide(block(), 0.5D, 0.5D, h, 1, 0, 0, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 3:
                            renderHalfSide(block(), 0.5D, 0.5D, 1.0D - h, -1, 0, 0, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 4:
                            renderHalfSide(block(), h, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 5:
                            renderHalfSide(block(), 1.0D - h, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                        }

                        return;
                    }
                } else if (side % 6 == Facing.oppositeSide[s]) {
                    Icon icon = block().getIcon(side % 6, meta());

                    if ((icon instanceof IconConnected)) {
                        switch (Facing.oppositeSide[s]) {
                        case 0:
                            renderHalfSide(block(), 0.5D, vert[0].vec.y, 0.5D, 1, 0, 0, 0, 0, -1, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 1:
                            renderHalfSide(block(), 0.5D, vert[0].vec.y, 0.5D, -1, 0, 0, 0, 0, -1, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 2:
                            renderHalfSide(block(), 0.5D, 0.5D, vert[0].vec.z, 1, 0, 0, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 3:
                            renderHalfSide(block(), 0.5D, 0.5D, vert[0].vec.z, -1, 0, 0, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 4:
                            renderHalfSide(block(), vert[0].vec.x, 0.5D, 0.5D, 0, 0, -1, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                            break;
                        case 5:
                            renderHalfSide(block(), vert[0].vec.x, 0.5D, 0.5D, 0, 0, 1, 0, 1, 0, (IconConnected) icon, side, pos, lightMatrix, c, (FaceMicroblockClient) part);
                        }

                        return;
                    }
                } else {
                    if (hasMatchingPart((FaceMicroblockClient) part, part.x() + Facing.offsetsXForSide[(side % 6)], part.y() + Facing.offsetsYForSide[(side % 6)], part.z()
                            + Facing.offsetsZForSide[(side % 6)]))
                        return;
                }
            }
        }
        super.renderMicroFace(vert, side, pos, lightMatrix, part);
    }

    public int getSideFromBounds(Cuboid6 bounds) {
        if (bounds.max.y != 1.0D)
            return 0;

        if (bounds.min.y != 0.0D)
            return 1;

        if (bounds.max.z != 1.0D)
            return 2;

        if (bounds.min.z != 0.0D)
            return 3;

        if (bounds.max.x != 1.0D)
            return 4;

        if (bounds.min.x != 0.0D)
            return 5;

        return -1;
    }

    public void renderSide(Block block, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnected icon, int side, Vector3 pos,
            LightMatrix lightMatrix, int colour, IUVTransformation uvt) {
        for (int j = 0; j < 4; j++) {
            int i = RenderConnectedTextures.fakeBlock.getType(block, side, (int) pos.x, (int) pos.y, (int) pos.z, ax * (int) u[j], ay * (int) u[j], az * (int) u[j], bx
                    * (int) v[j], by * (int) v[j], bz * (int) v[j], (int) (ox * 2.0D - 1.0D), (int) (oy * 2.0D - 1.0D), (int) (oz * 2.0D - 1.0D));

            icon.setType(i);
            double cx = ox + ax * u[j] / 4.0D + bx * v[j] / 4.0D;
            double cy = oy + ay * u[j] / 4.0D + by * v[j] / 4.0D;
            double cz = oz + az * u[j] / 4.0D + bz * v[j] / 4.0D;
            Vertex5[] vec = new Vertex5[4];

            for (int k = 0; k < 4; k++) {
                vec[k] = new Vertex5(cx + u[k] * ax * 0.25D + v[k] * bx * 0.25D, cy + u[k] * ay * 0.25D + v[k] * by * 0.25D, cz + u[k] * az * 0.25D + v[k] * bz * 0.25D,
                        icon.getInterpolatedU(16.0D - (8.0D + u[j] * 4.0D + u[k] * 4.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[j] * 4.0D + v[k] * 4.0D)));
            }

            super.renderMicroFace(vec, side, pos, lightMatrix, colour, new UVTranslation(0.0D, 0.0D));
            icon.resetType();
        }
    }

    @SuppressWarnings("rawtypes")
    public boolean hasMatchingPart(FaceMicroblockClient part, int x, int y, int z) {
        TileEntity tile_base = part.world().getBlockTileEntity(x, y, z);

        if ((tile_base != null) && ((tile_base instanceof TileMultipart))) {
            Iterator parts = ((TileMultipart) tile_base).partList().toIterator();

            while (parts.hasNext()) {
                TMultiPart p = (TMultiPart) parts.next();

                if ((p instanceof FaceMicroblockClient)) {
                    if ((((FaceMicroblockClient) p).shape() == part.shape()) && (((FaceMicroblockClient) p).material() == part.material()))
                        return true;
                }
            }
        }

        return false;
    }

    public int getHalfType(Block block, int side, int x, int y, int z, int ax, int ay, int az, int bx, int by, int bz, FaceMicroblockClient part) {
        boolean a = hasMatchingPart(part, x + ax, y + ay, z + az);
        boolean b = hasMatchingPart(part, x + bx, y + by, z + bz);

        if (a) {
            if (b) {
                if (hasMatchingPart(part, x + ax + bx, y + ay + by, z + az + bz))
                    return 3;

                return 4;
            }

            return 2;
        }

        if (b)
            return 1;

        return 0;
    }

    public void renderHalfSide(Block block, double ox, double oy, double oz, int ax, int ay, int az, int bx, int by, int bz, IconConnected icon, int side, Vector3 pos,
            LightMatrix lightMatrix, int colour, FaceMicroblockClient part) {
        for (int j = 0; j < 4; j++) {
            int i = getHalfType(block, side, (int) pos.x, (int) pos.y, (int) pos.z, ax * (int) u[j], ay * (int) u[j], az * (int) u[j], bx * (int) v[j], by * (int) v[j], bz
                    * (int) v[j], part);
            icon.setType(i);
            double cx = ox + ax * u[j] / 4.0D + bx * v[j] / 4.0D;
            double cy = oy + ay * u[j] / 4.0D + by * v[j] / 4.0D;
            double cz = oz + az * u[j] / 4.0D + bz * v[j] / 4.0D;
            Vertex5[] vec = new Vertex5[4];

            for (int k = 0; k < 4; k++) {
                vec[k] = new Vertex5(cx + u[k] * ax * 0.25D + v[k] * bx * 0.25D, cy + u[k] * ay * 0.25D + v[k] * by * 0.25D, cz + u[k] * az * 0.25D + v[k] * bz * 0.25D,
                        icon.getInterpolatedU(16.0D - (8.0D + u[j] * 4.0D + u[k] * 4.0D)), icon.getInterpolatedV(16.0D - (8.0D + v[j] * 4.0D + v[k] * 4.0D)));
            }

            super.renderMicroFace(vec, side, pos, lightMatrix, colour, new UVTranslation(0.0D, 0.0D));
            icon.resetType();
        }
    }
}
