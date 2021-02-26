package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.physics.box2d.World;

public class EarthPlanet extends LeftPlanet {
    public EarthPlanet(World world, float y) {
        super(leftPlanets.get(1), world, y);
    }
}
