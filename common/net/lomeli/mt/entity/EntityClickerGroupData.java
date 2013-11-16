package net.lomeli.mt.entity;

import net.minecraft.entity.EntityLivingData;

public class EntityClickerGroupData implements EntityLivingData {
    public boolean field_142048_a;
    public boolean field_142046_b;

    final EntityClicker field_142047_c;

    public EntityClickerGroupData(EntityClicker entity, boolean par2, boolean par3) {
        this.field_142047_c = entity;
        this.field_142048_a = false;
        this.field_142046_b = false;
        this.field_142048_a = par2;
        this.field_142046_b = par3;
    }

    EntityClickerGroupData(EntityClicker entity, boolean par2, boolean par3, EntityClickerINNER1 par4EntityZombieINNER1) {
        this(entity, par2, par3);
    }
}
