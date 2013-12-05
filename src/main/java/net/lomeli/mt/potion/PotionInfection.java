package net.lomeli.mt.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.lomlib.entity.CustomDamageSource;
import net.lomeli.lomlib.entity.EntityUtil;
import net.lomeli.lomlib.util.ResourceUtil;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;

public class PotionInfection extends Potion {

    public static DamageSource infection = new CustomDamageSource("infection").setMagicDamage();
    public static Potion infect;

    public PotionInfection() {
        super(40, true, 9401144);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceUtil.getIcon(Strings.MOD_ID, "potion.png"));
        return super.hasStatusIcon();
    }

    public static void setUp() {
        LanguageRegistry.instance().addStringLocalization("death.attack.infection", "%1$s " + StatCollector.translateToLocal("potion.magicthings:CordycepsDeath"));
        Potion[] potionTypes = null;
        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modified = Field.class.getDeclaredField("modifiers");
                    modified.setAccessible(true);
                    modified.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (Exception e) {
                MThings.logger.log(Level.SEVERE, "Could not load Potions!");
            }
        }
        infect = new PotionInfection().setPotionName("potion.infection").setIconIndex(3, 15);
    }

    @Override
    public void performEffect(EntityLivingBase entityLiving, int potionID) {
        super.performEffect(entityLiving, potionID);
        if (entityLiving != null && potionID == this.getId()) {
            if (!EntityUtil.isUndeadEntity(entityLiving) && entityLiving.worldObj.rand.nextInt(10) == 0) {
                entityLiving.attackEntityFrom(infection, 1F);
            }
        }
    }
}
