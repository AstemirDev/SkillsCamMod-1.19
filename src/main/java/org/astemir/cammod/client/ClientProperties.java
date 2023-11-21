package org.astemir.cammod.client;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.io.FileUtils;
import org.astemir.api.io.ResourceUtils;
import org.astemir.api.io.json.JsonWrap;
import org.astemir.api.math.components.Color;
import org.astemir.cammod.SkillsCamMod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ClientProperties implements ResourceManagerReloadListener {

    public float sunXRot = 0;
    public float sunYRot = 0;
    public boolean customSunRot = false;

    public float sunSize = 40;
    public boolean customSunSize = false;

    public Color sunColor = Color.WHITE;
    public boolean customSunColor = false;


    public float darkness = 0;
    public boolean customDarkness = false;

    public float starBrightness = 0;
    public boolean customStarBrightness = false;

    public Color skyColor = Color.WHITE;
    public boolean customSkyColor = false;

    public Color sunriseColor = Color.WHITE;
    public boolean customSunriseColor = false;

    public Color cloudsColor = Color.WHITE;
    public boolean customCloudsColor = false;

    public Color fogColor = Color.WHITE;
    public boolean customFogColor = false;

    public float fogFarDistance = 0;
    public float fogNearDistance = 0;
    public boolean customFogDistance = false;

    public FogShape fogShape = FogShape.SPHERE;
    public boolean customFogShape = false;

    public Vec3 cameraOffset = new Vec3(0,0,0);
    public boolean customCameraOffset = false;

    public Vec3 cameraPosition = new Vec3(0,0,0);
    public boolean customCameraPosition = false;

    public Vec2 cameraRotation = new Vec2(0,0);
    public boolean customCameraRotation = false;

    public boolean unlockedCamera = false;

    public boolean smoothCamera = false;

    public boolean panic = false;

    public float zoomSpeed = 0.06f;

    public ClientProperties() {
        loadConfiguration();
    }

    public void loadConfiguration(){
        InputStream stream = null;
        try {
            stream = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(SkillsCamMod.MOD_ID,"properties.json")).get().open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean status = false;
        if (stream != null){
            JsonWrap wrap = new JsonWrap(FileUtils.readText(stream));
            zoomSpeed = wrap.get("zoom_speed",Float.class);
            status = true;
        }
        System.out.println("Load Configuration: "+(status ? "OK" : "FAILED"));
    }

    public void setSunXRot(float sunXRot) {
        this.sunXRot = sunXRot;
        this.customSunRot = true;
    }

    public void setSunYRot(float sunYRot) {
        this.sunYRot = sunYRot;
        this.customSunRot = true;
    }

    public void setSunSize(float sunSize) {
        this.sunSize = sunSize;
        this.customSunSize = true;
    }

    public void setSunColor(Color sunColor) {
        this.sunColor = sunColor;
        this.customSunColor = true;
    }

    public void setDarkness(float darkness) {
        this.darkness = darkness;
        this.customDarkness = true;
    }

    public void setStarBrightness(float starBrightness) {
        this.starBrightness = starBrightness;
        this.customStarBrightness = true;
    }

    public void setSkyColor(Color skyColor) {
        this.skyColor = skyColor;
        this.customSkyColor = true;
    }

    public void setSunriseColor(Color sunriseColor) {
        this.sunriseColor = sunriseColor;
        this.customSunriseColor = true;
    }

    public void setCloudsColor(Color cloudsColor) {
        this.cloudsColor = cloudsColor;
        this.customCloudsColor = true;
    }

    public void setFogColor(Color fogColor) {
        this.fogColor = fogColor;
        this.customFogColor = true;
    }

    public void setFogFarDistance(float fogFarDistance) {
        this.fogFarDistance = fogFarDistance;
        this.customFogDistance = true;
    }

    public void setFogNearDistance(float fogNearDistance) {
        this.fogNearDistance = fogNearDistance;
        this.customFogDistance = true;
    }

    public void setFogShape(FogShape fogShape) {
        this.fogShape = fogShape;
        this.customFogShape = true;
    }

    public void setCameraOffset(Vec3 cameraOffset) {
        this.cameraOffset = cameraOffset;
        this.customCameraOffset = true;
    }

    public void setCameraPosition(Vec3 cameraPosition) {
        this.cameraPosition = cameraPosition;
        this.customCameraPosition = true;
    }

    public void setCameraRotation(Vec2 cameraRotation) {
        this.cameraRotation = cameraRotation;
        this.customCameraRotation = true;
    }

    public void setUnlockedCamera(boolean unlockedCamera) {
        this.unlockedCamera = unlockedCamera;
    }

    public void setSmoothCamera(boolean smoothCamera) {
        this.smoothCamera = smoothCamera;
    }


    public void resetSunRot() {
        this.customSunRot = false;
        this.sunYRot = 0;
        this.sunXRot = 0;
    }

    public void resetSunSize() {
        this.customSunSize = false;
        this.sunSize = 40;
    }

    public void resetSunColor() {
        this.sunColor = Color.WHITE;
        this.customSunColor = false;
    }

    public void resetDarkness() {
        this.customDarkness = false;
    }

    public void resetStarBrightness() {
        this.customStarBrightness = false;
    }

    public void resetSkyColor() {
        this.customSkyColor = false;
    }

    public void resetSunriseColor() {
        this.sunriseColor = Color.WHITE;
        this.customSunriseColor = false;
    }

    public void resetCloudsColor() {
        this.cloudsColor = Color.WHITE;
        this.customCloudsColor = false;
    }

    public void resetFogColor() {
        this.fogColor = Color.WHITE;
        this.customFogColor = false;
    }

    public void resetFogDistance() {
        this.customFogDistance = false;
    }

    public void resetFogShape() {
        this.customFogShape = false;
    }

    public void resetCameraOffset() {
        this.customCameraOffset = false;
    }

    public void resetCameraPosition() {
        this.customCameraPosition = false;
    }

    public void resetCameraRotation() {
        this.customCameraRotation = false;
    }

    @Override
    public void onResourceManagerReload(ResourceManager pResourceManager) {
        loadConfiguration();
    }
}
