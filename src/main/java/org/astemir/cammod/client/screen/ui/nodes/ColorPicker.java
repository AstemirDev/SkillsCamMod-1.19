package org.astemir.cammod.client.screen.ui.nodes;


import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.event.NodeEvent;
import org.astemir.cammod.client.screen.ui.nodes.progress.ProgressBar;
import java.util.function.Consumer;

public class ColorPicker extends Node{


    private Color pickedColor = Color.WHITE;
    private ColorRect colorRect;
    private ProgressBar redBar;
    private ProgressBar blueBar;
    private ProgressBar greenBar;
    private ProgressBar alphaBar;
    private NodeEvent.Function<Color> onColorChanged;


    public ColorPicker(Color pickedColor) {
        this();
        this.pickedColor = pickedColor;
    }

    public ColorPicker() {
        setSize(new Vector2(128,32));
    }

    @Override
    public void init() {
        super.init();
        redBar = new ProgressBar(pickedColor.r,0,1){
            @Override
            public void onChangeValue(float value) {
                super.onChangeValue(value);
                pickedColor = new Color(value,pickedColor.g,pickedColor.b,pickedColor.a);
                colorRect.setFillColor(pickedColor);
                onColorChanged.onHandle(pickedColor);
            }
        };
        redBar.setSize(new Vector2(getSize().x/2,8));
        redBar.setPosition(new Vector2(36,0));
        redBar.setProgressColor(Color.RED);
        addChild(redBar);

        greenBar = new ProgressBar(pickedColor.g,0,1){
            @Override
            public void onChangeValue(float value) {
                super.onChangeValue(value);
                pickedColor = new Color(pickedColor.r,value,pickedColor.b,pickedColor.a);
                colorRect.setFillColor(pickedColor);
                onColorChanged.onHandle(pickedColor);
            }
        };
        greenBar.setSize(new Vector2(getSize().x/2,8));
        greenBar.setPosition(new Vector2(36,10));
        greenBar.setProgressColor(Color.GREEN);
        addChild(greenBar);

        blueBar = new ProgressBar(pickedColor.b,0,1){
            @Override
            public void onChangeValue(float value) {
                super.onChangeValue(value);
                pickedColor = new Color(pickedColor.r,pickedColor.g,value,pickedColor.a);
                colorRect.setFillColor(pickedColor);
                onColorChanged.onHandle(pickedColor);
            }
        };
        blueBar.setSize(new Vector2(getSize().x/2,8));
        blueBar.setPosition(new Vector2(36,20));
        blueBar.setProgressColor(Color.BLUE);
        addChild(blueBar);

        alphaBar = new ProgressBar(pickedColor.a,0,1){
            @Override
            public void onChangeValue(float value) {
                super.onChangeValue(value);
                pickedColor = new Color(pickedColor.r,pickedColor.g,pickedColor.b,value);
                alphaBar.setProgressColor(new Color(1,1,1,value));
                colorRect.setFillColor(pickedColor);
                onColorChanged.onHandle(pickedColor);
            }
        };
        alphaBar.setSize(new Vector2(getSize().x/2,8));
        alphaBar.setProgressColor(Color.WHITE);
        alphaBar.setPosition(new Vector2(36,30));
        addChild(alphaBar);


        ColorRect borderRect = new ColorRect(Color.BLACK);
        borderRect.setSize(new Vector2(34,34));
        borderRect.setPosition(new Vector2(-1,1));
        addChild(borderRect);

        colorRect = new ColorRect(pickedColor);
        colorRect.setSize(new Vector2(32,32));
        colorRect.setPosition(new Vector2(0,2));
        addChild(colorRect);

    }

    public Color getPickedColor() {
        return pickedColor;
    }

    public void setPickedColor(Color pickedColor) {
        this.pickedColor = pickedColor;
    }

    public ProgressBar getRedBar() {
        return redBar;
    }

    public ProgressBar getBlueBar() {
        return blueBar;
    }

    public ProgressBar getGreenBar() {
        return greenBar;
    }

    public ProgressBar getAlphaBar() {
        return alphaBar;
    }

    public ColorPicker setOnColorChanged(NodeEvent.Function<Color> onColorChanged) {
        this.onColorChanged = onColorChanged;
        return this;
    }
}
