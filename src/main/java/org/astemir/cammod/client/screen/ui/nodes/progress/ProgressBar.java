package org.astemir.cammod.client.screen.ui.nodes.progress;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.event.NodeEvent;
import org.astemir.cammod.client.screen.ui.misc.Area;
import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.misc.UIProperty;
import org.astemir.cammod.client.screen.ui.nodes.Node;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.function.Consumer;

public class ProgressBar extends Node {


    private static final Vector2 BEGIN_PART_SIZE = new Vector2(13,8);
    private static final Vector2 MIDDLE_PART_SIZE = new Vector2(11,8);
    private static final Vector2 END_PART_SIZE = new Vector2(13,8);

    private static final DecimalFormat FORMAT = new DecimalFormat("###.##",new DecimalFormatSymbols(Locale.ENGLISH));

    private Color progressColor = new Color(0.4f,0.4f,0.44f,0.75f);

    private ProgressBarStyle style = ProgressBarStyle.FLAT;

    public NodeEvent.Function<Float> onValueChanged = NodeEvent.emptyFunction();

    private float maxValue = 1;
    private float minValue = 0;
    private float value = 0;
    private boolean selected = false;


    public ProgressBar(float defaultValue,float minValue, float maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = defaultValue;
        setSize(new Vector2(128,8));
    }

    @Override
    public Node setSize(Vector2 size) {
        int x = (int) ((size.x-BEGIN_PART_SIZE.x-END_PART_SIZE.x)/MIDDLE_PART_SIZE.x);
        int y = (int) (size.y/MIDDLE_PART_SIZE.y);
        Vector2 newSize = new Vector2((x*MIDDLE_PART_SIZE.x)+ BEGIN_PART_SIZE.x+ END_PART_SIZE.x,y*MIDDLE_PART_SIZE.y);
        return super.setSize(newSize);
    }

    @Override
    public void onMouseClicked(double x, double y, int button) {
        selected = getArea().contains((float)x,(float)y);
        if (selected){
            Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK,1,1);
            if (getArea().contains((int)x,(int)y)) {
                float beginX = getGlobalPosition().x;
                float endX = beginX+getSize().x;
                value = MathUtils.arrange(beginX,endX,minValue,maxValue,(float)x);
                onChangeValue(value);
            }
        }
    }

    @Override
    public void onMouseReleased(double x, double y, int button) {
        if (selected){
            selected = false;
        }
    }

    @Override
    public void onMouseScrolled(double x, double y, double delta) {
    }

    @Override
    public void onMouseDragged(double x, double y, double dragX, double dragY, int button) {
        super.onMouseDragged(x, y, dragX, dragY, button);
        if (selected) {
            float beginX = getGlobalPosition().x;
            float endX = beginX+getSize().x;
            value=Math.max(Math.min(maxValue,MathUtils.arrange(beginX,endX,minValue,maxValue,(float)x)),minValue);
            onChangeValue(value);
        }
    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBeginTexture();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        poseStack.pushPose();
        AtlasTexture texture = getTheme().getAtlases(getClass()).get(UIProperty.DEFAULT);
        int xP = (int) ((getSize().x-BEGIN_PART_SIZE.x-END_PART_SIZE.x)/MIDDLE_PART_SIZE.x);
        texture.draw("bar_begin",poseStack,x,y);
        for (int i = 1;i<=xP;i++) {
            texture.drawXIndex("bar_middle",poseStack,i,x+2,y);
        }
        texture.draw("bar_end",poseStack,x+getSize().x-END_PART_SIZE.x,y);
        if (value > 0) {
            float f1 = MathUtils.arrange(minValue,maxValue,0,1,value);
            beginClip(new Area(new Vector2(x,y),new Vector2(getSize().x*f1,getSize().y)),2,0);
            RenderSystem.setShaderColor(progressColor.r,progressColor.g,progressColor.b,progressColor.a);
            for (int i = 0;i<=xP+1;i++){
                texture.drawXIndex(style.getId(),poseStack,i,x+2,y+2);
            }
            endClip();
        }
        poseStack.popPose();
        renderEnd();
    }

    public void onChangeValue(float value){
        onValueChanged.onHandle(value);
    }

    public Color getProgressColor() {
        return progressColor;
    }

    public ProgressBar setProgressColor(Color progressColor) {
        this.progressColor = progressColor;
        return this;
    }

    public float getValue() {
        return value;
    }

    public ProgressBar value(float value) {
        this.value = value;
        return this;
    }

    public ProgressBar maxValue(float maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public ProgressBar minValue(float minValue) {
        this.minValue = minValue;
        return this;
    }

    public ProgressBar range(float min,float max) {
        this.minValue = min;
        this.maxValue = max;
        return this;
    }

    public ProgressBar style(ProgressBarStyle style){
        this.style = style;
        return this;
    }

    public ProgressBar setOnValueChanged(NodeEvent.Function<Float> onValueChanged) {
        this.onValueChanged = onValueChanged;
        return this;
    }
}
