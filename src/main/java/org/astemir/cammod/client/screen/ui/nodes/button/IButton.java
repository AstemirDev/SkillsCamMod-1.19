package org.astemir.cammod.client.screen.ui.nodes.button;


import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import org.astemir.cammod.client.screen.ui.misc.Area;

public interface IButton {

    default boolean iPress(double x, double y, int button){
        if (getClickableArea().contains((float)x,(float)y)){
            onButtonPressed(button);
            if (getSound() != null) {
                Minecraft.getInstance().player.playSound(getSound(), 1, 1);
            }
            return true;
        }
        return false;
    }

    default boolean iRelease(double x, double y, int button){
        if (getClickableArea().contains((float)x,(float)y)){
            onButtonReleased(button);
            if (getSound() != null) {
                Minecraft.getInstance().player.playSound(getSound(), 1, 1.5f);
            }
            return true;
        }
        return false;
    }

    default SoundEvent getSound(){
        return SoundEvents.UI_BUTTON_CLICK;
    }

    Area getClickableArea();

    void onButtonPressed(int button);

    void onButtonReleased(int button);
}
