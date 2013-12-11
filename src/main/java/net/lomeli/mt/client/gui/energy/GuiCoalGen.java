package net.lomeli.mt.client.gui.energy;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.lomeli.lomlib.gui.GuiContainerPlus;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import net.lomeli.mt.inventory.energy.ContainerCoalGen;
import net.lomeli.mt.lib.Gui;
import net.lomeli.mt.tile.energy.TileEntityCoalGen;

public class GuiCoalGen extends GuiContainerPlus {
    private TileEntityCoalGen generator;

    public GuiCoalGen(InventoryPlayer player, TileEntityCoalGen tile) {
        super(new ContainerCoalGen(player, tile));
        generator = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        String s = StatCollector.translateToLocal(Gui.coalGen);
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) - 40, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(Gui.COAL_GEN);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        if (this.generator.isActive()) {
            i1 = this.generator.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        if (this.generator.getEnergy() > 0) {
            List info = new ArrayList<String>();
            info.add(generator.getEnergy() + " / " + generator.getMaxEnergy() + " JW");
            int j = (int) this.generator.getEnergyScaled(71);
            this.drawTexturedModalRect(k + 112, (l + 77 - j), 200, 0, 13, j);
            this.drawToolTipOverArea(par2, par3, k + 112, l + 6, k + 125, l + 77, info, mc.fontRenderer);
        }
    }
}
