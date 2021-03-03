package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Monster;

import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.Random;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;

public class EnemyFactory {

    public static LinkedList<Enemy> enemies;








    private static  float minSpacingWidth;
    private static  float maxSpacingWidth;
    private static  float minSpacingHeight;
    private static  float maxSpacingHeight;
    public static int numberOfEnemies;

    private static Random r = new Random();


    public EnemyFactory() {
        minSpacingHeight = Constants.HEIGHT * 0.7f ;
        maxSpacingHeight = Constants.HEIGHT * 1.5f;
        minSpacingWidth = Constants.WIDTH * 0.2f;
        maxSpacingWidth = Constants.WIDTH * 0.6f;
        enemies = new LinkedList<>();
        r = new Random();
    }

    public static void generateMonsters(World world){

        float y;

        if (enemies.isEmpty()){
            y = maxSpacingHeight;
        }
        else {
            y = enemies.getLast().sprite.getY();
        }


        while (y < Constants.HEIGHT * 5) {
            float x = minSpacingWidth + r.nextFloat() * (maxSpacingWidth - minSpacingWidth);


            y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
            generateMonster(randomType(), world, x, y);


        }


        numberOfEnemies = enemies.size();
    }

    
    

    public static void generateMonster(EnemyType type, World world, float x, float y) {
        switch (type){
            case BLUE:
                enemies.add(new BlueEnemy( world, x, y));
                break;

            case GREEN:
                enemies.add(new GreenEnemy( world, x, y));
                break;

            case RED:
                enemies.add(new RedEnemy( world, x, y));
                break;

            case MAGNET:
                enemies.add(new MagnetoEnemy( world, x, y));
                break;

            case BLACKHOLE:
                enemies.add(new BlackHole( world, x, y));
                break;
        }

    }



    private static EnemyType randomType(){
        return EnemyType.values()[new Random().nextInt(EnemyType.values().length)];
    }


    public static void moveWorld(float velocity){

        for (Enemy m: enemies) {
            m.body.setLinearVelocity(m.body.getLinearVelocity().x,velocity );
        }
    }


    public static void stopWorld(){
        for (Enemy m: enemies) {
            m.body.setLinearVelocity(m.body.getLinearVelocity().x,0f);
        }
    }




    public static void removeMonster(){
        enemies.removeFirst();
    }





}
