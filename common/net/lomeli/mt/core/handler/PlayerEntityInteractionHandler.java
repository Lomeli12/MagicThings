package net.lomeli.mt.core.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

public class PlayerEntityInteractionHandler {

    @ForgeSubscribe
    public void playerEntityInteraction(EntityInteractEvent event) {
        if(event.target != null && event.entityPlayer != null && !event.entity.worldObj.isRemote) {
            EntityPlayer player = event.entityPlayer;
            Entity entity = event.target;
            player.getAbsorptionAmount();
            entity.getAir();
        }
    }

}
