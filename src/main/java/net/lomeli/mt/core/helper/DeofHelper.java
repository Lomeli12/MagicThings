package net.lomeli.mt.core.helper;

import net.lomeli.lomlib.asm.DeofUtil;

public class DeofHelper {
    public static void makeFieldsPublic() {
        DeofUtil.setFieldAccess("net.minecraft.entity.monster.EntityPigZombie", "angerLevel", true, true);
        DeofUtil.setFieldAccess("net.minecraft.entity.EntityCreature", "entityToAttack", true, true);

        DeofUtil.setFieldAccess("net.minecraft.item.ItemSword", "weaponDamage", true, true);

        DeofUtil.setMethodAccess("net.minecraft.entity.EntityLivingBase", "getExperiencePoints", true, true);
    }
}
