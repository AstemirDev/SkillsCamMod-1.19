package org.astemir.cammod.scene.camera;

import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.client.event.CameraAdvancedSetupEvent;
import org.astemir.api.client.event.CameraPreMatrixResetEvent;
import org.astemir.api.common.entity.ClientSideValue;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.collection.Couple;
import org.astemir.api.math.components.Vector2;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.math.random.RandomUtils;
import org.astemir.cammod.scene.MainScene;

public class CameraController {


    public static CameraController INSTANCE = new CameraController();
    private CameraAnimator animator = new CameraAnimator();
    private ClientSideValue zoom = new ClientSideValue(0,1);
    private Vector3 position = new Vector3(0,0,0);
    private Vector2 rotation = new Vector2(0,0);
    private Vector3 offset = new Vector3(0,0,0);
    private float ticks = 0;
    private float zoomCoefficient = 0.75f;
    private float xRange = 2;
    private float yRange = 1;
    private float xRotSmooth = 0;
    private float yRotSmooth = 0;
    private boolean isZooming = false;
    public boolean isUsed = false;


    public void enable(){
        isUsed = true;
        if (!animator.getCameraPath().isEmpty()){
            CameraPathPoint point = animator.getCameraPath().getPoints().get(0);
            position = point.getPosition();
            rotation = point.getRotation();
        }
    }

    public void disable(){
        isUsed = false;
        ticks = 0;
    }


    public void tick(CameraAdvancedSetupEvent e){
        zoom.update(0.1f);
        if (isUsed){
            if (!animator.getCameraPath().isEmpty()) {
                Couple<Vector3,Vector2> point = animator.getCameraPath().currentPosition(ticks);
                float delta = Minecraft.getInstance().getDeltaFrameTime();
                float length = animator.getCameraPath().getLength();
                ticks += delta;
                if (ticks < length) {
                    float rotX = MathUtils.lerpRot(MathUtils.rad(rotation.x), MathUtils.rad(point.getValue().x), delta);
                    float rotY = MathUtils.lerpRot(MathUtils.rad(rotation.y), MathUtils.rad(point.getValue().y), delta);
                    position = position.lerp(point.getKey(), delta);
                    rotation = new Vector2(MathUtils.deg(rotX), MathUtils.deg(rotY));
                    e.setPosition(position);
                    e.setRotation(rotation);
                    e.disableVanillaBehavior();
                } else {
                    disable();
                    ticks = 0;
                }
            }
        }
    }



    public void preSetup(CameraPreMatrixResetEvent e){
        if (MainScene.getInstance().getProperties().smoothCamera) {
            if (RandomUtils.doWithChance(30) && Minecraft.getInstance().player.tickCount % 40 == 0){
                float f1 = RandomUtils.randomFloat(0.25f,1.5f);
                float f2 = RandomUtils.randomFloat(0.25f,3.5f);
                if (RandomUtils.doWithChance(50)){
                    f1*=-1;
                }
                if (RandomUtils.doWithChance(50)){
                    f2*=-1;
                }
                xRange = f1;
                yRange = f2;
            }
            xRotSmooth = MathUtils.lerpRot(xRotSmooth, xRange, e.getPartialTicks()/120f);
            yRotSmooth = MathUtils.lerpRot(yRotSmooth, yRange, e.getPartialTicks()/120f);
            e.getPoseStack().mulPose(Vector3f.XN.rotationDegrees(xRotSmooth));
            e.getPoseStack().mulPose(Vector3f.YN.rotationDegrees(yRotSmooth));
        }
        if (MainScene.getInstance().getProperties().panic){
            Player player = Minecraft.getInstance().player;
            float ticks = ((float) player.tickCount) + e.getPartialTicks();
            float f1 = MathUtils.cos(ticks * 0.15f) / 50;
            float f2 = MathUtils.sin(ticks * 0.25f) / 70;
            float f4 = 1 + MathUtils.sin(ticks * 0.225f) / 50;
            e.getPoseStack().translate(f1, f2, 0);
            e.getPoseStack().scale(f4, f4, 1);
        }
    }

    public static float smoothStep(float a, float b, float t) {
        if (t < a)
            return 0;
        if (t >= b)
            return 1;
        t = (t - a) / (b - a);
        return t * t * (3 - 2 * t);
    }

    public static Vector2 smoothStep(Vector2 a, Vector2 b, float t) {
        return new Vector2(smoothStep(a.x,b.x,t),smoothStep(a.y,b.y,t));
    }

    public static Vector3 smoothStep(Vector3 a, Vector3 b, float t) {
        return new Vector3(smoothStep(a.x,b.x,t),smoothStep(a.y,b.y,t),smoothStep(a.z,b.z,t));
    }


    public void reset(){
        position = new Vector3(0,0,0);
        rotation = new Vector2(0,0);
        offset = new Vector3(0,0,0);
        animator.clear();
    }

    public ClientSideValue getZoom() {
        return zoom;
    }

    public boolean isZooming() {
        return isZooming;
    }

    public void setZooming(boolean zooming) {
        isZooming = zooming;
    }

    public float getZoomCoefficient() {
        return zoomCoefficient;
    }

    public void setZoomCoefficient(float zoomCoefficient) {
        this.zoomCoefficient = zoomCoefficient;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setRotation(Vector2 rotation) {
        this.rotation = rotation;
    }

    public void setOffset(Vector3 offset) {
        this.offset = offset;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector2 getRotation() {
        return rotation;
    }

    public Vector3 getOffset() {
        return offset;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public float getTicks() {
        return ticks;
    }

    public CameraAnimator getAnimator() {
        return animator;
    }


    public static CameraController getInstance() {
        return INSTANCE;
    }
}
