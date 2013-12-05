package net.lomeli.mt.item;

import java.util.List;
import java.util.Random;

import net.lomeli.mt.core.helper.TeleportHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemError extends ItemMT {

    public ItemError(int id) {
        super(id, "error");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player != null) {
            if (!player.isSneaking()) {
                Random rand = new Random();
                boolean used = false;
                if (rand.nextBoolean()) {
                    player.addPotionEffect(new PotionEffect(rand.nextInt(19) + 1, rand.nextInt(20000) + 1, rand.nextInt(5) + 1));
                    // used = true;
                }
                if (rand.nextBoolean()) {
                    world.playSoundAtEntity(player, "mob.endermen.stare", 1.0F, 1.0F);
                }
                if (rand.nextBoolean()) {
                    TeleportHelper.teleportRandomly(player, world, rand);
                    // used = true;
                }
                if (rand.nextBoolean()) {
                    player.inventory.dropAllItems();
                    used = true;
                }
                if (rand.nextBoolean()) {
                    player.setFire(rand.nextInt(2000) + 1);
                    // used = true;
                }
                if (rand.nextBoolean()) {
                    player.heal(20);
                    // used = true;
                }
                if (rand.nextInt(50) < 2 && !used) {
                    world.createExplosion(player, player.posX, player.posY, player.posZ, 3F, true);
                    used = true;
                }
                if (rand.nextInt(50) < 1 && !used) {
                    player.addExperience((1 + rand.nextInt(9000)));
                    used = true;
                }
                if (rand.nextInt(50) < 1 && !used) {
                    player.inventory.addItemStackToInventory(new ItemStack(Item.diamond));
                    used = true;
                }
                if (rand.nextInt(60) < 1 && !used) {
                    player.inventory.addItemStackToInventory(new ItemStack(Item.emerald));
                    used = true;
                }
                if (rand.nextBoolean()) {
                    world.toggleRain();
                }
                itemStack.stackSize--;
            }
        }
        return itemStack;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bool) {
        if (itemStack != null) {
            infoList.add("I AM!");
        }
    }
}
