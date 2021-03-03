package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.physics.box2d.World;

import java.util.LinkedList;
import java.util.Random;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;

public class PlanetFactory {

    public static LinkedList<Planet> planets;


    public static int InitiaPlanetsSize;

    private static float maxSpacingHeight;
    private static float minSpacingHeight;

    private static Random r;

    private static float y;


    public PlanetFactory() {
        planets = new LinkedList<>();
        maxSpacingHeight = Constants.HEIGHT * 4f;
        minSpacingHeight = Constants.HEIGHT * 1f;
        r = new Random();
    }

    public static void generatePlanets(World world){
        y = Constants.HEIGHT * 2;
        planets.add(new RedPlanet(world, y));

        y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
        planets.add(new YellowPlanet(world, y));

        y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
        planets.add(new DeathStarPlanet(world, y));

        y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
        planets.add(new EarthPlanet(world, y));



        InitiaPlanetsSize = planets.size();
    }





    public static void recyclePlanet(Planet planet, float y) {
        planet.changePosition(y);
        planets.remove(planet);
        planets.addLast(planet);
    }

    public static void moveWorld(float velocity){

        for (Planet p: planets) {
            p.body.setLinearVelocity(0,velocity );
        }
    }


    public static void stopWorld(){
        for (Planet p: planets) {
            p.body.setLinearVelocity(0,0);
        }
    }


    public static void recyclePlanet(Planet p){
        y += minSpacingHeight + r.nextFloat()  * (maxSpacingHeight - minSpacingHeight);
        recyclePlanet(p, y );
    }

    public static float getY() {
        return y;
    }
}
