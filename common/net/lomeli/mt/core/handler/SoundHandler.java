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

public class SoundHandler {
    private static SoundHandler instance;

    public static SoundHandler getInstance() {
        if(instance == null)
            instance = new SoundHandler();
        return instance;
    }

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
        if(event.name == "discCoR") {
            FMLClientHandler.instance().getClient().sndManager.playStreaming(Strings.MOD_ID.toLowerCase() + ":discCoR",
                    (float) event.x + 0.5F, (float) event.y + 0.5F, (float) event.z + 0.5F);
        }
    }

    private void addSoundEfx(SoundPool pool, String sound) {
        try {
            pool.addSound(Strings.MOD_ID.toLowerCase() + ":" + sound);
        }catch(Exception e) {
            MThings.logger.log(Level.SEVERE, "Sound not load sound " + Strings.MOD_ID.toLowerCase() + ":" + sound);
        }
    }

}
