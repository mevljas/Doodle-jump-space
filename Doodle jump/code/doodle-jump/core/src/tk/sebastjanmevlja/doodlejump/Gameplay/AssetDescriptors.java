package tk.sebastjanmevlja.doodlejump.Gameplay;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {
    public static final AssetDescriptor<TextureAtlas> atlas = new AssetDescriptor<TextureAtlas>(AssetNames.ATLAS, TextureAtlas.class);
    public static final AssetDescriptor<Texture> backgroundImage = new AssetDescriptor<Texture>(AssetNames.LOADING_BACKGROUND, Texture.class);
    public static final AssetDescriptor<Skin> skin = new AssetDescriptor<Skin>(AssetNames.SKIN, Skin.class);


    private AssetDescriptors(){}

}
