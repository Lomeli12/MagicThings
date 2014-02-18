package net.lomeli.mt.entity;

import net.lomeli.mt.MThings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DungeonHooks;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ModEntities {

    private static Type[] typeList = { Type.FOREST, Type.HILLS, Type.SWAMP, Type.JUNGLE, Type.WASTELAND, Type.MAGICAL };

    public static void loadEntities() {
        MThings.proxy.registerRenders();
        loadHostiles();
    }

    private static void loadHostiles() {
        registerEntity(EntityClicker.class, "Clicker", 0x4F881E, 0xC99A6D);
        addOverWorldSpawn(EntityClicker.class, 15, 1, 2);
        LanguageRegistry.instance().addStringLocalization("entity.Clicker.name", "Clicker");

        DungeonHooks.addDungeonMob("Clicker", 50);
    }

    public static void addOverWorldSpawn(Class<? extends EntityLiving> entityClass, int spawnprob, int min, int max) {
        for (int i = 0; i < typeList.length; i++) {
            EntityRegistry.addSpawn(entityClass, spawnprob, min, max, EnumCreatureType.creature, BiomeDictionary.getBiomesForType(typeList[i]));
        }
    }

    @SuppressWarnings("unchecked")
    private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bkEggColor, int fgEggColor) {
        EntityRegistry.instance();
        int id = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, entityName, id);
        EntityList.entityEggs.put(Integer.valueOf(id), new EntityEggInfo(id, bkEggColor, fgEggColor));
    }
}
