package net.lomeli.mt.core;

import java.io.File;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;

import net.minecraftforge.common.Configuration;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Ints;
import net.lomeli.mt.lib.Ints.KeyConfigs;
import net.lomeli.mt.lib.ItemInfo;

public class Config {

    private Configuration config;

    public Config(File configFile) {
        config = new Configuration(configFile);
    }

    public void configureMod() {
        config.load();

        configItemIDs();
        configBlockIDs();
        configureOptions();
        configureKeys();

        config.save();
    }

    private void configItemIDs() {
        ItemInfo.standardItemsID = getItemID("standardItems", 7000);
        ItemInfo.flyingRingID = getItemID("flyingRing", 7001);
        ItemInfo.materialID = getItemID("material", 7002);
        ItemInfo.medKitID = getItemID("medkit", 7003);
        ItemInfo.ingotsID = getItemID("ingots", 7004);
        ItemInfo.herbID = getItemID("herb", 7005);
        ItemInfo.shaverID = getItemID("shaver", 7006);
        ItemInfo.errorID = getItemID("error", 7007);
        ItemInfo.emptyCaseID = getItemID("emptyCase", 7008);
        ItemInfo.dustID = getItemID("dusts", 7009);
        ItemInfo.upgradesID = getItemID("upgrades", 7010);
        ItemInfo.peaceTreatyID = getItemID("peaceTreaty", 7011);
        ItemInfo.portaCraftID = getItemID("portaCraft", 7012);
        ItemInfo.liquidReaderID = getItemID("liquidReader", 7013);
        ItemInfo.condenseBagID = getItemID("condenseBags", 7014);
        ItemInfo.recordID = getItemID("record", 7015);
        ItemInfo.wrenchID = getItemID("wrench", 7016);
        ItemInfo.tankUpgradeID = getItemID("tankUpgrade", 7017);
        ItemInfo.runningShoesID = getItemID("runningShoes", 7018);
        ItemInfo.tileCapsuleID = getItemID("tileCapsule", 7019);
    }

    private int getItemID(String str, int id) {
        return config.getItem(str, id).getInt(id);
    }

    private void configBlockIDs() {
        BlockInfo.ingotBlockID = getBlockID("IngotBlocks", 700);
        BlockInfo.infectedBlockID = getBlockID("infectedBlock", 701);
        BlockInfo.compactGenID = getBlockID("compackCobGen", 702);
        BlockInfo.fertileBushID = getBlockID("fertileBush", 703);
        BlockInfo.magicSandID = getBlockID("magicSand", 704);
        BlockInfo.aquaticManipID = getBlockID("aquaticManip", 705);
        BlockInfo.clearTankID = getBlockID("clearTank", 706);
        BlockInfo.machineFrameID = getBlockID("machineFrame", 707);
        BlockInfo.magmaFurnaceID = getBlockID("magmaFurnace", 708);
        BlockInfo.treatedWoolID = getBlockID("treatedwool", 709);
        BlockInfo.decorID = getBlockID("decorativeBlock", 710);
        BlockInfo.glassID = getBlockID("glass", 711);
        BlockInfo.airID = getBlockID("airBlock", 712);

        BlockInfo.stamaticOreGen = config.get("OreGeneration", "stamaticOreGenRate", 15).getInt(15);
        BlockInfo.aquaticOreGen = config.get("OreGeneration", "aquaticOreGenRate", 2).getInt(2);
        BlockInfo.igniousOreGen = config.get("OreGeneration", "igniousOreGenRate", 4).getInt(4);
        BlockInfo.neoOreGen = config.get("OreGeneration", "neoOreGenRate", 3).getInt(3);
        BlockInfo.netherIgniousOreGen = config.get("OreGeneration", "netherIgniousOreGenRate", 8).getInt(8);

        BlockInfo.fertileBushRange = config.get("General", "fertileBushRange", 5).getInt(5);
    }

    private int getBlockID(String str, int id) {
        return config.getBlock(str, id).getInt(id);
    }

    private static String energyTap = "Number of ticks before the Energy Tap Produces Energy. 20 ticks per seconfigd";
    private static String shaverLoot = "Chance of mobs dropping items they're holding using the shaver.";
    private static String shaverExplode = "Chance of creeper exploding if shaver is used on one.";

    private void configureOptions() {
        MThings.debug = config.get("General", "debugMode", false).getBoolean(false);
        Ints.energyTapTick = config.get("General", "EnergyTapTick", 50, energyTap).getInt(50);
        Ints.shaverLootChance = config.get("General", "shaverLoot", 15, shaverLoot).getInt(15);
        Ints.shaverExplodeChance = config.get("General", "shaverExplode", 30, shaverExplode).getInt(30);

        if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER) {
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            if (day >= 20 && day <= 30)
                date = true;
        }
    }

    public static boolean date;

    private void configureKeys() {
        String keyConfigInfo = "See http://minecraft.gamepedia.com/Key_codes for key codes";

        KeyConfigs.KEY_MOUNT_TOOL_CLEAR = config.get("General", "MOUNT_TOOL_CLEAR", Keyboard.KEY_F, keyConfigInfo).getInt(Keyboard.KEY_F);
        KeyConfigs.KEY_LIQUID_READER_MODE_CHANGE = config.get("General", "LIQUID_READER_MODE_CHANGE", Keyboard.KEY_R, keyConfigInfo).getInt(Keyboard.KEY_R);
    }
}
