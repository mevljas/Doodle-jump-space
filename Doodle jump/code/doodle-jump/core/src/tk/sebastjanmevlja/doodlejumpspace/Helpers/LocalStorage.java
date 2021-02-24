package tk.sebastjanmevlja.doodlejumpspace.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class LocalStorage {
    private Preferences preferences;
    private boolean musicEnabled;
    private boolean soundEnabled;
    private boolean savedData;
    private float musicVolume;
    private float soundVolume;
    private int highScore;

    public LocalStorage(){
        preferences = Gdx.app.getPreferences("Doodle jump");
        musicEnabled = preferences.getBoolean("musicEnabled",true);
        soundEnabled = preferences.getBoolean("soundEnabled",true);
        musicVolume = preferences.getFloat("musicVolume",0.5f);
        soundVolume = preferences.getFloat("soundVolume",1.0f);
        highScore = preferences.getInteger("HighScore",0);
        savedData = preferences.getBoolean("savedData",false);


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
        preferences.putInteger("HighScore", highScore);
        preferences.flush();
    }

    public int getHighScore(){
        return highScore;
    }

    public void setScore(int score){
        preferences.putInteger("score", score);
        preferences.flush();
    }

    public int getScore(){
        return preferences.getInteger("score",0);
    }

    public void setLives(int lives){
        preferences.putInteger("lives", lives);
        preferences.flush();
    }

    public int getLives(){
        return preferences.getInteger("lives",5);
    }

    public void setSavedData(boolean value){
        this.savedData = value;
        preferences.putBoolean("savedData", value);
        preferences.flush();
    }

    public boolean getSavedData(){
        return this.savedData;
    }

}
