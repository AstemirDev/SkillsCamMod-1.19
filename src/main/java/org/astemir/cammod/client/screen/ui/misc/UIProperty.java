package org.astemir.cammod.client.screen.ui.misc;

public enum UIProperty {

    DEFAULT,SELECTED,CUSTOM;


    public static UIProperty isSelected(boolean value){
        if (value){
            return SELECTED;
        }
        return DEFAULT;
    }

    public static UIProperty forName(String name){
        switch (name.toLowerCase()){
            case "default":{
                return DEFAULT;
            }
            case "selected":{
                return SELECTED;
            }
        }
        return DEFAULT;
    }
}
