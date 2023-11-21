package org.astemir.cammod.client.screen.ui.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import org.astemir.cammod.client.screen.ui.nodes.Node;
import org.astemir.cammod.client.screen.ui.utils.RenderUtils;

import java.util.HashMap;
import java.util.Map;

public class AtlasTexture {

    private Map<String, Area> textures = new HashMap<>();

    public AtlasTexture texture(String name, float x, float y, float width, float height){
        this.textures.put(name,new Area(x,y,width,height));
        return this;
    }

    public void draw(String textureName,PoseStack stack, float x, float y,float scaleX,float scaleY) {
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x, y, area.getX(), area.getY(), area.getWidth(), area.getHeight(),scaleX,scaleY);
        }
    }

    public void drawXYIndex(String textureName, PoseStack stack, int horizontalIndex, int verticalIndex, float x, float y,float scaleX,float scaleY){
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x+(horizontalIndex*area.getWidth()*scaleX), y+(verticalIndex*area.getHeight()*scaleX), area.getX(), area.getY(), area.getWidth(), area.getHeight(),scaleX,scaleY);
        }
    }

    public void drawXIndex(String textureName, PoseStack stack, int horizontalIndex, float x, float y,float scaleX,float scaleY){
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x+(horizontalIndex*area.getWidth()*scaleX), y, area.getX(), area.getY(), area.getWidth(), area.getHeight(),scaleX,scaleY);
        }
    }

    public void drawYIndex(String textureName, PoseStack stack, int verticalIndex, float x, float y,float scaleX,float scaleY){
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x, y+(verticalIndex*area.getHeight()*scaleY), area.getX(), area.getY(), area.getWidth(), area.getHeight(),scaleX,scaleY);
        }
    }

    public void draw(String textureName,PoseStack stack, float x, float y) {
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x, y, area.getX(), area.getY(), area.getWidth(), area.getHeight());
        }
    }

    public void drawXYIndex(String textureName, PoseStack stack, int horizontalIndex, int verticalIndex, float x, float y){
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x+(horizontalIndex*area.getWidth()), y+(verticalIndex*area.getHeight()), area.getX(), area.getY(), area.getWidth(), area.getHeight());
        }
    }

    public void drawXIndex(String textureName, PoseStack stack, int horizontalIndex, float x, float y){
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x+(horizontalIndex*area.getWidth()), y, area.getX(), area.getY(), area.getWidth(), area.getHeight());
        }
    }

    public void drawYIndex(String textureName, PoseStack stack, int verticalIndex, float x, float y){
        Area area = getTexture(textureName);
        if (area != null) {
            RenderUtils.blit(stack, x, y+(verticalIndex*area.getHeight()), area.getX(), area.getY(), area.getWidth(), area.getHeight());
        }
    }

    public Area getTexture(String name){
        return textures.get(name);
    }

    public Map<String, Area> getTextures() {
        return textures;
    }
}
