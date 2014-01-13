package net.lomeli.mt.client.gui;

import java.util.UUID;

import org.lwjgl.opengl.GL11;

import net.lomeli.mt.inventory.ContainerCondenseBag;
import net.lomeli.mt.inventory.InventoryCondenseBag;
import net.lomeli.mt.lib.Gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiCondenseBag extends GuiContainer {
    private ItemStack itemStack;

    public GuiCondenseBag(ItemStack stack, InventoryCondenseBag inventory, InventoryPlayer player) {
        super(new ContainerCondenseBag(stack, inventory, player));
        if (!stack.hasTagCompound()) {
            stack.stackTagCompound = new NBTTagCompound();

            inventory.setUniqueID(UUID.randomUUID().toString());
        }
        this.itemStack = stack;
        this.xSize += 37;
    }

    public ResourceLocation getTextures() {
        if (itemStack.getItemDamage() != 0)
            return Gui.LARGE_SACK;
        else
            return Gui.SMALL_SACK;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        ResourceLocation guiTexture = getTextures();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        mc.renderEngine.bindTexture(guiTexture);

        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

}
