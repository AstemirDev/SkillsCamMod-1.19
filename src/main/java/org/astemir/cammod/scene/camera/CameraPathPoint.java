package org.astemir.cammod.scene.camera;


import org.astemir.api.math.components.Vector2;
import org.astemir.api.math.components.Vector3;

public class CameraPathPoint {

    private Vector3 position = new Vector3(0,0,0);
    private Vector2 rotation = new Vector2(0,0);
    private CameraEasing easing = CameraEasing.DEFAULT;
    private float time = 0;
    private float speed = 0.5f;

    public CameraPathPoint(Vector3 position, Vector2 rotation, CameraEasing easing, float time, float speed) {
        this.position = position;
        this.rotation = rotation;
        this.easing = easing;
        this.time = time;
        this.speed = speed;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public Vector2 getRotation() {
        return rotation;
    }

    public void setRotation(Vector2 rotation) {
        this.rotation = rotation;
    }

    public CameraEasing getEasing() {
        return easing;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setEasing(CameraEasing easing) {
        this.easing = easing;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
