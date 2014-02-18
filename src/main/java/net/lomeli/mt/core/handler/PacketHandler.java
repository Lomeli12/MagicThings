package net.lomeli.mt.core.handler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.lomeli.mt.lib.Strings;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

/**
 * Work in Progress as I have no idea how packets work =P
 * 
 * @author Anthony
 * 
 */
public class PacketHandler implements IPacketHandler {
    private static final byte flyingPacket = 102;

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
        byte dataId = data.readByte();

        switch (dataId) {
        case flyingPacket:
            recieveFlyingPlayerPacket(data);
            break;
        }
    }

    // This one is incredibly broken.
    /*private void recieveStepPacket(ByteArrayDataInput data) {
        if (Minecraft.getMinecraft().theWorld.playerEntities.size() > 0) {
            EntityPlayer player = null;
            for (int i = 0; i < Minecraft.getMinecraft().theWorld.playerEntities.size(); i++) {
                EntityPlayer tempPlayer = (EntityPlayer) Minecraft.getMinecraft().theWorld.playerEntities.get(i);
                if (tempPlayer.username.equalsIgnoreCase(data.readUTF())) {
                    player = tempPlayer;
                    break;
                }
            }
            if (player != null)
                player.stepHeight = data.readFloat();
            System.out.println(data.readFloat());
        }
    }

    public static void sendStepPacket(String player, float stepHeight) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            Packet250CustomPayload packet = new Packet250CustomPayload();
            dos.writeByte(stepPacket);
            dos.writeUTF(player);
            dos.writeFloat(stepHeight);
            dos.close();
            packet.channel = Strings.MOD_ID;
            packet.data = baos.toByteArray();
            packet.length = baos.size();
            packet.isChunkDataPacket = false;
            PacketDispatcher.sendPacketToServer(packet);
        } catch (IOException e) {
        }
    }*/

    private void recieveFlyingPlayerPacket(ByteArrayDataInput data) {
        if (Minecraft.getMinecraft().theWorld.playerEntities.size() > 0) {
            EntityPlayer player = null;
            for (int i = 0; i < Minecraft.getMinecraft().theWorld.playerEntities.size(); i++) {
                EntityPlayer tempPlayer = (EntityPlayer) Minecraft.getMinecraft().theWorld.playerEntities.get(i);
                if (tempPlayer.username.equalsIgnoreCase(data.readUTF()))
                    player = tempPlayer;
            }
            if (player != null)
                player.capabilities.allowFlying = data.readBoolean();
        }
    }

    public static void sendFlyingPlayerPacket(String player, boolean canFly) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            Packet250CustomPayload packet = new Packet250CustomPayload();
            dos.writeByte(flyingPacket);
            dos.writeUTF(player);
            dos.writeBoolean(canFly);
            dos.close();
            packet.channel = Strings.MOD_ID;
            packet.data = baos.toByteArray();
            packet.length = baos.size();
            packet.isChunkDataPacket = false;
            PacketDispatcher.sendPacketToServer(packet);
        } catch (IOException e) {
        }
    }

}
