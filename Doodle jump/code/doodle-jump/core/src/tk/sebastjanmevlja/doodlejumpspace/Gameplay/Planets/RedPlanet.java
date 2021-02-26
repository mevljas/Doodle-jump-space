package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.physics.box2d.World;

public class RedPlanet extends RightPlanet {

    public RedPlanet(World world, float y) {
        super(RightPlanets.get(0), world, y);
    }
}
