package org.astemir.cammod.client.screen.ui.nodes.button;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.event.NodeEvent;
import org.astemir.cammod.client.screen.ui.misc.Area;
import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.misc.UIProperty;
import org.astemir.cammod.client.screen.ui.nodes.Node;
import org.astemir.cammod.client.screen.ui.utils.RenderUtils;

public class Button extends Node implements IButton {

    private Vector2 partSize = new Vector2(11,11);
    private String text = "";
    private ButtonStyle style = ButtonStyle.DEFAULT;
    public NodeEvent.Run onPressed = NodeEvent.emptyRun();

    public Button() {
        setSize(new Vector2(110,110));
        setPartSize(new Vector2(11,11));
    }

    @Override
    public Node setSize(Vector2 size) {
        int xP = (int) (size.x/partSize.x);
        int yP = (int) (size.y/partSize.y);
        return super.setSize(new Vector2(xP*partSize.x,yP*partSize.y));
    }

    @Override
    public void onMouseClicked(double x, double y, int button) {
        super.onMouseClicked(x,y,button);
        if (getArea().contains((float)x,(float)y)){
            onButtonPressed(button);
        }
    }

    @Override
    public void onMouseReleased(double x, double y, int button) {
        super.onMouseReleased(x, y, button);
        if (getArea().contains((float)x,(float)y)) {
            onButtonReleased(button);
        }
    }

    @Override
    public Area getClickableArea() {
        return getArea();
    }

    @Override
    public void onButtonPressed(int button){
        Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK,1,1);
        onPressed.onHandle();
    }

    @Override
    public void onButtonReleased(int button){
    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBeginTexture();
        poseStack.pushPose();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        int xP = (int) (getSize().x/partSize.x);
        int yP = (int) (getSize().y/partSize.y);
        AtlasTexture texture = getTheme().getAtlases(getClass(),style.getId()).get(UIProperty.isSelected(getArea().contains((float)mouseX,(float)mouseY)));
        for (int i = 0;i<=xP;i++){
            for (int j = 0;j<=yP;j++){
                float partX = x+(i*partSize.x);
                float partY = y+(j*partSize.y);
                if (i == 0 && j == 0){
                    texture.draw("top_0",poseStack,partX,partY, getScale().x, getScale().y);
                }else
                if (i == 0 && j > 0 && j < yP-1){
                    texture.draw("middle_0",poseStack,partX,partY,getScale().x, getScale().y);
                }else
                if (i == 0 && j == yP-1){
                    texture.draw("bottom_0",poseStack,partX,partY,getScale().x, getScale().y);
                }else
                if (i == xP-1 && j == 0){
                    texture.draw("top_2",poseStack,partX,partY,getScale().x, getScale().y);
                }else
                if (i == xP-1 && j > 0 && j < yP-1){
                    texture.draw("middle_2",poseStack,partX,partY,getScale().x, getScale().y);
                }else
                if (i == xP-1 && j == yP-1){
                    texture.draw("bottom_2",poseStack,partX,partY,getScale().x, getScale().y);
                }else
                if (i > 0 && i < xP-1 && j > 0 && j < yP-1){
                    texture.draw("middle_1",poseStack,partX,partY,getScale().x, getScale().y);
                }else
                if (i > 0 && i < xP-1 && j == 0){
                    texture.draw("top_1",poseStack,partX,partY,getScale().x, getScale().y);
                }else
                if (i > 0 && i < xP-1 && j == yP-1){
                    texture.draw("bottom_1",poseStack,partX,partY,getScale().x, getScale().y);
                }
            }
        }
        if (!text.isEmpty()) {
            Vector2 textPosition = getGlobalPosition().add(new Vector2(getSize().x / 2, getSize().y / 2));
            RenderUtils.drawStringCentered(poseStack, Minecraft.getInstance().font, text, textPosition.x, textPosition.y, Color.WHITE);
        }
        poseStack.popPose();
        renderEnd();
    }


    public Button setOnPressed(NodeEvent.Run onPressed) {
        this.onPressed = onPressed;
        return this;
    }

    public Button text(String text) {
        this.text = text;
        return this;
    }

    public Button style(ButtonStyle style){
        this.style = style;
        return this;
    }

    public Vector2 getPartSize() {
        return partSize;
    }

    public void setPartSize(Vector2 partSize) {
        this.partSize = partSize;
    }

    public String getText() {
        return text;
    }
}
