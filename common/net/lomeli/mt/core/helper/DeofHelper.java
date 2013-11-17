package net.lomeli.mt.core.helper;

import net.lomeli.lomlib.util.DeofUtil;

public class DeofHelper {
    public static void makeFieldsPublic() {
        DeofUtil.setFieldsAccess("net.minecraft.entity.ai.EntityAINearestAttackableTarget", new String[] { "targetClass",
                "targetChance", "theNearestAttackableTargetSorter", "targetEntitySelector", "targetEntity" }, new boolean[] {
                true, true, true, true, true }, true);

        DeofUtil.setFieldAccess("net.minecraft.entity.monster.EntityPigZombie", "angerLevel", true, true);
        DeofUtil.setFieldAccess("net.minecraft.entity.EntityCreature", "entityToAttack", true, true);

        DeofUtil.setFieldAccess("net.minecraft.item.ItemSword", "weaponDamage", true, true);

        DeofUtil.setMethodAccess("net.minecraft.block.BlockChest", "isThereANeighborChest", true, true);
        DeofUtil.setMethodAccess("net.minecraft.entity.EntityLivingBase", "getExperiencePoints", true, true);
    }
}
