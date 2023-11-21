package org.astemir.cammod.client.renderer.clone;



import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModel;
import org.astemir.api.client.wrapper.SkillsWrapperEntity;
import org.astemir.api.math.components.Color;
import org.astemir.cammod.SkillsCamMod;
import org.astemir.cammod.common.entity.EntityClone;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class WrapperClone extends SkillsWrapperEntity<EntityClone> {

    public static Map<String, ModelClone> models = new HashMap<>();


    public WrapperClone() {
        loadModels();
    }

    public static void loadModels(){
        models.clear();
        JsonParser parser = new JsonParser();
        InputStream stream = null;
        try {
            stream = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(SkillsCamMod.MOD_ID,"models.json")).get().open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonElement parsed = parser.parse(new InputStreamReader(stream));
        for (Map.Entry<String, JsonElement> modelEntry : parsed.getAsJsonObject().entrySet()) {
            JsonObject content = modelEntry.getValue().getAsJsonObject();
            CloneTexture texture = CloneTexture.fromJson(content.get("texture"));
            ResourceLocation model = new ResourceLocation(content.get("model").getAsString());
            ResourceLocation animations = new ResourceLocation(content.get("animations").getAsString());
            ModelClone modelClone = new ModelClone(texture,model,animations);
            if (content.has("transparent")){
                modelClone.transparent = content.get("transparent").getAsBoolean();
            }
            if (content.has("light")){
                modelClone.modelLight = content.get("light").getAsInt();
            }
            if (content.has("layers")){
                for (Map.Entry<String, JsonElement> layerEntry : content.get("layers").getAsJsonObject().entrySet()) {
                    JsonObject layerContent = layerEntry.getValue().getAsJsonObject();
                    CloneTexture layerTexture = CloneTexture.fromJson(layerContent.get("texture"));
                    boolean shimmer = false;
                    boolean glow = false;
                    boolean transparent = false;
                    Color color = Color.WHITE;
                    if (layerContent.has("shimmer")){
                        shimmer = layerContent.get("shimmer").getAsBoolean();
                    }
                    if (layerContent.has("glow")){
                        glow = layerContent.get("glow").getAsBoolean();
                    }
                    if (layerContent.has("transparent")){
                        transparent = layerContent.get("transparent").getAsBoolean();
                    }
                    if (layerContent.has("color")){
                        JsonArray colorArray = layerContent.get("color").getAsJsonArray();
                        int[] colors = new int[colorArray.size()];
                        for (int i = 0; i < colorArray.size(); i++) {
                            colors[i] = colorArray.get(i).getAsInt();
                        }
                        color = Color.fromArray(colors);
                    }
                    LayerClone layerClone = new LayerClone(modelClone,layerTexture,color,shimmer,glow);
                    layerClone.setTransparent(transparent);
                    modelClone.addLayer(layerClone);
                }
            }
            models.put(modelEntry.getKey(),modelClone);
        }
    }

    @Override
    public SkillsModel<EntityClone, IDisplayArgument> getModel(EntityClone target) {
        SkillsModel<EntityClone,IDisplayArgument> model = models.get(target.getModelName());
        if (model == null){
            return models.get("default");
        }else {
            return models.get(target.getModelName());
        }
    }
}
