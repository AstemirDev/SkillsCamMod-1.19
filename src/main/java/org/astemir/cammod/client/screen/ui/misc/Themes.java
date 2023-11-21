package org.astemir.cammod.client.screen.ui.misc;

import org.astemir.api.io.ResourceUtils;
import org.astemir.cammod.SkillsCamMod;
import org.astemir.cammod.client.screen.ui.misc.themes.TextureSets;
import org.astemir.cammod.client.screen.ui.misc.themes.UITheme;
import org.astemir.cammod.client.screen.ui.nodes.*;
import org.astemir.cammod.client.screen.ui.nodes.button.Button;
import org.astemir.cammod.client.screen.ui.nodes.button.CheckBox;
import org.astemir.cammod.client.screen.ui.nodes.button.PanelButton;
import org.astemir.cammod.client.screen.ui.nodes.panel.Window;
import org.astemir.cammod.client.screen.ui.nodes.progress.ProgressBar;

public class Themes {

    public static final UITheme DEFAULT = new UITheme(ResourceUtils.loadTexture(SkillsCamMod.MOD_ID,"ui/modern.png")).
            newAtlasSet(Button.class, TextureSets.BUTTON,TextureSets.BUTTON_OLD_FASHIONED).
            newAtlasSet(InputBox.class, TextureSets.INPUT_BOX).
            newAtlasSet(SliderVertical.class, TextureSets.SLIDER_VERTICAL).
            newAtlasSet(ProgressBar.class, TextureSets.PROGRESS_BAR).
            newAtlasSet(PanelButton.class,TextureSets.PANEL_BUTTONS).
            newAtlasSet(CheckBox.class, TextureSets.CHECKBOX).
            newAtlasSet(Window.class,TextureSets.PANEL);
}
