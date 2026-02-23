package de.tum.cit.aet.valleyday.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public enum SoundFX {

    POWERUPSFX("powerup.mp3", 1f),
    DRAGONFIRE("DRAGON_FIRE.mp3", 1f),
    PLANTING("Planting.mp3", 1f),
    HARVESTING("Harvesting.mp3", 1f),
    PAIN("PAIN.mp3", 1f),
    PICKAXE("pickaxe.mp3", 1f),
    BROKE("Broke.mp3", 1f),
    SWOOSH("swoosh.mp3", 1f),
    BLOOD("blood.mp3", 1),
    POP ("pop.mp3", 1f);

    private Music music;
    private final String fileName;
    private final float defaultVolume;

    SoundFX(String fileName, float volume) {
        this.fileName = fileName;
        this.defaultVolume = volume;
    }

    // STATIC METHOD MUST EXIST
    public static void loadAll() {
        for (SoundFX fx : values()) {
            fx.music = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fx.fileName));
            fx.music.setLooping(false);
            fx.music.setVolume(fx.defaultVolume);
        }
    }

    public void play() {
        if (music != null) {
            music.setVolume(defaultVolume);
            music.play();
        }
    }
}

