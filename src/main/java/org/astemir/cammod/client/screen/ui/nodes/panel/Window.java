package org.astemir.cammod.client.screen.ui.nodes.panel;

import com.mojang.blaze3d.vertex.PoseStack;

import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.misc.UIProperty;
import org.astemir.cammod.client.screen.ui.nodes.ColorRect;
import org.astemir.cammod.client.screen.ui.nodes.Node;
import org.astemir.cammod.client.screen.ui.nodes.button.PanelButton;
import org.astemir.cammod.client.screen.ui.nodes.button.PanelButtonType;

public class Window extends Node {

    private Vector2 partSize = new Vector2(16,16);

    private ColorRect colorRect = new ColorRect(new Color(0.25f,0.25f,0.25f,1));

    private boolean showBorders = true;

    private PanelButton closeButton = new PanelButton(this).type(PanelButtonType.CLOSE);
    private PanelButton fullscreenButton = new PanelButton(this).type(PanelButtonType.FULLSCREEN);
    private PanelButton collapseButton = new PanelButton(this).type(PanelButtonType.COLLAPSE);


    public Window() {
        setSize(new Vector2(32,32));
        setPartSize(new Vector2(16,16));
    }

    @Override
    public void init() {
        colorRect.setPosition(new Vector2(partSize.x/2,partSize.y/2));
        colorRect.setSize(new Vector2((getSize().x-partSize.x),(getSize().y-partSize.y)));
        colorRect.setLayer(-1);
        closeButton.setPosition(new Vector2(8,-3));
        fullscreenButton.setPosition(new Vector2(17,-3));
        collapseButton.setPosition(new Vector2(26,-3));
        addChild(closeButton);
        addChild(fullscreenButton);
        addChild(collapseButton);
        addChild(colorRect);
    }

    @Override
    public Node setSize(Vector2 size) {
        int xP = (int) (size.x/partSize.x);
        int yP = (int) (size.y/partSize.y);
        return super.setSize(new Vector2(xP*partSize.x,yP*partSize.y));
    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (isShowBorders()) {
            renderBeginTexture();
            float x = getGlobalPosition().x;
            float y =  getGlobalPosition().y;
            int xP = (int) (getSize().x / partSize.x);
            int yP = (int) (getSize().y / partSize.y);
            AtlasTexture texture = getAtlas(UIProperty.DEFAULT);
            poseStack.pushPose();
            for (int i = 0; i <= xP; i++) {
                for (int j = 0; j <= yP; j++) {
                    if (i == 0 && j == 0) {
                        texture.drawXYIndex("top_0",poseStack,i,j,x,y, getScale().x, getScale().y);
                    }else
                    if (i == 0 && j > 0 && j < yP - 1) {
                        texture.drawXYIndex("middle_0",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }else
                    if (i == 0 && j == yP - 1) {
                        texture.drawXYIndex("bottom_0",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }else
                    if (i == xP - 1 && j == 0) {
                        texture.drawXYIndex("top_2",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }else
                    if (i == xP - 1 && j > 0 && j < yP - 1) {
                        texture.drawXYIndex("middle_2",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }else
                    if (i == xP - 1 && j == yP - 1) {
                        texture.drawXYIndex("bottom_2",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }else
                    if (i > 0 && i < xP - 1 && j > 0 && j < yP - 1) {
                        texture.drawXYIndex("middle_1",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }else
                    if (i > 0 && i < xP - 1 && j == 0) {
                        texture.drawXYIndex("top_1",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }else
                    if (i > 0 && i < xP - 1 && j == yP - 1) {
                        texture.drawXYIndex("bottom_1",poseStack,i,j,x,y,getScale().x, getScale().y);
                    }
                }
            }
            poseStack.popPose();
            renderEnd();
        }
    }

    public Vector2 getRenderAreaOffset(){
        return getPartSize().div(2);
    }

    public ColorRect getColorRect() {
        return colorRect;
    }

    public boolean isShowBorders() {
        return showBorders;
    }

    public void setShowBorders(boolean showBorders) {
        this.showBorders = showBorders;
    }

    public Vector2 getPartSize() {
        return partSize;
    }

    public void setPartSize(Vector2 partSize) {
        this.partSize = partSize;
    }
}
