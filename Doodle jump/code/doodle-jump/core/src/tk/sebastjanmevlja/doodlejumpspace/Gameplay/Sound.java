package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;
import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

public class Sound {

    public static boolean musicEnabled = true;
    public static boolean soundEnabled = true;
    public static float musicVolume = 0.5f;
    public static float soundVolume = 1.0f;


    public static void changeMusicState() {
        if (musicEnabled) {
            if (!Assets.backgroundMusic.isPlaying()) {
                Assets.backgroundMusic.play();
                Assets.backgroundMusic.setLooping(true);
                Assets.backgroundMusic.setVolume(musicVolume);

            }
        } else {
            if (Assets.backgroundMusic.isPlaying()) {
                Assets.backgroundMusic.stop();
                Assets.backgroundMusic.setLooping(false);
            }
        }
    }

    public static void setMusicEnabled(boolean enabled) {
        musicEnabled = enabled;
        changeMusicState();
        Game.localStorage.setMusicEnabled(musicEnabled);
    }

    public static void changeMusicVolume(float volume) {
        musicVolume = volume;
        Assets.backgroundMusic.setVolume(musicVolume);
        Game.localStorage.setMusicVolume(musicVolume);
    }

    public static void setSoundEnabled(boolean enabled) {
        soundEnabled = enabled;
        Game.localStorage.setSoundEnabled(soundEnabled);
    }

    public static void changeSoundVolume(float volume) {
        soundVolume = volume;
        Assets.fallingSound.setVolume(volume);
        Assets.jumpSound.setVolume(volume);
        Assets.monsterSound.setVolume(volume);
        Assets.platformBreakingSound.setVolume(volume);
        Assets.startSound.setVolume(volume);
        Game.localStorage.setSoundVolume(soundVolume);
    }

    public static void playFallingSound() {
        if (soundEnabled) {
            Assets.fallingSound.stop();
            Assets.fallingSound.play();
        }

    }

    public static void playJumpSound() {
        if (soundEnabled) {
            Assets.jumpSound.stop();
            Assets.jumpSound.play();
        }
    }

    public static void playMonsterSound() {
        if (soundEnabled) {
            Assets.monsterSound.stop();
            Assets.monsterSound.play();
        }
    }

    public static void playPlatformBreakingSound() {
        if (soundEnabled) {
            Assets.platformBreakingSound.stop();
            Assets.platformBreakingSound.play();
        }
    }

    public static void playStartSound() {
        if (soundEnabled) {
            Assets.startSound.stop();
            Assets.startSound.play();
        }
    }

    public static void playShieldSound() {
        if (soundEnabled) {
            Assets.shieldSound.stop();
            Assets.shieldSound.play();
        }
    }

    public static void playBulletSound() {
        if (soundEnabled) {
            Assets.bulletSound.stop();
            Assets.bulletSound.play();
            Assets.bulletSound.setVolume(0.5f);
        }
    }

    public static void playJetpackSound() {
        if (soundEnabled) {
            Assets.jetpackSound.play();
            Assets.jetpackSound.setLooping(true);
            Assets.jetpackSound.setVolume(0.3f);
        }
    }

    public static void stopJetpackSound() {
        Assets.jetpackSound.stop();
    }


    public static void playMagnetoSound() {
        if (soundEnabled && !Assets.magnetoSound.isPlaying()) {
            Assets.magnetoSound.play();
            Assets.jetpackSound.setVolume(0.5f);
        }
    }

    public static void stopMagnetoSound() {
        Assets.magnetoSound.stop();
    }

    public static void playSlimeSound() {
        if (soundEnabled) {
            Assets.slimeSound.play();
        }
    }


}
