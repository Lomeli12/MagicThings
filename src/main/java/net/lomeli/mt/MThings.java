package net.lomeli.mt;

import java.util.logging.Level;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

import net.lomeli.lomlib.util.LogHelper;
import net.lomeli.lomlib.util.UpdateHelper;

import net.lomeli.mt.addon.Addons;
import net.lomeli.mt.addon.MorphAddon;
import net.lomeli.mt.block.ModBlocks;
import net.lomeli.mt.core.CommonProxy;
import net.lomeli.mt.core.Config;
import net.lomeli.mt.core.CreativeTabMT;
import net.lomeli.mt.core.handler.CraftingHandler;
import net.lomeli.mt.core.handler.EntityLivingHandler;
import net.lomeli.mt.core.handler.FlyingTickHandler;
import net.lomeli.mt.core.handler.GuiHandler;
import net.lomeli.mt.core.handler.PlayerEntityInteractionHandler;
import net.lomeli.mt.core.handler.PlayerItemHandler;
import net.lomeli.mt.core.handler.SoundHandler;
import net.lomeli.mt.core.helper.DeofHelper;
import net.lomeli.mt.core.world.WorldMTGen;
import net.lomeli.mt.entity.ModEntities;
import net.lomeli.mt.item.ModItems;
import net.lomeli.mt.lib.Ints;
import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.potion.PotionInfection;
import net.lomeli.mt.recipes.ModRecipes;

import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION, dependencies = "required-after:LomLib@[1.0.8,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class MThings {
    @Mod.Instance(Strings.MOD_ID)
    public static MThings instance;

    public static CreativeTabs modtab = new CreativeTabMT(CreativeTabs.getNextID(), Strings.MOD_NAME);

    public static Config modConfig;

    public static boolean debug;
    public static LogHelper logger = new LogHelper(Strings.MOD_NAME);
    public static UpdateHelper updater = new UpdateHelper();

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        DeofHelper.makeFieldsPublic();

        modConfig = new Config(event.getSuggestedConfigurationFile());

        modConfig.configureMod();

        try {
            updater.check(Strings.MOD_NAME, Strings.UPDATE_XML, Ints.MAJOR, Ints.MINOR, Ints.REVISION);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Could not connect to update server!");
        }
        PotionInfection.setUp();

        ModItems.loadItems();
        ModBlocks.loadBlocks();

        if (event.getSide().isClient())
            TickRegistry.registerTickHandler(new FlyingTickHandler(), Side.CLIENT);

        proxy.registerTickHandlers();
        proxy.registerIcons();

        GameRegistry.registerWorldGenerator(new WorldMTGen());

        Addons.initAddons();
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.registerTileEntites();

        MinecraftForge.EVENT_BUS.register(new PlayerItemHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerEntityInteractionHandler());
        MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());
        if (event.getSide().isClient())
            MinecraftForge.EVENT_BUS.register(new SoundHandler());

        NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());

        GameRegistry.registerCraftingHandler(new CraftingHandler());

        ModEntities.loadEntities();
    }

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        ModRecipes.loadRecipes();
        MorphAddon.registerAbilities();
    }
}
