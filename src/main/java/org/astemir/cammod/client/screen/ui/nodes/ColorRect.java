package org.astemir.cammod.client.screen.ui.nodes;

import com.mojang.blaze3d.vertex.*;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.utils.RenderUtils;

public class ColorRect extends Node{

    private Color fillColor = Color.RED;

    public ColorRect(Color color) {
        this.fillColor = color;
        setSize(new Vector2(32,32));
    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBegin();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        poseStack.pushPose();
        RenderUtils.fillColor(poseStack,x,y,x+getSize().x,y+getSize().y,fillColor,1);
        poseStack.popPose();
        renderEnd();
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
}
