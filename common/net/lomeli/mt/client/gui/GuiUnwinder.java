package net.lomeli.mt.client.gui;

import org.lwjgl.opengl.GL11;

import net.lomeli.mt.inventory.ContainerUnwinder;
import net.lomeli.mt.lib.Gui;
import net.lomeli.mt.tile.TileEntityUnwinder;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiUnwinder extends GuiContainer {

    public GuiUnwinder(InventoryPlayer inventoryPlayer, TileEntityUnwinder tile) {
        super(new ContainerUnwinder(inventoryPlayer, tile));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(StatCollector.translateToLocal(Gui.unwindTile), 8, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        this.mc.renderEngine.bindTexture(Gui.UNWINDER);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(Gui.UNWINDER);
    }

}
