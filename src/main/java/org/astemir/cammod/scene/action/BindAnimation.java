package org.astemir.cammod.scene.action;


public abstract class BindAnimation extends BindAction{

    private String animationName;

    public BindAnimation(BindType type, String animationName) {
        super(type);
        this.animationName = animationName;
    }

    public String getAnimationName() {
        return animationName;
    }
}
