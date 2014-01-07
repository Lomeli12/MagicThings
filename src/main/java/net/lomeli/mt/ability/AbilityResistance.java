package net.lomeli.mt.ability;

import net.lomeli.lomlib.client.ResourceUtil;

import net.lomeli.mt.lib.Strings;
import net.lomeli.mt.potion.PotionInfection;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import morph.api.Ability;

public class AbilityResistance extends Ability {

    @Override
    public String getType() {
        return "infectionresistance";
    }

    public boolean hasPoisonEffect(EntityLivingBase entity) {
        boolean hasEffect = false;
        if (entity != null) {
            if (entity.isPotionActive(Potion.poison) || entity.isPotionActive(Potion.wither) || entity.isPotionActive(PotionInfection.infect))
                hasEffect = true;
        }
        return hasEffect;
    }

    @Override
    public void tick() {
        if (this.getParent() != null) {
            if (this.hasPoisonEffect(this.getParent())) {
                if (this.getParent().isPotionActive(PotionInfection.infect))
                    this.getParent().removePotionEffect(PotionInfection.infect.id);
                if (this.getParent().isPotionActive(Potion.poison))
                    this.getParent().removePotionEffect(Potion.poison.id);
                if (this.getParent().isPotionActive(Potion.wither))
                    this.getParent().getActivePotionEffect(Potion.wither).duration--;
            }
        }
    }

    @Override
    public void kill() {
    }

    @Override
    public Ability clone() {
        return new AbilityResistance();
    }

    @Override
    public void save(NBTTagCompound tag) {
    }

    @Override
    public void load(NBTTagCompound tag) {
    }

    @Override
    public void postRender() {
    }

    @Override
    public ResourceLocation getIcon() {
        return ResourceUtil.getIcon(Strings.MOD_ID.toLowerCase(), "antivirus.png");
    }

}
