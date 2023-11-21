package org.astemir.cammod.client.input;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public abstract class KeyBind {

    private KeyMapping mapping;
    private final int delay;
    private int time = 0;
    private boolean pressed = false;

    public KeyBind(KeyMapping mapping,int delay) {
        this.mapping = mapping;
        this.delay = delay;
    }

    public void checkPressed(){
        if (time == 0) {
            if (mapping.isDown()) {
                time = delay;
                onPressed(Minecraft.getInstance());
                pressed = true;
            }else{
                if (pressed) {
                    pressed = false;
                    onReleased(Minecraft.getInstance());
                }
            }
        }else{
            time--;
        }
    }

    public boolean isPressed() {
        return pressed;
    }

    abstract void onPressed(Minecraft minecraft);

    void onReleased(Minecraft minecraft){};
}
