package net.lomeli.mt.core.helper;

import net.lomeli.lomlib.util.ReflectionUtil;

public class DeofHelper {
    public static void makeFieldsPublic() {
        ReflectionUtil.setFieldAccess("net.minecraft.entity.monster.EntityPigZombie", "angerLevel", true, true);
        ReflectionUtil.setFieldAccess("net.minecraft.entity.EntityCreature", "entityToAttack", true, true);

        ReflectionUtil.setFieldAccess("net.minecraft.item.ItemSword", "weaponDamage", true, true);

        ReflectionUtil.setMethodAccess("net.minecraft.entity.EntityLivingBase", "getExperiencePoints", true, true);
    }
}
