package org.astemir.cammod.client.screen.ui.misc;


import org.astemir.api.math.components.Vector2;

public class AtlasProperties {

    private Vector2 elementSize = new Vector2(16,16);
    private String name = "";

    public AtlasProperties(String name) {
        this.name = name;
    }

    public AtlasProperties elementSize(Vector2 size){
        this.elementSize = size;
        return this;
    }

    public Vector2 getElementSize() {
        return elementSize;
    }

    public String getName() {
        return name;
    }
}
