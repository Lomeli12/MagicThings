package net.lomeli.mt.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCavernDisc extends ItemRecord {

    @SuppressWarnings("rawtypes")
    private static final Map records = new HashMap();

    public final String recordName;

    @SuppressWarnings("unchecked")
    public ItemCavernDisc(int id) {
        super(id, "discCoR");
        recordName = "discCoR";
        this.setMaxStackSize(1);
        this.setCreativeTab(MThings.modtab);
        records.put(recordName, this);
    }

    @Override
    public void registerIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":disc");
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (world.getBlockId(x, y, z) == Block.jukebox.blockID && world.getBlockMetadata(x, y, z) == 0) {
                ((BlockJukeBox) Block.jukebox).insertRecord(world, x, y, z, itemstack);
                world.playAuxSFXAtEntity((EntityPlayer) null, 1005, x, y, z, this.itemID);
                --itemstack.stackSize;
                return true;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getRecordTitle() {
        return "KingSora3 - Cavern of Remembrance";
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(this.getRecordTitle());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }

    @Override
    public Item setUnlocalizedName(String str) {
        super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + str);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public static ItemCavernDisc getRecord(String par) {
        return (ItemCavernDisc) records.get(par);
    }

}
