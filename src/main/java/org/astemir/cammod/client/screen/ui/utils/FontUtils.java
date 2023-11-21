package org.astemir.cammod.client.screen.ui.utils;

import net.minecraft.client.gui.Font;

public class FontUtils {

    public static int width(Font font,String text){
        if (!text.isEmpty()) {
            return font.width(text);
        }else{
            return 0;
        }
    }

    public static int height(Font font,String text){
        if (!text.isEmpty()) {
            return font.wordWrapHeight(text, width(font, text));
        }else{
            return 0;
        }
    }
}
