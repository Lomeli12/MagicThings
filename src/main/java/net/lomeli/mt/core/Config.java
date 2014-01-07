package net.lomeli.mt.core;

import java.io.File;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;

import net.lomeli.lomlib.util.XMLConfiguration;
import net.lomeli.lomlib.util.XMLConfiguration.ConfigEnum;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Ints;
import net.lomeli.mt.lib.Ints.KeyConfigs;
import net.lomeli.mt.lib.ItemInfo;

public class Config {

    private File configFile;
    private XMLConfiguration config;

    public Config(File configFile) {
        this.configFile = configFile;
    }

    public void configureMod() {
        config = new XMLConfiguration(this.configFile);

        config.loadXml();

        configItemIDs(config);
        configBlockIDs(config);
        configureOptions(config);
        configureKeys(config);

        config.saveXML();
    }

    private void configItemIDs(XMLConfiguration config) {
        ItemInfo.standardItemsID = config.getItemID("standardItems", 7000);
        ItemInfo.flyingRingID = config.getItemID("flyingRing", 7001);
        ItemInfo.materialID = config.getItemID("material", 7002);
        ItemInfo.medKitID = config.getItemID("medKit", 7003);
        ItemInfo.ingotsID = config.getItemID("ingots", 7004);
        ItemInfo.herbID = config.getItemID("herb", 7005);
        ItemInfo.shaverID = config.getItemID("shaver", 7006);
        ItemInfo.errorID = config.getItemID("error", 7007);
        ItemInfo.emptyCaseID = config.getItemID("emptyCase", 7008);
        ItemInfo.dustID = config.getItemID("dusts", 7009);
        ItemInfo.upgradesID = config.getItemID("upgrades", 7010);
        ItemInfo.peaceTreatyID = config.getItemID("peaceTreaty", 7011);
        ItemInfo.portaCraftID = config.getItemID("portaCraft", 7012);
        ItemInfo.liquidReaderID = config.getItemID("liquidReader", 7013);
        ItemInfo.condenseBagID = config.getItemID("condenseBags", 7014);
        ItemInfo.recordID = config.getItemID("record", 7015);
        ItemInfo.wrenchID = config.getItemID("wrench", 7016);
        ItemInfo.tankUpgradeID = config.getItemID("tankUpgrade", 7017);
    }

    private void configBlockIDs(XMLConfiguration config) {
        BlockInfo.ingotBlockID = config.getBlockID("IngotBlocks", 700);
        BlockInfo.infectedBlockID = config.getBlockID("infectedBlock", 701);
        BlockInfo.compactGenID = config.getBlockID("compackCobGen", 702);
        BlockInfo.fertileBushID = config.getBlockID("fertileBush", 703);
        BlockInfo.magicSandID = config.getBlockID("magicSand", 704);
        BlockInfo.aquaticManipID = config.getBlockID("aquaticManip", 705);
        BlockInfo.clearTankID = config.getBlockID("clearTank", 706);
        BlockInfo.machineFrameID = config.getBlockID("machineFrame", 707);
        BlockInfo.magmaFurnaceID = config.getBlockID("magmaFurnace", 708);
        BlockInfo.treatedWoolID = config.getBlockID("treatedwool", 709);
        BlockInfo.decorID = config.getBlockID("decorativeBlock", 710);
        BlockInfo.glassID = config.getBlockID("glass", 711);
        BlockInfo.airID = config.getBlockID("airBlock", 712);

        BlockInfo.stamaticOreGen = config.getInt("stamaticOreGenRate", 15, ConfigEnum.OTHER);
        BlockInfo.aquaticOreGen = config.getInt("aquaticOreGenRate", 2, ConfigEnum.OTHER);
        BlockInfo.igniousOreGen = config.getInt("igniousOreGenRate", 4, ConfigEnum.OTHER);
        BlockInfo.neoOreGen = config.getInt("neoOreGenRate", 3, ConfigEnum.OTHER);

        BlockInfo.fertileBushRange = config.getInt("fertileBushRange", 5, ConfigEnum.OTHER);
    }

    private static String energyTap = "Number of ticks before the Energy Tap Produces Energy. 20 ticks per second";
    private static String shaverLoot = "Chance of mobs dropping items they're holding using the shaver.";
    private static String shaverExplode = "Chance of creeper exploding if shaver is used on one.";

    private void configureOptions(XMLConfiguration config) {
        MThings.debug = config.getBoolean("debugMode", false, ConfigEnum.GENERAL_CONFIG);
        Ints.energyTapTick = config.getInt("EnergyTapTick", 50, energyTap, ConfigEnum.GENERAL_CONFIG);
        Ints.shaverLootChance = config.getInt("shaverLoot", 15, shaverLoot, ConfigEnum.GENERAL_CONFIG);
        Ints.shaverExplodeChance = config.getInt("shaverExplode", 30, shaverExplode, ConfigEnum.GENERAL_CONFIG);

        if (Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER) {
            int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            if (day >= 20 && day <= 30)
                date = true;
        }
    }

    public static boolean date;

    private void configureKeys(XMLConfiguration config) {
        String keyConfigInfo = "See http://minecraft.gamepedia.com/Key_codes for key codes";

        KeyConfigs.KEY_MOUNT_TOOL_CLEAR = config.getInt("MOUNT_TOOL_CLEAR", Keyboard.KEY_F, keyConfigInfo, ConfigEnum.OTHER);
        KeyConfigs.KEY_LIQUID_READER_MODE_CHANGE = config.getInt("LIQUID_READER_MODE_CHANGE", Keyboard.KEY_R, keyConfigInfo, ConfigEnum.OTHER);
    }
}
