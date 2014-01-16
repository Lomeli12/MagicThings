package net.lomeli.mt.lib;

import net.lomeli.lomlib.client.ResourceUtil;

import net.minecraft.util.ResourceLocation;

public class GuiInfo {
    private static final String GUI_LOC = "textures/gui/";
    private static String guiName = "gui." + Strings.MOD_ID.toLowerCase() + ":";

    public static final ResourceLocation PORTA_CRAFT = new ResourceLocation(GUI_LOC + "container/crafting_table.png");
    public static final ResourceLocation SMALL_SACK = ResourceUtil.getGuiResource(Strings.MOD_ID.toLowerCase(), "s_sack.png");
    public static final ResourceLocation LARGE_SACK = ResourceUtil.getGuiResource(Strings.MOD_ID.toLowerCase(), "l_sack.png");
    public static final ResourceLocation TELEPORTER = ResourceUtil.getGuiResource(Strings.MOD_ID.toLowerCase(), "teleport.png");
    public static final ResourceLocation MAGMA_FURNACE = ResourceUtil.getGuiResource(Strings.MOD_ID.toLowerCase(), "magmaFurnace.png");

    public static String magmaFurnaceTile = guiName + "magmaFurnace";
}
