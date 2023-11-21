package org.astemir.cammod.client.screen.ui.misc;


import java.util.HashMap;
import java.util.Map;

public class TextureSet {

    private Map<UIProperty,AtlasTexture> atlases = new HashMap();

    public TextureSet put(AtlasTexture texture){
        this.atlases.put(UIProperty.DEFAULT,texture);
        return this;
    }

    public TextureSet put(UIProperty property, AtlasTexture texture){
        this.atlases.put(property,texture);
        return this;
    }

    public AtlasTexture get(UIProperty property){
        return atlases.get(property);
    }

}
