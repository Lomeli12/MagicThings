package net.lomeli.mt.core;

import java.io.File;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Ints;
import net.lomeli.mt.lib.Ints.KeyConfigs;
import net.lomeli.mt.lib.ItemInfo;

import net.lomeli.lomlib.util.XMLConfiguration;
import net.lomeli.lomlib.util.XMLConfiguration.ConfigEnum;

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
        ItemInfo.ironStickID = config.getItemID("ironStick", 7000);
        ItemInfo.ironRingID = config.getItemID("ironRing", 7001);
        ItemInfo.flyingRingID = config.getItemID("flyingRing", 7002);
        ItemInfo.baseMaterialID = config.getItemID("baseMaterial", 7003);
        ItemInfo.advMaterialID = config.getItemID("advMaterial", 7004);
        ItemInfo.quantumMaterialID = config.getItemID("quantumMaterial", 7005);
        ItemInfo.medKitID = config.getItemID("medKit", 7006);
        ItemInfo.ingotStamaticID = config.getItemID("ingotStamatic", 7007);
        ItemInfo.herbID = config.getItemID("herb", 7008);
        ItemInfo.shaverID = config.getItemID("shaver", 7009);
        ItemInfo.errorID = config.getItemID("error", 7010);
        ItemInfo.ingotAquaID = config.getItemID("ingotAqua", 7011);
        ItemInfo.dustStamaticID = config.getItemID("dustStamatic", 7012);
        ItemInfo.dustAquaID = config.getItemID("dustAqua", 7013);
        ItemInfo.emptyCaseID = config.getItemID("emptyCase", 7014);
        ItemInfo.energyTapID = config.getItemID("energyTap", 7015);
        ItemInfo.neoGemID = config.getItemID("neoGem", 7016);
        ItemInfo.ingotIgniousID = config.getItemID("ingotIgnious", 7017);
        ItemInfo.dustIgniousID = config.getItemID("dustIgnious", 7018);
        ItemInfo.upgradesID = config.getItemID("upgrades", 7019);
        ItemInfo.purifiedDustID = config.getItemID("purifiedDust", 7020);
        ItemInfo.magnetID = config.getItemID("magnet", 7021);
        ItemInfo.peaceTreatyID = config.getItemID("peaceTreaty", 7022);
        ItemInfo.portaCraftID = config.getItemID("portaCraft", 7023);
        ItemInfo.mountToolID = config.getItemID("mountTool", 7024);
        ItemInfo.toastID = config.getItemID("toast", 7025);
        ItemInfo.liquidReaderID = config.getItemID("liquidReader", 7026);
        ItemInfo.condenseBagID = config.getItemID("condenseBags", 7027);
        ItemInfo.recordID = config.getItemID("record", 7028);
        ItemInfo.wrenchID = config.getItemID("wrench", 7029);
        ItemInfo.crankID = config.getItemID("handCrank", 7030);
        ItemInfo.tankUpgradeID = config.getItemID("tankUpgrade", 7031);
        ItemInfo.ikenoID = config.getItemID("IkenoBlade", 7032);
        ItemInfo.smokedBrickID = config.getItemID("SmokedBrick", 7033);
        ItemInfo.cableItemID = config.getItemID("cableItem", 7034);
        ItemInfo.jwMeterID = config.getItemID("jwMeter", 7035);

        ItemInfo.magnetDurability = config.getInt("magnetDurability", 576, ConfigEnum.OTHER);
    }

    private void configBlockIDs(XMLConfiguration config) {
        BlockInfo.stamaticOreID = config.getBlockID("stamaticOre", 700);
        BlockInfo.aquaticOreID = config.getBlockID("aquaticOre", 701);
        BlockInfo.igniousOreID = config.getBlockID("igniousOre", 702);
        BlockInfo.neoOreID = config.getBlockID("neoOre", 703);
        BlockInfo.chargeStationID = config.getBlockID("chargeStation", 704);
        BlockInfo.unwindID = config.getBlockID("unwinder", 705);
        BlockInfo.mobFarmerID = config.getBlockID("mobFarmer", 706);
        BlockInfo.infectedBlockID = config.getBlockID("infectedBlock", 707);
        BlockInfo.compactGenID = config.getBlockID("compackCobGen", 708);
        BlockInfo.fertileBushID = config.getBlockID("fertileBush", 709);
        BlockInfo.magicSandID = config.getBlockID("magicSand", 710);
        BlockInfo.aquaticManipID = config.getBlockID("aquaticManip", 711);
        BlockInfo.clearTankID = config.getBlockID("clearTank", 712);
        BlockInfo.machineFrameID = config.getBlockID("machineFrame", 713);
        BlockInfo.magmaFurnaceID = config.getBlockID("magmaFurnace", 714);
        BlockInfo.advFrameID = config.getBlockID("advFrame", 715);
        BlockInfo.altarID = config.getBlockID("Altar", 716);
        BlockInfo.treatedWoolID = config.getBlockID("treatedwool", 717);
        BlockInfo.decorID = config.getBlockID("decorativeBlock", 718);
        BlockInfo.coalGenID = config.getBlockID("coalGen", 719);
        BlockInfo.cableID = config.getBlockID("cable", 720);
        BlockInfo.bat1ID = config.getBlockID("batTier1", 721);
        BlockInfo.bat2ID = config.getBlockID("batTier2", 722);
        BlockInfo.bat3ID = config.getBlockID("batTier3", 723);
        BlockInfo.glassID = config.getBlockID("glass", 724);

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
