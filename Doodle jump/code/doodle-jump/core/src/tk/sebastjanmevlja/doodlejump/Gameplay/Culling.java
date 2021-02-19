package tk.sebastjanmevlja.doodlejump.Gameplay;


import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.Monster;
import tk.sebastjanmevlja.doodlejump.Gameplay.Monster.MonsterFactory;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.Platform;
import tk.sebastjanmevlja.doodlejump.Gameplay.Platform.PlatformFactory;
import tk.sebastjanmevlja.doodlejump.Level.Level1Screen;

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
        for (Monster m: MonsterFactory.monsters) {
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
