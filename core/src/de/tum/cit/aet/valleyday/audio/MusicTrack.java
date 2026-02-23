package de.tum.cit.aet.valleyday.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * This enum is used to manage the music tracks in the game.
 * Currently, only one track is used, but this could be extended to include multiple tracks.
 * Using an enum for this purpose is a good practice, as it allows for easy management of the music tracks
 * and prevents the same track from being loaded into memory multiple times.
 * See the assets/audio folder for the actual music files.
 * Feel free to add your own music tracks and use them in the game!
 */
public enum MusicTrack {
    
    BACKGROUND("theShire.mp3", 0.2f),
    GAMETRACK("gametrack.mp3", 0.2f),
    FIXYOU("FixYou.mp3", 0.2f),
    HEARTOFCOURAGE ("courage.mp3", 0.2f);
    

    
    
    
    
    /** The music file owned by this variant. */
    private final Music music;
    //Felix: new attribute
    private final float defaultVolume;
    
    MusicTrack(String fileName, float volume) {
        this.music = Gdx.audio.newMusic(Gdx.files.internal("audio/" + fileName));
        this.music.setLooping(true);
       //this.music.setVolume(volume);
       //just for working with it cause the music annoys
       this.defaultVolume = volume;
       this.music.setVolume(defaultVolume);
    }
    
    /**
     * Play this music track.
     * This will not stop other music from playing - if you add more tracks, you will have to handle that yourself.
     */
    public void play() {
        music.setVolume(defaultVolume);
        this.music.play();
    }

    //Felix: Mute game sound or unmute it
    public void changeMuted() {
        if(music.getVolume() == 0.0f) {
            music.setVolume(defaultVolume);
        }
        else {music.setVolume(0.0f);}
        }

    public void mute(){
        music.setVolume(0);
    }

    public void unmute(){
        music.setVolume(defaultVolume);
    }
    }

    

