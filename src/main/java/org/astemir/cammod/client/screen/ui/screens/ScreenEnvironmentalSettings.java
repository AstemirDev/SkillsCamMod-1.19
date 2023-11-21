package org.astemir.cammod.client.screen.ui.screens;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.event.NodeEvent;
import org.astemir.cammod.client.screen.ui.nodes.*;
import org.astemir.cammod.client.screen.ui.nodes.button.Button;
import org.astemir.cammod.client.screen.ui.nodes.button.ButtonStyle;
import org.astemir.cammod.client.screen.ui.nodes.panel.Window;
import org.astemir.cammod.client.screen.ui.nodes.panel.ScrollableWindow;
import org.astemir.cammod.client.screen.ui.nodes.progress.ProgressBar;
import org.astemir.cammod.client.ClientProperties;
import org.astemir.cammod.scene.MainScene;

public class ScreenEnvironmentalSettings extends NodeScreen {

    private Window mainPanel;

    public ScreenEnvironmentalSettings(Screen lastScreen) {
        super(lastScreen, Component.empty(),new Vector2(340,220));
    }

    @Override
    protected void init() {
        super.init();
        mainPanel = new ScrollableWindow();
        fitNode(mainPanel);
        centerNode(mainPanel);

        ClientProperties properties = MainScene.getInstance().getProperties();

        //Labels
        mainPanel.addChild(new Label("Настройки солнца:").
                setCentered(false).
                setPosition(new Vector2(20,32)));

        mainPanel.addChild(new Label("X:").
                setCentered(false).
                setPosition(new Vector2(20,48)));

        mainPanel.addChild(new Label("Y:").
                setCentered(false).
                setPosition(new Vector2(60,48)));

        mainPanel.addChild(new Label("Размер:").
                setCentered(false).
                setPosition(new Vector2(100,48)));

        mainPanel.addChild(new Label("Цвет:").
                setCentered(false).
                setPosition(new Vector2(20,70)));

        mainPanel.addChild(new Label("Цвет заката:").
                setCentered(false).
                setPosition(new Vector2(140,70)));

        mainPanel.addChild(new Label("Настройки тумана:").
                setCentered(false).
                setPosition(new Vector2(20,170)));

        mainPanel.addChild(new Label("Начало:").
                setCentered(false).
                setPosition(new Vector2(20,190)));

        mainPanel.addChild(new Label("Конец:").
                setCentered(false).
                setPosition(new Vector2(150,190)));

        mainPanel.addChild(new Label("Цвет тумана:").
                setCentered(false).
                setPosition(new Vector2(20,220)));

        mainPanel.addChild(new Label("Настройки неба:").
                setCentered(false).
                setPosition(new Vector2(20,320)));

        mainPanel.addChild(new Label("Цвет неба:").
                setCentered(false).
                setPosition(new Vector2(20,336)));

        mainPanel.addChild(new Label("Цвет облаков:").
                setCentered(false).
                setPosition(new Vector2(140,336)));

        mainPanel.addChild(new Label("Темнота:").
                setCentered(false).
                setPosition(new Vector2(20,400)));

        mainPanel.addChild(new Label("Яркость звёзд:").
                setCentered(false).
                setPosition(new Vector2(20,430)));

        //Progress bars
        ProgressBar sunXRot = (ProgressBar) new ProgressBar(properties.sunYRot,0,360).
                setProgressColor(Color.RED).
                setOnValueChanged(NodeEvent.function(properties::setSunYRot)).
                setPosition(new Vector2(20,58)).
                setSize(new Vector2(32,8));
        mainPanel.addChild(sunXRot);

        ProgressBar sunYRot = (ProgressBar) new ProgressBar(properties.sunXRot,0,360).
                setProgressColor(Color.BLUE).
                setOnValueChanged(NodeEvent.function(properties::setSunXRot)).
                setPosition(new Vector2(60,58)).
                setSize(new Vector2(32,8));

        mainPanel.addChild(sunYRot);

        ProgressBar sunSize = (ProgressBar) new ProgressBar(properties.sunSize,0,200).
                setProgressColor(Color.GREEN).
                setOnValueChanged(NodeEvent.function(properties::setSunSize)).
                setPosition(new Vector2(100,58)).
                setSize(new Vector2(48,8));
        mainPanel.addChild(sunSize);

        ProgressBar fogNear = (ProgressBar) new ProgressBar(properties.fogNearDistance,0,50).
                setProgressColor(Color.PURPLE).
                setOnValueChanged(NodeEvent.function(properties::setFogNearDistance)).
                setPosition(new Vector2(20,204)).
                setSize(new Vector2(110,8));
        mainPanel.addChild(fogNear);

        ProgressBar fogFar = (ProgressBar) new ProgressBar(properties.fogFarDistance,0,50).
                setProgressColor(Color.AQUA).
                setOnValueChanged(NodeEvent.function(properties::setFogFarDistance)).
                setPosition(new Vector2(150,204)).
                setSize(new Vector2(110,8));
        mainPanel.addChild(fogFar);

        ProgressBar darkness = (ProgressBar) new ProgressBar(properties.darkness,0,1).
                setProgressColor(Color.BLACK).
                setOnValueChanged(NodeEvent.function(properties::setDarkness)).
                setPosition(new Vector2(20,410)).
                setSize(new Vector2(110,8));
        mainPanel.addChild(darkness);

        ProgressBar starBrightness = (ProgressBar) new ProgressBar(properties.starBrightness,0,1).
                setProgressColor(Color.WHITE).
                setOnValueChanged(NodeEvent.function(properties::setStarBrightness)).
                setPosition(new Vector2(20,440)).
                setSize(new Vector2(110,8));
        mainPanel.addChild(starBrightness);

        //Color Pickers
        ColorPicker sunColorPicker = (ColorPicker) new ColorPicker(properties.sunColor).
                setOnColorChanged(NodeEvent.function(properties::setSunColor)).
                setPosition(new Vector2(20,80));
        mainPanel.addChild(sunColorPicker);

        ColorPicker sunriseColorPicker = (ColorPicker) new ColorPicker(properties.sunriseColor).
                setOnColorChanged(NodeEvent.function(properties::setSunriseColor)).
                setPosition(new Vector2(140,80));
        mainPanel.addChild(sunriseColorPicker);

        ColorPicker fogColorPicker = (ColorPicker) new ColorPicker(properties.fogColor).
                setOnColorChanged(NodeEvent.function(properties::setFogColor)).
                setPosition(new Vector2(20,234));
        mainPanel.addChild(fogColorPicker);

        ColorPicker skyColorPicker = (ColorPicker) new ColorPicker(properties.skyColor).
                setOnColorChanged(NodeEvent.function(properties::setSkyColor)).
                setPosition(new Vector2(20,350));
        mainPanel.addChild(skyColorPicker);

        ColorPicker cloudsColorPicker = (ColorPicker) new ColorPicker(properties.cloudsColor).
                setOnColorChanged(NodeEvent.function(properties::setCloudsColor)).
                setPosition(new Vector2(140,350));
        mainPanel.addChild(cloudsColorPicker);

        //Buttons
        mainPanel.addChild(
            new Button().text("Сброс").style(ButtonStyle.OLD_FASHIONED).
                    setOnPressed(NodeEvent.run(()->{
                        sunXRot.value(0);
                        sunYRot.value(0);
                        sunSize.value(40);
                        properties.resetSunRot();
                        properties.resetSunSize();
                        properties.resetSunColor();
                        properties.resetSunriseColor();
                    })).
                    setSize(new Vector2(128,22)).
                    setPosition(new Vector2(56,128)));

        mainPanel.addChild(
            new Button().text("Сброс").style(ButtonStyle.OLD_FASHIONED).
                    setOnPressed(NodeEvent.run(()->{
                        fogFar.value(0);
                        fogNear.value(0);
                        properties.resetFogColor();
                        properties.resetFogDistance();
                    })).
                    setSize(new Vector2(128,22)).
                    setPosition(new Vector2(56,282)));

        addNode(new Label("Настройки окружения").
                setPosition(new Vector2(this.width/2,((this.height/2)-height/2)+14)));
        addNode(mainPanel);
    }

}
