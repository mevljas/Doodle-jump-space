package tk.sebastjanmevlja.doodlejumpspace.Gameplay;

import tk.sebastjanmevlja.doodlejumpspace.MyGame.Game;

public class Sound {

    public static boolean musicEnabled = true;
    public static boolean soundEnabled = true;
    public static float musicVolume = 0.5f;
    public static float soundVolume = 1.0f;


    public static void changeMusicState() {
        if (musicEnabled) {
            if (!Asset.backgroundMusic.isPlaying()) {
                Asset.backgroundMusic.play();
                Asset.backgroundMusic.setLooping(true);
                Asset.backgroundMusic.setVolume(musicVolume);

            }
        } else {
            if (Asset.backgroundMusic.isPlaying()) {
                Asset.backgroundMusic.stop();
                Asset.backgroundMusic.setLooping(false);
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
        Asset.backgroundMusic.setVolume(musicVolume);
        Game.localStorage.setMusicVolume(musicVolume);
    }

    public static void setSoundEnabled(boolean enabled) {
        soundEnabled = enabled;
        Game.localStorage.setSoundEnabled(soundEnabled);
    }

    public static void changeSoundVolume(float volume) {
        soundVolume = volume;
        Asset.fallingSound.setVolume(volume);
        Asset.jumpSound.setVolume(volume);
        Asset.monsterSound.setVolume(volume);
        Asset.platformBreakingSound.setVolume(volume);
        Asset.startSound.setVolume(volume);
        Game.localStorage.setSoundVolume(soundVolume);
    }

    public static void playFallingSound() {
        if (soundEnabled) {
            Asset.fallingSound.stop();
            Asset.fallingSound.play();
        }

    }

    public static void playJumpSound() {
        if (soundEnabled) {
            Asset.jumpSound.stop();
            Asset.jumpSound.play();
        }
    }

    public static void playMonsterSound() {
        if (soundEnabled) {
            Asset.monsterSound.stop();
            Asset.monsterSound.play();
        }
    }

    public static void playPlatformBreakingSound() {
        if (soundEnabled) {
            Asset.platformBreakingSound.stop();
            Asset.platformBreakingSound.play();
        }
    }

    public static void playStartSound() {
        if (soundEnabled) {
            Asset.startSound.stop();
            Asset.startSound.play();
        }
    }

    public static void playShieldSound() {
        if (soundEnabled) {
            Asset.shieldSound.stop();
            Asset.shieldSound.play();
        }
    }

    public static void playBulletSound() {
        if (soundEnabled) {
            Asset.bulletSound.stop();
            Asset.bulletSound.play();
        }
    }


}
