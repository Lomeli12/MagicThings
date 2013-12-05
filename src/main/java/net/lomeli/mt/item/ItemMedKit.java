package net.lomeli.mt.item;

import java.util.List;

import net.lomeli.lomlib.util.ToolTipUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMedKit extends ItemMT {

    public ItemMedKit(int id) {
        super(id, "medkit");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player != null && !player.capabilities.isCreativeMode) {
            player.heal(10F);
            switch (itemStack.getItemDamage()) {
            case 1:
                player.removePotionEffect(Potion.poison.id);
                player.removePotionEffect(Potion.wither.id);
                break;
            case 2:
                player.addPotionEffect(new PotionEffect(22, 1000, 1));
                break;
            case 3:
                player.heal(5F);
                break;
            case 4:
                player.heal(20F);
                player.removePotionEffect(Potion.poison.id);
                player.removePotionEffect(Potion.wither.id);
                break;
            case 5:
                player.heal(20F);
                player.addPotionEffect(new PotionEffect(22, 2500, 3));
                break;
            case 6:
                player.heal(10F);
                break;
            default:
                break;
            }

            player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 250, 2));
            itemStack.stackSize--;
        }
        return itemStack;
    }

    @Override
    public int getMetadata(int par1) {
        return par1;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if (itemStack != null && ToolTipUtil.doAdditionalInfo()) {
            int meta = itemStack.getItemDamage();
            switch (meta) {
            case 1:
                infoList.add("Heals 50% of your health.");
                infoList.add("Cures Posion and Wither.");
                break;
            case 2:
                infoList.add("Heals 50% of your health.");
                infoList.add("Temporary Absorbtion effect.");
                break;
            case 3:
                infoList.add("Heals 75% of your health.");
                break;
            case 4:
                infoList.add("Fully heals you and cures");
                infoList.add("Posion and Wither.");
                break;
            case 5:
                infoList.add("Fully heals you and gives you");
                infoList.add("temporary Absorbtion effect.");
                break;
            case 6:
                infoList.add("Fully heals you.");
                break;
            default:
                infoList.add("Heals 50% of your health.");
                break;
            }
        } else {
            infoList.add(ToolTipUtil.additionalInfoInstructions(ToolTipUtil.LIGHT_GRAY));
        }
    }
}
