package org.astemir.cammod.client.screen.ui.utils;


import org.astemir.api.math.components.Vector2;

public class AlignmentUtils {

    public Vector2 centered(float x, float y, float width, float height){
        return new Vector2(x-width/2,y-height/2);
    }

    public Vector2 centeredHorizontal(float x,float y,float width){
        return new Vector2(x-width/2,y);
    }

    public Vector2 centeredVertical(float x,float y,float height){
        return new Vector2(x,y-height/2);
    }
}
