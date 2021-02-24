package tk.sebastjanmevlja.doodlejump.Gameplay.Monster;

import com.badlogic.gdx.physics.box2d.World;
import tk.sebastjanmevlja.doodlejump.Gameplay.Constants;

import java.util.LinkedList;
import java.util.Random;

public class MonsterFactory {

    public static LinkedList<Monster> monsters;








    private static  float minSpacingWidth;
    private static  float maxSpacingWidth;
    private static  float minSpacingHeight;
    private static  float maxSpacingHeight;
    public static int InitiaMonsterSize;

    private static Random r = new Random();


    public MonsterFactory() {
        minSpacingHeight = Constants.HEIGHT * 0.8f ;
        maxSpacingHeight = Constants.HEIGHT * 2f;
        minSpacingWidth = Constants.WIDTH * 0.2f;
        maxSpacingWidth = Constants.WIDTH * 0.6f;
        monsters = new LinkedList<>();
        r = new Random();
    }

    public static void generateMonsters(World world){

        float y;

        if (monsters.isEmpty()){
            y = maxSpacingHeight;
        }
        else {
            y = monsters.getLast().sprite.getY();
        }


        while (y < Constants.HEIGHT * 5) {
            float x = minSpacingWidth + r.nextFloat() * (maxSpacingWidth - minSpacingWidth);


            y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
            generateMonster(randomType(), world, x, y);


        }


        InitiaMonsterSize = monsters.size();
    }

    
    

    public static void generateMonster(MonsterType type, World world, float x, float y) {
        switch (type){
            case BLUE:
                monsters.add(new BlueMonster( world, x, y));
                break;

            case GREEN:
                monsters.add(new GreenMonster( world, x, y));
                break;

            case RED:
                monsters.add(new RedMonster( world, x, y));
                break;

            case UFO:
                monsters.add(new UfoMonster( world, x, y));
                break;

            case BLACKHOLE:
                monsters.add(new BlackHoleMonster( world, x, y));
                break;


        }

    }



    private static MonsterType randomType(){
        return MonsterType.values()[new Random().nextInt(MonsterType.values().length)];
    }


    public static void moveWorld(float velocity){

        for (Monster m: monsters) {
            m.body.setLinearVelocity(m.body.getLinearVelocity().x,velocity );
        }
    }



    public static float getYVelocity(){
        return monsters.getFirst().body.getLinearVelocity().y;
    }


    public static void stopWorld(){
        for (Monster m: monsters) {
            m.body.setLinearVelocity(m.body.getLinearVelocity().x,0f);
        }
    }




    public static void removeMonster(Monster m){
        monsters.removeFirst();
    }





}
