package tk.sebastjanmevlja.doodlejumpspace.Gameplay.Planets;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import tk.sebastjanmevlja.doodlejumpspace.Helpers.Assets;

public class LeftPlanet extends Planet {

    public static Array<TextureAtlas.AtlasRegion> leftPlanets = Assets.atlas.findRegions("planets_left");

    public LeftPlanet(TextureAtlas.AtlasRegion texture, World world, float y) {
        super(texture, world, 0, y);
    }
}
