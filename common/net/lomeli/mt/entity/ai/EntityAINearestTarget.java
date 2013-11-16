package net.lomeli.mt.entity.ai;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAINearestTarget extends EntityAINearestAttackableTarget {

    @SuppressWarnings("rawtypes")
    public EntityAINearestTarget(EntityCreature par1EntityCreature, Class par2Class, int par3, boolean par4) {
        super(par1EntityCreature, par2Class, par3, par4);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean shouldExecute() {
        if(this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
            return false;
        else {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.selectEntitiesWithinAABB(this.targetClass,
                    this.taskOwner.boundingBox.expand(d0, 4.0D, d0), this.targetEntitySelector);
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if(list.isEmpty())
                return false;
            else {
                EntityLivingBase potentialTarget = (EntityLivingBase) list.get(0);
                if(potentialTarget instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) potentialTarget;
                    if((!player.isSneaking() && (player.motionX != 0 || player.motionY != 0 || player.motionY != 0))
                            || player.isEating()) {
                        this.targetEntity = potentialTarget;
                        return true;
                    }
                }else if(potentialTarget.motionX != 0 || potentialTarget.motionY != 0 || potentialTarget.motionZ != 0) {
                    this.targetEntity = (EntityLivingBase) list.get(0);
                    return true;
                }
                return false;
            }
        }
    }

}
