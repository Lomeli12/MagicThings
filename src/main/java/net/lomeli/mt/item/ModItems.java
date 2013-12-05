package net.lomeli.mt.item;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.mt.lib.ItemInfo;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class ModItems {
    public static Item ironStick, ironRing, baseMaterial, advMaterial, quantumMaterial, ingotStamatic, herb, ingotAqua, dustStamatic, dustAqua, emptyCase, neoGem, ingotIgnious,
            dustIgnious, purifiedDust;

    public static ItemStack stamatic, aquatic, ignious, stamaticOre, aquaticOre, igniousOre;

    public static ItemStack green, blue, red, yellow;

    public static Item flyingRing, medKit, shaver, error, energyTap, upgrades, magnet, peaceTreaty, portaCraft, mountTool, toast, liquidReader, condenseBag, record, wrench, crank,
            wandCommand, ikenoBlade, tankUpgrade;

    public static ItemStack conBag, exBag;

    public static void loadItems() {
        ironStick = new ItemMT(ItemInfo.ironStickID, "ironstick").setUnlocalizedName("IronStick");
        ironRing = new ItemMT(ItemInfo.ironRingID, "ironband").setUnlocalizedName("IronBand");
        baseMaterial = new ItemMT(ItemInfo.baseMaterialID, "basematerial").setUnlocalizedName("BaseMaterial");
        advMaterial = new ItemMT(ItemInfo.advMaterialID, "advmaterial").setUnlocalizedName("AdvMaterial");
        quantumMaterial = new ItemMT(ItemInfo.quantumMaterialID, "quantummaterial").setUnlocalizedName("QuantumMaterial");
        ingotStamatic = new ItemMT(ItemInfo.ingotStamaticID, "ingotstamatic").setUnlocalizedName("IngotStamatic");
        herb = new ItemHerb(ItemInfo.herbID).setUnlocalizedName("Herb");
        ingotAqua = new ItemMT(ItemInfo.ingotAquaID, "ingotaquatic").setUnlocalizedName("IngotAquatic");
        dustStamatic = new ItemMT(ItemInfo.dustStamaticID, "stamaticdust").setUnlocalizedName("DustStamatic");
        dustAqua = new ItemMT(ItemInfo.dustAquaID, "aquaticdust").setUnlocalizedName("DustAquatic");
        emptyCase = new ItemMT(ItemInfo.emptyCaseID, "emptycase").setUnlocalizedName("EmptyCase");
        neoGem = new ItemMT(ItemInfo.neoGemID, "neoGem").setUnlocalizedName("NeoGem");
        ingotIgnious = new ItemMT(ItemInfo.ingotIgniousID, "ingotignious").setUnlocalizedName("IngotIgnious");
        dustIgnious = new ItemMT(ItemInfo.dustIgniousID, "igniousdust").setUnlocalizedName("DustIgnious");
        purifiedDust = new ItemPurifiedDust(ItemInfo.purifiedDustID).setUnlocalizedName("PurifiedDust");

        flyingRing = new ItemFlyingRing(ItemInfo.flyingRingID).setUnlocalizedName("FlyingRing");
        medKit = new ItemMedKit(ItemInfo.medKitID).setUnlocalizedName("MedKits");
        shaver = new ItemShaver(ItemInfo.shaverID).setUnlocalizedName("Shaver");
        error = new ItemError(ItemInfo.errorID).setUnlocalizedName("Error");
        energyTap = new ItemEnergyTap(ItemInfo.energyTapID).setUnlocalizedName("EnergyTap");
        // Upgrades
        magnet = new ItemMagnet(ItemInfo.magnetID).setUnlocalizedName("Magnet");
        peaceTreaty = new ItemPeaceTreaty(ItemInfo.peaceTreatyID).setUnlocalizedName("PeaceTreaty");
        portaCraft = new ItemPortaCraft(ItemInfo.portaCraftID).setUnlocalizedName("PortaCraft");
        mountTool = new ItemMountTool(ItemInfo.mountToolID).setUnlocalizedName("MountTool");
        toast = new ItemEverlastingToast(ItemInfo.toastID).setUnlocalizedName("Toast");
        liquidReader = new ItemFluidReader(ItemInfo.liquidReaderID).setUnlocalizedName("FluidReader");
        condenseBag = new ItemCondenseBag(ItemInfo.condenseBagID).setUnlocalizedName("CondenseBag");
        record = new ItemCavernDisc(ItemInfo.recordID).setUnlocalizedName("CavernDisc");
        wrench = new ItemWrench(ItemInfo.wrenchID).setUnlocalizedName("Wrench");
        crank = new ItemCrank(ItemInfo.crankID, "handCrank").setUnlocalizedName("HandCrank");
        tankUpgrade = new ItemTankUpgrade(ItemInfo.tankUpgradeID).setUnlocalizedName("TankUpgrade");
        ikenoBlade = new ItemIkenoBlade(ItemInfo.ikenoID).setUnlocalizedName("IBlade");

        green = new ItemStack(herb, 1, 0);
        blue = new ItemStack(herb, 1, 1);
        red = new ItemStack(herb, 1, 2);
        yellow = new ItemStack(herb, 1, 3);

        stamatic = new ItemStack(purifiedDust, 1, 0);
        aquatic = new ItemStack(purifiedDust, 1, 1);
        ignious = new ItemStack(purifiedDust, 1, 2);
        stamaticOre = new ItemStack(purifiedDust, 1, 3);
        aquaticOre = new ItemStack(purifiedDust, 1, 4);
        igniousOre = new ItemStack(purifiedDust, 1, 5);

        conBag = new ItemStack(condenseBag, 1, 0);
        exBag = new ItemStack(condenseBag, 1, 1);

        GameRegistry.registerItem(herb, "Herbs");
        GameRegistry.registerItem(purifiedDust, "Purified Dusts");
        GameRegistry.registerItem(condenseBag, "Condense Bag");

        MinecraftForge.addGrassSeed(green, 10);
        MinecraftForge.addGrassSeed(blue, 10);
        MinecraftForge.addGrassSeed(red, 10);
        MinecraftForge.addGrassSeed(yellow, 30);

        OreDictionary.registerOre("ingotStamatic", ingotStamatic);
        OreDictionary.registerOre("ingotAqua", ingotAqua);
        OreDictionary.registerOre("ingotIgnious", ingotIgnious);
        OreDictionary.registerOre("neoGem", neoGem);
        OreDictionary.registerOre("dustStamatic", dustStamatic);
        OreDictionary.registerOre("dustAqua", dustAqua);
        OreDictionary.registerOre("dustIgnious", dustIgnious);
    }
}
