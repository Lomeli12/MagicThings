package net.lomeli.mt.core;

import java.io.File;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.BlockInfo;
import net.lomeli.mt.lib.Ints;
import net.lomeli.mt.lib.Ints.KeyConfigs;
import net.lomeli.mt.lib.ItemInfo;

import net.minecraftforge.common.Configuration;

public class Config {

    private File configFile;

    public Config(File configFile) {
        this.configFile = configFile;
    }

    public void configureMod() {
        configItemIDs();
        configBlockIDs();
        configureOptions();
        configureKeys();
    }

    private void configItemIDs() {
        Configuration config = new Configuration(configFile);

        config.load();

        String item = "Items";
        String info = item + "Info";

        ItemInfo.ironStickID = config.get(item, "ironStick", 7000).getInt(7000);
        ItemInfo.ironRingID = config.get(item, "ironRing", 7001).getInt(7001);
        ItemInfo.flyingRingID = config.get(item, "flyingRing", 7002).getInt(7002);
        ItemInfo.baseMaterialID = config.get(item, "baseMaterial", 7003).getInt(7003);
        ItemInfo.advMaterialID = config.get(item, "advMaterial", 7004).getInt(7004);
        ItemInfo.quantumMaterialID = config.get(item, "quantumMaterial", 7005).getInt(7005);
        ItemInfo.medKitID = config.get(item, "medKit", 7006).getInt(7006);
        ItemInfo.ingotStamaticID = config.get(item, "ingotStamatic", 7007).getInt(7007);
        ItemInfo.herbID = config.get(item, "herb", 7008).getInt(7008);
        ItemInfo.shaverID = config.get(item, "shaver", 7009).getInt(7009);
        ItemInfo.errorID = config.get(item, "error", 7010).getInt(7010);
        ItemInfo.ingotAquaID = config.get(item, "ingotAqua", 7011).getInt(7011);
        ItemInfo.dustStamaticID = config.get(item, "dustStamatic", 7012).getInt(7012);
        ItemInfo.dustAquaID = config.get(item, "dustAqua", 7013).getInt(7013);
        ItemInfo.emptyCaseID = config.get(item, "emptyCase", 7014).getInt(7014);
        ItemInfo.energyTapID = config.get(item, "energyTap", 7015).getInt(7015);
        ItemInfo.neoGemID = config.get(item, "neoGem", 7016).getInt(7016);
        ItemInfo.ingotIgniousID = config.get(item, "ingotIgnious", 7017).getInt(7017);
        ItemInfo.dustIgniousID = config.get(item, "dustIgnious", 7018).getInt(7018);
        ItemInfo.upgradesID = config.get(item, "upgrades", 7019).getInt(7019);
        ItemInfo.purifiedDustID = config.get(item, "purifiedDust", 7020).getInt(7020);
        ItemInfo.magnetID = config.get(item, "magnet", 7021).getInt(7021);
        ItemInfo.peaceTreatyID = config.get(item, "peaceTreaty", 7022).getInt(7022);
        ItemInfo.portaCraftID = config.get(item, "portaCraft", 7023).getInt(7023);
        ItemInfo.mountToolID = config.get(item, "mountTool", 7024).getInt(7024);
        ItemInfo.toastID = config.get(item, "toast", 7025).getInt(7025);
        ItemInfo.liquidReaderID = config.get(item, "liquidReader", 7026).getInt(7026);
        ItemInfo.condenseBagID = config.get(item, "condenseBags", 7027).getInt(7027);
        ItemInfo.recordID = config.get(item, "record", 7028).getInt(7028);
        ItemInfo.wrenchID = config.get(item, "wrench", 7029).getInt(7029);
        ItemInfo.crankID = config.get(item, "handCrank", 7030).getInt(7030);
        ItemInfo.tankUpgradeID = config.get(item, "tankUpgrade", 7031).getInt(7031);
        ItemInfo.ikenoID = config.get(item, "IkenoBlade", 7032).getInt(7032);

        ItemInfo.magnetDurability = config.get(info, "magnetDurability", 576).getInt(576);

        config.save();
    }

    private void configBlockIDs() {
        Configuration config = new Configuration(configFile);

        config.load();

        String block = "Blocks";
        String info = block + "Info";

        BlockInfo.stamaticOreID = config.get(block, "stamaticOre", 700).getInt(700);
        BlockInfo.aquaticOreID = config.get(block, "aquaticOre", 701).getInt(701);
        BlockInfo.igniousOreID = config.get(block, "igniousOre", 702).getInt(702);
        BlockInfo.neoOreID = config.get(block, "neoOre", 703).getInt(703);
        BlockInfo.chargeStationID = config.get(block, "chargeStation", 704).getInt(704);
        BlockInfo.unwindID = config.get(block, "unwinder", 705).getInt(705);
        BlockInfo.mobFarmerID = config.get(block, "mobFarmer", 706).getInt(706);
        BlockInfo.infectedBlockID = config.get(block, "infectedBlock", 707).getInt(707);
        BlockInfo.compactGenID = config.get(block, "compackCobGen", 708).getInt(708);
        BlockInfo.fertileBushID = config.get(block, "fertileBush", 709).getInt(709);
        BlockInfo.magicSandID = config.get(block, "magicSand", 710).getInt(710);
        BlockInfo.aquaticManipID = config.get(block, "aquaticManip", 711).getInt(711);
        BlockInfo.clearTankID = config.get(block, "clearTank", 712).getInt(712);
        BlockInfo.machineFrameID = config.get(block, "machineFrame", 713).getInt(713);
        BlockInfo.magmaFurnaceID = config.get(block, "magmaFurnace", 714).getInt(714);
        BlockInfo.advFrameID = config.get(block, "advFrame", 715).getInt(715);
        BlockInfo.altarID = config.get(block, "Altar", 716).getInt(716);
        BlockInfo.treatedWoolID = config.get(block, "treatedwool", 717).getInt(717);

        BlockInfo.stamaticOreGen = config.get(info, "stamaticOreGenRate", 15).getInt(15);
        BlockInfo.aquaticOreGen = config.get(info, "aquaticOreGenRate", 2).getInt(2);
        BlockInfo.igniousOreGen = config.get(info, "igniousOreGenRate", 4).getInt(4);
        BlockInfo.neoOreGen = config.get(info, "neoOreGenRate", 3).getInt(3);

        BlockInfo.fertileBushRange = config.get(info, "fertileBushRange", 5).getInt(5);

        config.save();
    }

    private static String energyTap = "Number of ticks before the Energy Tap Produces Energy.";
    private static String shaverLoot = "Chance of mobs dropping items they're holding using the shaver.";
    private static String shaverExplode = "Chance of creeper exploding if shaver is used on one.";

    private void configureOptions() {
        Configuration config = new Configuration(configFile);

        config.load();

        String options = "Options";

        MThings.debug = config.get(options, "debugMode", false).getBoolean(false);
        Ints.energyTapTick = config.get(options, "EnergyTapTick", 50, energyTap).getInt(50);
        Ints.shaverLootChance = config.get(options, "shaverLoot", 15, shaverLoot).getInt(5);
        Ints.shaverExplodeChance = config.get(options, "shaverExplode", 30, shaverExplode).getInt(30);

        config.save();
        
        if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 4 && Calendar.getInstance().get(Calendar.MONTH) == Calendar.NOVEMBER)
            date = true;
    }
    
    public static boolean date;

    private void configureKeys() {
        Configuration config = new Configuration(configFile);

        config.load();

        String keys = "KeyConfigs";

        config.addCustomCategoryComment(keys, "See http://minecraft.gamepedia.com/Key_codes for key codes");

        KeyConfigs.KEY_MOUNT_TOOL_CLEAR = config.get(keys, "MOUNT_TOOL_CLEAR", Keyboard.KEY_F).getInt(Keyboard.KEY_F);
        KeyConfigs.KEY_LIQUID_READER_MODE_CHANGE = config.get(keys, "LIQUID_READER_MODE_CHANGE", Keyboard.KEY_R).getInt(
                Keyboard.KEY_R);

        config.save();
    }
}
