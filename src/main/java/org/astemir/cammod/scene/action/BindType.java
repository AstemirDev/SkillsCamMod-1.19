package org.astemir.cammod.scene.action;

public enum BindType {

    IDLE,WALK,HURT,IN_AIR,IN_WATER,ON_GROUND,SPRINT,SNEAK_IDLE,SNEAK_WALK,BUTTON_PRESSED,BUTTON_RELEASED,ANIMATION_END;



    public static BindType[] getDefaultBinds(){
        return new BindType[]{IDLE,WALK,HURT,IN_AIR,IN_WATER,ON_GROUND,SPRINT,SNEAK_IDLE,SNEAK_WALK};
    }
}
