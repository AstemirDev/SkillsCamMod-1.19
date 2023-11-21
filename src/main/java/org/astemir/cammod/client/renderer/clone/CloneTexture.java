package org.astemir.cammod.client.renderer.clone;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.astemir.api.client.ResourceArray;

public class CloneTexture {


    private ResourceArray animatedTexture;
    private ResourceLocation texture;

    public CloneTexture(String path,int count,double speed) {
        String[] args = path.split(":");
        String modId = args[0];
        String texturePath = args[1];
        this.animatedTexture = new ResourceArray(modId,texturePath,count,speed);
    }

    public CloneTexture(String path) {
        this.texture = new ResourceLocation(path);
    }


    public static CloneTexture fromJson(JsonElement jsonElement){
        if (jsonElement.isJsonObject()){
            String path = jsonElement.getAsJsonObject().get("path").getAsString();
            double speed = 1.0f;
            if (jsonElement.getAsJsonObject().has("speed")){
                speed = jsonElement.getAsJsonObject().get("speed").getAsDouble();
            }
            int count = jsonElement.getAsJsonObject().get("count").getAsInt();
            return new CloneTexture(path,count,speed);
        }else{
            return new CloneTexture(jsonElement.getAsString());
        }
    }

    public ResourceLocation getTexture(Entity entity){
        if (animatedTexture != null){
            return animatedTexture.getResourceLocation(entity.tickCount);
        }else
        if (texture != null){
            return texture;
        }
        return null;
    }
}
