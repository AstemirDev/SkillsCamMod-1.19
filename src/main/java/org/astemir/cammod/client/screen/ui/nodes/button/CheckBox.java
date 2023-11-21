package org.astemir.cammod.client.screen.ui.nodes.button;

import com.mojang.blaze3d.vertex.PoseStack;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.misc.Area;
import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.misc.UIProperty;
import org.astemir.cammod.client.screen.ui.nodes.Node;

public class CheckBox extends Node implements IButton {

    private boolean enabled = false;

    public CheckBox() {
        setSize(new Vector2(16,16));
    }


    @Override
    public void onMouseClicked(double x, double y, int button) {
        iPress(x,y,button);
    }

    @Override
    public void onMouseReleased(double x, double y, int button) {
        iRelease(x,y,button);
    }


    @Override
    public void onButtonPressed(int button) {
        setEnabled(!isEnabled());
    }

    @Override
    public void onButtonReleased(int button) {
    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBeginTexture();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        poseStack.pushPose();
        poseStack.translate(getGlobalPosition().x*(1-getScale().x),getGlobalPosition().y*(1-getScale().y),0);
        poseStack.scale(getScale().x,getScale().y,1);
        AtlasTexture texture = getAtlas(UIProperty.isSelected(getArea().contains((float)mouseX,(float)mouseY)));
        texture.draw(isEnabled() ? "enabled" : "disabled",poseStack,x,y);
        poseStack.popPose();
        renderEnd();
    }

    @Override
    public Node setSize(Vector2 size) {
        return super.setSize(new Vector2(16,16));
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Area getClickableArea() {
        return getArea();
    }

    public boolean isEnabled() {
        return enabled;
    }
}
