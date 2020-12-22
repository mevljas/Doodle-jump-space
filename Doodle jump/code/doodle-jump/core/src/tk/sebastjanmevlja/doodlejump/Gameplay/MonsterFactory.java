package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;
import java.util.Random;

public class MonsterFactory {

    public static LinkedList<Monster> monsters = new LinkedList<>();

    static Random random = new Random();

    private  static final Array<TextureAtlas.AtlasRegion> aliensGreen = Asset.atlas.findRegions("aliens1");
    private  static final Array<TextureAtlas.AtlasRegion> aliensBlue = Asset.atlas.findRegions("aliens2");
    private  static final TextureAtlas.AtlasRegion alienRed = Asset.atlas.findRegion("monster");
    private  static final TextureAtlas.AtlasRegion alienUfo = Asset.atlas.findRegion("ufo");
    public  static final TextureAtlas.AtlasRegion alienUfoLight = Asset.atlas.findRegion("ufo_light");


    private static final float minSpacing = Constants.HEIGHT ;
    private static final float maxSpacing = Constants.HEIGHT * 2f ;
    public static int InitiaMonsterSize;

    private static Random r = new Random();





    public static void generateMonsters(World world){

        float y;

        if (monsters.isEmpty()){
            y = maxSpacing;
        }
        else {
            y = monsters.getLast().sprite.getY() + Monster.HEIGHT;
        }



        while (y < Constants.HEIGHT * 5) {
            float x = Monster.WIDTH * 3 + random.nextFloat() * (Constants.WIDTH - Monster.WIDTH * 4);

            generateMonster(randomType(), world, x, y);

            y += minSpacing + r.nextFloat()  * (maxSpacing - minSpacing);
        }


        InitiaMonsterSize = monsters.size();
    }

    
    

    public static void generateMonster(MonsterType type, World world, float x, float y) {
        switch (type){
            case BLUE:
                monsters.add(new Monster(type, aliensBlue,world, x, y));
                break;

            case GREEN:
                monsters.add(new Monster(type, aliensGreen,world, x, y));
                break;

            case RED:
                monsters.add(new Monster(type, alienRed,world, x, y));
                break;

            case UFO:
                monsters.add(new Monster(type, alienUfo,world, x, y));
                break;


        }

    }



    private static MonsterType randomType(){
        return MonsterType.values()[new Random().nextInt(MonsterType.values().length)];
    }


    public static void moveWorld(float velocity){

        for (Monster m: monsters) {
            m.body.setLinearVelocity(m.body.getLinearVelocity().x,-velocity * 1.8f);
        }
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
