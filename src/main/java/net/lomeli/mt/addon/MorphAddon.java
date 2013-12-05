package net.lomeli.mt.addon;

import java.util.logging.Level;

import morph.api.Ability;
import morph.api.Api;
import morph.common.ability.AbilityHandler;
import morph.common.ability.AbilityHostile;

import net.lomeli.lomlib.util.ModLoaded;

import net.lomeli.mt.MThings;
import net.lomeli.mt.ability.AbilityResistance;
import net.lomeli.mt.entity.EntityClicker;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class MorphAddon {
    public static void registerAbilities() {
        if (ModLoaded.isModInstalled("Morph")) {
            MThings.logger.log(Level.INFO, "Morph Found! Registering new Abilities");
            try {
                Ability.registerAbility("infectionresistance", AbilityResistance.class);
                Ability.mapAbilities(EntityClicker.class, new AbilityResistance(), new AbilityHostile());

                MThings.logger.log(Level.INFO, "Abilities successfully registered!");
            } catch (Exception e) {
                MThings.logger.log(Level.SEVERE, "Could NOT load register Abilities!");
            }
        }
    }

    public static void flying(EntityPlayer player) {
        EntityLivingBase morph = Api.getMorphEntity(player.username, true);
        if (morph != null) {
            if (!AbilityHandler.hasAbility(morph.getClass(), "fly")) {
                player.capabilities.isFlying = false;
                player.capabilities.allowFlying = false;
                player.stepHeight = 0.5F;
            }
        } else {
            player.capabilities.isFlying = false;
            player.capabilities.allowFlying = false;
            player.stepHeight = 0.5F;
        }
    }
}
