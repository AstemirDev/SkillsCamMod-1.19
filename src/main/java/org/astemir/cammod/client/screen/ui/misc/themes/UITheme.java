package org.astemir.cammod.client.screen.ui.misc.themes;

import net.minecraft.resources.ResourceLocation;
import org.astemir.cammod.client.screen.ui.misc.TextureSet;

import java.util.HashMap;
import java.util.Map;

public class UITheme {

    public Map<Class<?>, TextureSet[]> sets = new HashMap<>();
    private ResourceLocation texture;

    public UITheme(ResourceLocation texture) {
        this.texture = texture;
    }

    public UITheme newAtlasSet(Class className, TextureSet... set){
        this.sets.put(className,set);
        return this;
    }

    public TextureSet getAtlases(Class<?> className){
        return getAtlases(className,0);
    }

    public TextureSet getAtlases(Class className,int index){
        for (Map.Entry<Class<?>, TextureSet[]> entry : sets.entrySet()) {
            if (entry.getKey().isAssignableFrom(className)){
                return entry.getValue()[index];
            }
        }
        return sets.get(className)[index];
    }

    public Map<Class<?>, TextureSet[]> getSets() {
        return sets;
    }

    public ResourceLocation getTexture() {
        return texture;
    }
}
