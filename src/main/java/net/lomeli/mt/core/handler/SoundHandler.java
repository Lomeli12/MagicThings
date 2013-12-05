package net.lomeli.mt.core.handler;

import java.util.logging.Level;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.mt.MThings;
import net.lomeli.mt.lib.Strings;

import net.minecraft.client.audio.SoundPool;

import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

@SideOnly(Side.CLIENT)
public class SoundHandler {
    private static SoundHandler instance;

    @SideOnly(Side.CLIENT)
    public static SoundHandler getInstance() {
        if (instance == null)
            instance = new SoundHandler();
        return instance;
    }

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void loadSounds(SoundLoadEvent event) {
        SoundPool sounds = event.manager.soundPoolSounds;
        SoundPool music = event.manager.soundPoolStreaming;
        addSoundEfx(sounds, "mob/clicker/say0.ogg");
        addSoundEfx(sounds, "mob/clicker/say1.ogg");
        addSoundEfx(sounds, "mob/clicker/say2.ogg");
        addSoundEfx(sounds, "mob/clicker/hurt0.ogg");
        addSoundEfx(sounds, "mob/clicker/hurt1.ogg");
        addSoundEfx(sounds, "mob/clicker/death0.ogg");

        addSoundEfx(music, "discCoR.ogg");
    }

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onPlayStreaming(PlayStreamingEvent event) {
        if (event.name == "discCoR") {
            FMLClientHandler.instance().getClient().sndManager.playStreaming(Strings.MOD_ID.toLowerCase() + ":discCoR", event.x + 0.5F, event.y + 0.5F, event.z + 0.5F);
        }
    }

    @SideOnly(Side.CLIENT)
    private void addSoundEfx(SoundPool pool, String sound) {
        try {
            pool.addSound(Strings.MOD_ID.toLowerCase() + ":" + sound);
        } catch (Exception e) {
            MThings.logger.log(Level.SEVERE, "Sound not load sound " + Strings.MOD_ID.toLowerCase() + ":" + sound);
        }
    }

}
