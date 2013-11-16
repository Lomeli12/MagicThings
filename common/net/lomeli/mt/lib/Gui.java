package net.lomeli.mt.lib;

import net.lomeli.lomlib.util.ResourceUtil;

import net.minecraft.util.ResourceLocation;

public class Gui {
    private static final String GUI_LOC = "textures/gui/";
    private static String guiName = "gui." + Strings.MOD_ID.toLowerCase() + ":";

    public static final ResourceLocation PORTA_CRAFT = new ResourceLocation(GUI_LOC + "container/crafting_table.png");
    public static final ResourceLocation SMALL_SACK = ResourceUtil.getGuiResource(Strings.MOD_ID, "s_sack.png");
    public static final ResourceLocation LARGE_SACK = ResourceUtil.getGuiResource(Strings.MOD_ID, "l_sack.png");
    public static final ResourceLocation TELEPORTER = ResourceUtil.getGuiResource(Strings.MOD_ID, "teleport.png");
    public static final ResourceLocation UNWINDER = ResourceUtil.getGuiResource(Strings.MOD_ID, "unwinder.png");
    public static final ResourceLocation MAGMA_FURNACE = ResourceUtil.getGuiResource(Strings.MOD_ID, "magmaFurnace.png");

    public static String magmaFurnaceTile = guiName + "magmaFurnace";
    public static String unwindTile = guiName + "unwind";
}
