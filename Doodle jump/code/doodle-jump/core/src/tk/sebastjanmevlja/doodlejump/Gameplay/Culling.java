package tk.sebastjanmevlja.doodlejump.Gameplay;


//Store number of drawn objects.
public class Culling {

    private static int allObjectsCounter = 0;
    private static int drawnObjectsCounter = 0;


    public static void incrementObjectsCounter(){
        allObjectsCounter++;
    }

    static void incrementDrawnObjectsCounter(){
        drawnObjectsCounter++;
    }

    public static void resetCounter(){
        allObjectsCounter = 0;
        drawnObjectsCounter = 0;
    }

    public static int getAllObjectsCounter() {
        return allObjectsCounter;
    }

    public static void setAllObjectsCounter() {
        resetCounter();
        Player.player.incrementGlobalObjectCounter();
        for (Monster m: MonsterFactory.monsters) {
            m.incrementGlobalObjectCounter();
        }

        for (Platform p: PlatformFactory.platforms) {
            p.incrementGlobalObjectCounter();
        }
    }

    public static int getDrawnObjectsCounter() {
        return drawnObjectsCounter;
    }




}
