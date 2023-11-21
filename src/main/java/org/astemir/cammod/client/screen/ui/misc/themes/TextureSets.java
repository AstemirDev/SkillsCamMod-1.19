package org.astemir.cammod.client.screen.ui.misc.themes;

import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.misc.TextureSet;
import org.astemir.cammod.client.screen.ui.misc.UIProperty;

public class TextureSets {

    public static final TextureSet BUTTON = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("top_0",0,0,11,11).
                    texture("top_1",13,0,11,11).
                    texture("top_2",26,0,11,11).
                    texture("middle_0",0,13,11,11).
                    texture("middle_1",13,13,11,11).
                    texture("middle_2",26,13,11,11).
                    texture("bottom_0",0,26,11,11).
                    texture("bottom_1",13,26,11,11).
                    texture("bottom_2",26,26,11,11)).
            put(UIProperty.SELECTED,new AtlasTexture().
                    texture("top_0",0,43,11,11).
                    texture("top_1",13,43,11,11).
                    texture("top_2",26,43,11,11).
                    texture("middle_0",0,56,11,11).
                    texture("middle_1",13,56,11,11).
                    texture("middle_2",26,56,11,11).
                    texture("bottom_0",0,69,11,11).
                    texture("bottom_1",13,69,11,11).
                    texture("bottom_2",26,69,11,11));

    public static final TextureSet BUTTON_OLD_FASHIONED = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("top_0",48,0,11,11).
                    texture("top_1",61,0,11,11).
                    texture("top_2",74,0,11,11).
                    texture("middle_0",48,13,11,11).
                    texture("middle_1",61,13,11,11).
                    texture("middle_2",74,13,11,11).
                    texture("bottom_0",48,26,11,11).
                    texture("bottom_1",61,26,11,11).
                    texture("bottom_2",74,26,11,11)).
            put(UIProperty.SELECTED,new AtlasTexture().
                    texture("top_0",48,43,11,11).
                    texture("top_1",61,43,11,11).
                    texture("top_2",74,43,11,11).
                    texture("middle_0",48,56,11,11).
                    texture("middle_1",61,56,11,11).
                    texture("middle_2",74,56,11,11).
                    texture("bottom_0",48,69,11,11).
                    texture("bottom_1",61,69,11,11).
                    texture("bottom_2",74,69,11,11));

    public static final TextureSet INPUT_BOX = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("top_0",80,0,12,12).
                    texture("top_1",94,0,12,12).
                    texture("top_2",108,0,12,12).
                    texture("bottom_0",80,14,12,12).
                    texture("bottom_1",94,14,12,12).
                    texture("bottom_2",108,14,12,12));

    public static final TextureSet CHECKBOX = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("disabled",48,43,16,16).
                    texture("enabled",64,43,16,16)).
            put(UIProperty.SELECTED,new AtlasTexture().
                    texture("disabled",48,59,16,16).
                    texture("enabled",64,59,16,16));


    public static final TextureSet PANEL_BUTTONS = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("close_button",149,0,9,9).
                    texture("fullscreen_button",160,0,9,9).
                    texture("collapse_button",171,0,9,9)).
            put(UIProperty.SELECTED,new AtlasTexture().
                    texture("close_button",149,11,9,9).
                    texture("fullscreen_button",160,11,9,9).
                    texture("collapse_button",171,11,9,9));

    public static final TextureSet PROGRESS_BAR = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("bar_begin",0,193,13,8).
                    texture("bar_middle",15,193,11,8).
                    texture("bar_end",28,193,13,8).
                    texture("fill_flat",2,205,11,4).
                    texture("fill_segmented_1x",2,227,11,4).
                    texture("fill_segmented_2x",2,219,11,4).
                    texture("fill_segmented_5x",2,212,11,4)
            );


    public static final TextureSet SLIDER_VERTICAL = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("slider",64,120,12,15).
                    texture("bar_top",80,120,14,14).
                    texture("bar_middle",80,137,14,14).
                    texture("bar_bottom",80,154,14,14)).
            put(UIProperty.SELECTED,new AtlasTexture().
                    texture("slider",64,136,12,15).
                    texture("bar_top",80,120,14,14).
                    texture("bar_middle",80,137,14,14).
                    texture("bar_bottom",80,154,14,14));

    public static final TextureSet PANEL = new TextureSet().
            put(UIProperty.DEFAULT,new AtlasTexture().
                    texture("top_0",0,120,16,16).
                    texture("top_1",19,120,16,16).
                    texture("top_2",38,120,16,16).
                    texture("middle_0",0,139,16,16).
                    texture("middle_1",19,139,16,16).
                    texture("middle_2",38,139,16,16).
                    texture("bottom_0",0,158,16,16).
                    texture("bottom_1",19,158,16,16).
                    texture("bottom_2",38,158,16,16));
}
