package net.lomeli.mt.item;

import net.lomeli.mt.item.special.ItemCondenseBag;
import net.lomeli.mt.item.special.ItemError;
import net.lomeli.mt.item.special.ItemFluidReader;
import net.lomeli.mt.item.special.ItemFlyingRing;
import net.lomeli.mt.item.special.ItemMedKit;
import net.lomeli.mt.item.special.ItemPeaceTreaty;
import net.lomeli.mt.item.special.ItemPortaCraft;
import net.lomeli.mt.item.special.ItemShaver;
import net.lomeli.mt.item.special.ItemWrench;
import net.lomeli.mt.lib.ItemInfo;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item standardItem, material, ingots, herb, dusts, emptyCase;

    public static Item flyingRing, medKit, shaver, error, upgrades, peaceTreaty, portaCraft, liquidReader, condenseBag, record, wrench,
            tankUpgrade;

    public static ItemStack conBag, exBag;
    public static ItemStack[] itemArray, ingotArray, herbArray, dustArray, materialArray;

    public static void loadItems() {
        itemArray = new ItemStack[2];
        ingotArray = new ItemStack[5];
        herbArray = new ItemStack[4];
        dustArray = new ItemStack[9];
        materialArray = new ItemStack[3];
        
        herb = new ItemHerb(ItemInfo.herbID).setUnlocalizedName("Herb");
        dusts = new ItemMetaData(ItemInfo.dustID, "dust_", 9).setUnlocalizedName("Dusts");
        standardItem = new ItemMetaData(ItemInfo.standardItemsID, "item_", 2).setUnlocalizedName("Items");
        ingots = new ItemMetaData(ItemInfo.ingotsID, "ingot_", 5).setUnlocalizedName("Ingots");
        material = new ItemMetaData(ItemInfo.materialID, "material_", 3).setUnlocalizedName("Materials");
        condenseBag = new ItemCondenseBag(ItemInfo.condenseBagID).setUnlocalizedName("CondenseBag");
        
        emptyCase = new ItemMT(ItemInfo.emptyCaseID, "emptycase").setUnlocalizedName("EmptyCase");
        flyingRing = new ItemFlyingRing(ItemInfo.flyingRingID).setUnlocalizedName("FlyingRing");
        medKit = new ItemMedKit(ItemInfo.medKitID).setUnlocalizedName("MedKits");
        error = new ItemError(ItemInfo.errorID).setUnlocalizedName("Error");
        peaceTreaty = new ItemPeaceTreaty(ItemInfo.peaceTreatyID).setUnlocalizedName("PeaceTreaty");
        portaCraft = new ItemPortaCraft(ItemInfo.portaCraftID).setUnlocalizedName("PortaCraft");
        record = new ItemCavernDisc(ItemInfo.recordID).setUnlocalizedName("CavernDisc");
        
        liquidReader = new ItemFluidReader(ItemInfo.liquidReaderID).setUnlocalizedName("FluidReader");
        wrench = new ItemWrench(ItemInfo.wrenchID).setUnlocalizedName("Wrench");
        tankUpgrade = new ItemTankUpgrade(ItemInfo.tankUpgradeID).setUnlocalizedName("TankUpgrade");
        shaver = new ItemShaver(ItemInfo.shaverID).setUnlocalizedName("Shaver");
        
        for(int i = 0; i < 9; i++){
            dustArray[i] = new ItemStack(dusts, 1, i);
            if(i < 5)
                ingotArray[i] = new ItemStack(ingots, 1, i);
            if(i < 4)
                herbArray[i] = new ItemStack(herb, 1, i);
            if(i < 3)
                materialArray[i] = new ItemStack(material, 1, i);
            if(i < 2)
                itemArray[i] = new ItemStack(standardItem, 1, i);
        }

        conBag = new ItemStack(condenseBag, 1, 0);
        exBag = new ItemStack(condenseBag, 1, 1);

        GameRegistry.registerItem(herb, "Herbs");
        GameRegistry.registerItem(dusts, "Dusts");
        GameRegistry.registerItem(condenseBag, "Condense Bags");
        GameRegistry.registerItem(ingots, "Ingots");
        GameRegistry.registerItem(material, "Materials");

        MinecraftForge.addGrassSeed(herbArray[0], 15);
        MinecraftForge.addGrassSeed(herbArray[1], 10);
        MinecraftForge.addGrassSeed(herbArray[2], 5);
        MinecraftForge.addGrassSeed(herbArray[3], 3);
        
        OreDictionary.registerOre("ingotStamatic", ingotArray[0]);
        OreDictionary.registerOre("ingotAqua", ingotArray[1]);
        OreDictionary.registerOre("ingotIgnious", ingotArray[2]);
        OreDictionary.registerOre("neoGem", ingotArray[3]);
        OreDictionary.registerOre("dustStamatic", dustArray[6]);
        OreDictionary.registerOre("dustAqua", dustArray[7]);
        OreDictionary.registerOre("dustIgnious", dustArray[8]);
    }
}
