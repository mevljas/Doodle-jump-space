package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.physics.box2d.World;

public class YellowPlanet extends LeftPlanet {
    public YellowPlanet(World world, float y) {
        super(leftPlanets.get(0), world, y);
    }
}
