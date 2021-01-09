package tk.sebastjanmevlja.doodlejump.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class LocalStorage {
    private Preferences preferences;
    private boolean musicEnabled;
    private boolean soundEnabled;
    private float musicVolume;
    private float soundVolume;
    private int highScore;

    public LocalStorage(){
        preferences = Gdx.app.getPreferences("Doodle jump");
        musicEnabled = preferences.getBoolean("musicEnabled",true);
        soundEnabled = preferences.getBoolean("soundEnabled",true);
        musicVolume = preferences.getFloat("musicVolume",0.5f);
        soundVolume = preferences.getFloat("soundVolume",1.0f);
        highScore = preferences.getInteger("score",0);


    }

    public void setMusicEnabled(boolean musicEnabled){
        this.musicEnabled=musicEnabled;
        preferences.putBoolean("musicEnabled",musicEnabled);
        preferences.flush();
    }

    public boolean getMusicEnabled(){
        return musicEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled){
        this.soundEnabled=soundEnabled;
        preferences.putBoolean("soundEnabled",soundEnabled);
        preferences.flush();
    }

    public boolean getSoundEnabled(){
        return soundEnabled;
    }

    public void setMusicVolume(float musicVolume){
        this.musicVolume=musicVolume;
        preferences.putFloat("musicVolume",musicVolume);
        preferences.flush();
    }

    public float getMusicVolume(){
        return musicVolume;
    }

    public void setSoundVolume(float soundVolume){
        this.soundVolume=soundVolume;
        preferences.putFloat("soundVolume",soundVolume);
        preferences.flush();
    }

    public float getSoundVolume(){
        return soundVolume;
    }

    public void setHighScore(int highScore){
        this.highScore = highScore;
        preferences.putInteger("score", highScore);
        preferences.flush();
    }

    public int getHighScore(){
        return highScore;
    }

}
