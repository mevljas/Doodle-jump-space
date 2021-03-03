package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Asset;
import tk.sebastjanmevlja.doodlejumpspace.Gameplay.Constants;

public class RightPlanet extends Planet {

    public static Array<TextureAtlas.AtlasRegion> RightPlanets = Asset.atlas.findRegions("planets_right");

    public RightPlanet(TextureAtlas.AtlasRegion texture, World world, float y) {
        super(texture, world, Constants.WIDTH - Planet.PLANET_WIDTH, y);
    }
}
