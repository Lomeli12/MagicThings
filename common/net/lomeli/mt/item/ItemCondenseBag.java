package net.lomeli.mt.item;

import java.util.List;
import java.util.UUID;

import net.lomeli.mt.MThings;
import net.lomeli.mt.inventory.ContainerCondenseBag;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class ItemCondenseBag extends ItemMT {
    private Icon[] iconArray;

    public ItemCondenseBag(int id) {
        super(id, "bag");
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        iconArray = new Icon[2];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.itemTexture + i);
        }
    }

    @Override
    public Icon getIconFromDamage(int i) {
        return iconArray[i];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(int itemID, CreativeTabs tabs, List list) {
        for(int i = 0; i < iconArray.length; i++) {
            list.add(new ItemStack(itemID, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem) {
        if(world.isRemote || !isCurrentItem)
            return;
        if(ContainerCondenseBag.class.isAssignableFrom(((EntityPlayer) entity).openContainer.getClass())) {
            ContainerCondenseBag container = (ContainerCondenseBag) ((EntityPlayer) entity).openContainer;
            if(container != null && container.updateNotification) {
                if(container.bagInventory.getUniqueID() == null || container.bagInventory.getUniqueID().isEmpty()) {
                    container.bagInventory.setUniqueID(UUID.randomUUID().toString());
                }
                container.bagInventory.writeToNBT(itemStack.stackTagCompound);
                container.updateNotification = false;
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if(!world.isRemote) {
            if(!player.isSneaking())
                player.openGui(MThings.instance, 2, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return itemStack;
    }
}
