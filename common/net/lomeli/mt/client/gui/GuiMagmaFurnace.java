package net.lomeli.mt.client.gui;

import org.lwjgl.opengl.GL11;

import net.lomeli.mt.inventory.ContainerMagmaFurnace;
import net.lomeli.mt.lib.Gui;
import net.lomeli.mt.tile.TileEntityMagmaFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiMagmaFurnace extends GuiContainer {

    private TileEntityMagmaFurnace furnace;

    public GuiMagmaFurnace(InventoryPlayer inventoryPlayer, TileEntityMagmaFurnace tile) {
        super(new ContainerMagmaFurnace(inventoryPlayer, tile));
        this.furnace = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = StatCollector.translateToLocal(Gui.magmaFurnaceTile);
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) - 40, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(Gui.MAGMA_FURNACE);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if(this.furnace.isActive()) {
            i1 = this.furnace.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.furnace.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        if(this.furnace.getMainTank().getFluid() != null && this.furnace.getMainTank().getFluidAmount() > 0) {
            this.drawTexturedModalRect(k + 112, (l + (77 - this.furnace.getLiquidScaled(71))), 200,
                    this.furnace.getLiquidScaled(71), 13, this.furnace.getLiquidScaled(71));
        }
    }

}
