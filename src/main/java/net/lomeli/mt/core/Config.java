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

    public Config(File configFile) {
        this.configFile = configFile;
    }

    public void configureMod() {
        XMLConfiguration config = new XMLConfiguration(this.configFile);

        config.loadXml();

        configItemIDs(config);
        configBlockIDs(config);
        configureOptions(config);
        configureKeys(config);

        config.saveXML();
    }

    private void configItemIDs(XMLConfiguration config) {
        ItemInfo.ironStickID = config.getInt("ironStick", 7000, ConfigEnum.ITEM_ID);
        ItemInfo.ironRingID = config.getInt("ironRing", 7001, ConfigEnum.ITEM_ID);
        ItemInfo.flyingRingID = config.getInt("flyingRing", 7002, ConfigEnum.ITEM_ID);
        ItemInfo.baseMaterialID = config.getInt("baseMaterial", 7003, ConfigEnum.ITEM_ID);
        ItemInfo.advMaterialID = config.getInt("advMaterial", 7004, ConfigEnum.ITEM_ID);
        ItemInfo.quantumMaterialID = config.getInt("quantumMaterial", 7005, ConfigEnum.ITEM_ID);
        ItemInfo.medKitID = config.getInt("medKit", 7006, ConfigEnum.ITEM_ID);
        ItemInfo.ingotStamaticID = config.getInt("ingotStamatic", 7007, ConfigEnum.ITEM_ID);
        ItemInfo.herbID = config.getInt("herb", 7008, ConfigEnum.ITEM_ID);
        ItemInfo.shaverID = config.getInt("shaver", 7009, ConfigEnum.ITEM_ID);
        ItemInfo.errorID = config.getInt("error", 7010, ConfigEnum.ITEM_ID);
        ItemInfo.ingotAquaID = config.getInt("ingotAqua", 7011, ConfigEnum.ITEM_ID);
        ItemInfo.dustStamaticID = config.getInt("dustStamatic", 7012, ConfigEnum.ITEM_ID);
        ItemInfo.dustAquaID = config.getInt("dustAqua", 7013, ConfigEnum.ITEM_ID);
        ItemInfo.emptyCaseID = config.getInt("emptyCase", 7014, ConfigEnum.ITEM_ID);
        ItemInfo.energyTapID = config.getInt("energyTap", 7015, ConfigEnum.ITEM_ID);
        ItemInfo.neoGemID = config.getInt("neoGem", 7016, ConfigEnum.ITEM_ID);
        ItemInfo.ingotIgniousID = config.getInt("ingotIgnious", 7017, ConfigEnum.ITEM_ID);
        ItemInfo.dustIgniousID = config.getInt("dustIgnious", 7018, ConfigEnum.ITEM_ID);
        ItemInfo.upgradesID = config.getInt("upgrades", 7019, ConfigEnum.ITEM_ID);
        ItemInfo.purifiedDustID = config.getInt("purifiedDust", 7020, ConfigEnum.ITEM_ID);
        ItemInfo.magnetID = config.getInt("magnet", 7021, ConfigEnum.ITEM_ID);
        ItemInfo.peaceTreatyID = config.getInt("peaceTreaty", 7022, ConfigEnum.ITEM_ID);
        ItemInfo.portaCraftID = config.getInt("portaCraft", 7023, ConfigEnum.ITEM_ID);
        ItemInfo.mountToolID = config.getInt("mountTool", 7024, ConfigEnum.ITEM_ID);
        ItemInfo.toastID = config.getInt("toast", 7025, ConfigEnum.ITEM_ID);
        ItemInfo.liquidReaderID = config.getInt("liquidReader", 7026, ConfigEnum.ITEM_ID);
        ItemInfo.condenseBagID = config.getInt("condenseBags", 7027, ConfigEnum.ITEM_ID);
        ItemInfo.recordID = config.getInt("record", 7028, ConfigEnum.ITEM_ID);
        ItemInfo.wrenchID = config.getInt("wrench", 7029, ConfigEnum.ITEM_ID);
        ItemInfo.crankID = config.getInt("handCrank", 7030, ConfigEnum.ITEM_ID);
        ItemInfo.tankUpgradeID = config.getInt("tankUpgrade", 7031, ConfigEnum.ITEM_ID);
        ItemInfo.ikenoID = config.getInt("IkenoBlade", 7032, ConfigEnum.ITEM_ID);

        ItemInfo.magnetDurability = config.getInt("magnetDurability", 576, ConfigEnum.OTHER);
    }

    private void configBlockIDs(XMLConfiguration config) {
        BlockInfo.stamaticOreID = config.getInt("stamaticOre", 700, ConfigEnum.BLOCK_ID);
        BlockInfo.aquaticOreID = config.getInt("aquaticOre", 701, ConfigEnum.BLOCK_ID);
        BlockInfo.igniousOreID = config.getInt("igniousOre", 702, ConfigEnum.BLOCK_ID);
        BlockInfo.neoOreID = config.getInt("neoOre", 703, ConfigEnum.BLOCK_ID);
        BlockInfo.chargeStationID = config.getInt("chargeStation", 704, ConfigEnum.BLOCK_ID);
        BlockInfo.unwindID = config.getInt("unwinder", 705, ConfigEnum.BLOCK_ID);
        BlockInfo.mobFarmerID = config.getInt("mobFarmer", 706, ConfigEnum.BLOCK_ID);
        BlockInfo.infectedBlockID = config.getInt("infectedBlock", 707, ConfigEnum.BLOCK_ID);
        BlockInfo.compactGenID = config.getInt("compackCobGen", 708, ConfigEnum.BLOCK_ID);
        BlockInfo.fertileBushID = config.getInt("fertileBush", 709, ConfigEnum.BLOCK_ID);
        BlockInfo.magicSandID = config.getInt("magicSand", 710, ConfigEnum.BLOCK_ID);
        BlockInfo.aquaticManipID = config.getInt("aquaticManip", 711, ConfigEnum.BLOCK_ID);
        BlockInfo.clearTankID = config.getInt("clearTank", 712, ConfigEnum.BLOCK_ID);
        BlockInfo.machineFrameID = config.getInt("machineFrame", 713, ConfigEnum.BLOCK_ID);
        BlockInfo.magmaFurnaceID = config.getInt("magmaFurnace", 714, ConfigEnum.BLOCK_ID);
        BlockInfo.advFrameID = config.getInt("advFrame", 715, ConfigEnum.BLOCK_ID);
        BlockInfo.altarID = config.getInt("Altar", 716, ConfigEnum.BLOCK_ID);
        BlockInfo.treatedWoolID = config.getInt("treatedwool", 717, ConfigEnum.BLOCK_ID);

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
