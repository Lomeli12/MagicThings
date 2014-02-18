package net.lomeli.mt.client.gui;

import net.lomeli.lomlib.client.gui.GuiContainerPlus;
import net.lomeli.lomlib.client.gui.element.ElementTank;
import net.lomeli.lomlib.client.gui.tab.TabInfo;

import net.lomeli.mt.inventory.ContainerMagmaFurnace;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.GuiInfo;
import net.lomeli.mt.tile.TileMagmaFurnace;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMagmaFurnace extends GuiContainerPlus {

    private TileMagmaFurnace furnace;

    public GuiMagmaFurnace(InventoryPlayer inventoryPlayer, TileMagmaFurnace tile) {
        super(new ContainerMagmaFurnace(inventoryPlayer, tile));
        this.furnace = tile;
        this.texture = GuiInfo.MAGMA_FURNACE;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.addElement(new ElementTank(this, 112, 10, this.furnace.getMainTank()).setGauge(1));
        this.addTab(new TabInfo(this, StatCollector.translateToLocal(BlockInfo.TAB_MAGMAFURNACE)).setSide(0));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        String s = StatCollector.translateToLocal(furnace.getInvName());
        this.fontRenderer.drawString(s, (this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2) - 40, 6, 4210752);
        this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        super.drawGuiContainerForegroundLayer(x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        this.drawGui();
        int i1;
        i1 = this.furnace.getBurnTimeRemainingScaled(12);
        this.drawTexturedModalRect(guiLeft + 56, guiTop + 36 + 13 - i1, 176, 13 - i1, 14, i1 + 2);
        i1 = this.furnace.getCookProgressScaled(24);
        this.drawTexturedModalRect(guiLeft + 79, guiTop + 34, 176, 14, i1 + 1, 16);
        super.drawGuiContainerBackgroundLayer(f, mouseX, mouseY);
    }

}
