package org.astemir.cammod.scene.camera;


import org.astemir.api.client.animation.AnimationUtils;

public abstract class CameraEasing {

    public static final CameraEasing DEFAULT = new CameraEasing() {
        @Override
        float ease(float t) {
            return t;
        }
    };

    public static final CameraEasing EASE_IN = new CameraEasing() {
        @Override
        float ease(float t) {
            return AnimationUtils.easingIn(t);
        }
    };


    public static final CameraEasing EASE_OUT = new CameraEasing() {
        @Override
        float ease(float t) {
            return AnimationUtils.easingOut(t);
        }
    };


    public static final CameraEasing EASE_IN_OUT = new CameraEasing() {
        @Override
        float ease(float t) {
            return AnimationUtils.easingInOut(t);
        }
    };

    abstract float ease(float t);
}
