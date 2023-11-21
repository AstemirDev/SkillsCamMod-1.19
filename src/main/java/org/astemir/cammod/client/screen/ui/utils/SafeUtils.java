package org.astemir.cammod.client.screen.ui.utils;

public class SafeUtils {

    public static float parseFloatValue(String text,float defaultValue){
        try{
            return Float.parseFloat(text);
        }catch (Exception e){
        }
        return defaultValue;
    }

    public static  int parseIntValue(String text,int defaultValue){
        try{
            return Integer.parseInt(text);
        }catch (Exception e){
        }
        return defaultValue;
    }

    public static  boolean parseBoolValue(String text,boolean defaultValue){
        try{
            return Boolean.parseBoolean(text);
        }catch (Exception e){
        }
        return defaultValue;
    }

}
