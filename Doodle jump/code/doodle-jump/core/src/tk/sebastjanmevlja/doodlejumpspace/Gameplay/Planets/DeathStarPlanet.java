package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.physics.box2d.World;

public class DeathStarPlanet extends RightPlanet {

    public DeathStarPlanet( World world, float y) {
        super(RightPlanets.get(1), world, y);
    }
}
