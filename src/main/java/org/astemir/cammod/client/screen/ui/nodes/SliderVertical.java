package org.astemir.cammod.client.screen.ui.nodes;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import org.astemir.api.math.MathUtils;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.event.NodeEvent;
import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.misc.UIProperty;

public class SliderVertical extends Node {

    private Vector2 sliderSize = new Vector2(12,15);
    private Vector2 barSize = new Vector2(14,14);

    private float value = 0;
    private boolean selected = false;

    public NodeEvent.Function<Float> onSliderDrag = NodeEvent.emptyFunction();



    public SliderVertical() {
        setSize(new Vector2(128,32));
    }

    @Override
    public Node setSize(Vector2 size) {
        int xP = (int) (size.x/barSize.x);
        int yP = (int) (size.y/barSize.y);
        return super.setSize(new Vector2(xP*barSize.x,yP*barSize.y));
    }

    @Override
    public void onMouseClicked(double x, double y, int button) {
        selected = getArea().contains((float)x,(float)y);
        if (selected){
            Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK,1,1);
            if (getArea().contains((int)x,(int)y)) {
                float beginY = getGlobalPosition().y;
                float endY = beginY+getSize().y;
                value = MathUtils.arrange(beginY,endY,0,1,(float)y);
                onSliderDrag.onHandle(value);
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
        super.onMouseScrolled(x, y, delta);
        float f = (float)(delta/sliderSize.y);
        value=MathUtils.lerp(value,Math.max(Math.min(1,value+f),0),Minecraft.getInstance().getPartialTick());
        onSliderDrag.onHandle(value);
    }

    @Override
    public void onMouseDragged(double x, double y, double dragX, double dragY, int button) {
        super.onMouseDragged(x, y, dragX, dragY, button);
        if (selected) {
            float beginY = getGlobalPosition().y;
            float endY = beginY+getSize().y;
            value=Math.max(Math.min(1,MathUtils.arrange(beginY,endY,0,1,(float)y)),0);
            onSliderDrag.onHandle(value);
        }
    }


    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBeginTexture();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        int yP = (int) (getSize().y/barSize.y);
        AtlasTexture texture = getAtlas(UIProperty.isSelected(getArea().contains((float)mouseX,(float)mouseY)));
        poseStack.pushPose();
        for (int i = 0;i<=yP-1;i++){
            if (i == 0){
                texture.drawYIndex("bar_top",poseStack,i,x,y);
            }else
            if (i == yP-1){
                texture.drawYIndex("bar_bottom",poseStack,i,x,y);
            }else{
                texture.drawYIndex("bar_middle",poseStack,i,x,y);
            }
        }
        float sliderY = (y+(value*getSize().y-sliderSize.y/2));
        if (sliderY < y+1){
            sliderY = y+1;
        }
        if (sliderY > y+getSize().y-sliderSize.y-1){
            sliderY = y+getSize().y-sliderSize.y-1;
        }
        texture.draw("slider",poseStack, x+1, sliderY);
        poseStack.popPose();
        renderEnd();
    }


    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Vector2 getSliderSize() {
        return sliderSize;
    }

    public void setSliderSize(Vector2 sliderSize) {
        this.sliderSize = sliderSize;
    }

    public Vector2 getBarSize() {
        return barSize;
    }

    public void setBarSize(Vector2 barSize) {
        this.barSize = barSize;
    }
}
