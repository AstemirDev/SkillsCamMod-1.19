package org.astemir.cammod.scene.action;


public abstract class BindButton extends BindAction{

    private int key;

    public BindButton(BindType type,int key) {
        super(type);
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
