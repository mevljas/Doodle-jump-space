package tk.sebastjanmevlja.doodlejumpspace.Gameplay;


import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster.Enemy;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster.MonsterFactory;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform.Platform;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Platform.PlatformFactory;
import tk.sebastjanmevlja.doodlejumpspace.Level.Level1Screen;

//Store number of drawn objects.
public class Culling {

    private static int allObjectsCounter = 0;
    private static int drawnObjectsCounter = 0;


    public static void incrementObjectsCounter(){
        allObjectsCounter++;
    }



    public static void resetCounter(){
        allObjectsCounter = 0;
        drawnObjectsCounter = 0;
    }

    public static int getAllObjectsCounter() {
        return allObjectsCounter;
    }

    public static void setAllObjectsCounter() {
        Player.player.incrementGlobalObjectCounter();
        for (Enemy m: MonsterFactory.enemies) {
            m.incrementGlobalObjectCounter();
        }

        for (Platform p: PlatformFactory.platforms) {
            p.incrementGlobalObjectCounter();
        }
    }

    public static void setDrawnObjectsCounter() {
        drawnObjectsCounter = Level1Screen.getStage().getActors().size;
    }

    public static void countObjects(){
        resetCounter();
        setDrawnObjectsCounter();
        setAllObjectsCounter();
    }

    public static int getDrawnObjectsCounter() {
        return drawnObjectsCounter;
    }




}
