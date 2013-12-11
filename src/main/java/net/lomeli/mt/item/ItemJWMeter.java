package net.lomeli.mt.item;

import net.lomeli.mt.api.tile.ITileEnergy;
import net.lomeli.mt.core.helper.EnergyHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import buildcraft.api.power.IPowerReceptor;

import universalelectricity.prefab.tile.TileEntityElectrical;

import cofh.api.energy.IEnergyHandler;

public class ItemJWMeter extends ItemMT{

    public ItemJWMeter(int id) {
        super(id, "jwmeter");
        setMaxStackSize(1);
        setUnlocalizedName("jwmeter");
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getBlockTileEntity(x, y, z);
        int id = world.getBlockId(x, y, z);
        if(!world.isRemote){
            if(tile != null && id != 0){
                if(EnergyHelper.isValidMachine(tile)){
                    Object energy = null, max = null;
                    String type = null;
                    if(tile instanceof ITileEnergy){
                        energy = ((ITileEnergy)tile).getEnergy();
                        max = ((ITileEnergy)tile).getMaxEnergy();
                        type = " JW";
                    }else if(tile instanceof IPowerReceptor){
                        if(((IPowerReceptor)tile).getPowerReceiver(null) != null){
                            energy = ((IPowerReceptor)tile).getPowerReceiver(null).getEnergyStored();
                            max = ((IPowerReceptor)tile).getPowerReceiver(null).getMaxEnergyStored();
                            type = " MJ";
                        }
                    }else if(tile instanceof IEnergyHandler){
                        energy = ((IEnergyHandler)tile).getEnergyStored(null);
                        max = ((IEnergyHandler)tile).getMaxEnergyStored(null);
                        type = " RF";
                    }else if(tile instanceof TileEntityElectrical){
                        energy = ((TileEntityElectrical)tile).getEnergyStored();
                        max = ((TileEntityElectrical)tile).getMaxEnergyStored();
                        type = " Watt";
                    }
                    if(energy != null && max != null && type != null){
                        player.closeScreen();
                        player.addChatMessage(energy.toString() + " / " + max.toString() + type);
                        player.swingItem();
                    }
                    
                }
            }
        }
        return true;
    }

}
