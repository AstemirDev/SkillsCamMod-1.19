package org.astemir.cammod.client.screen.ui.nodes;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.utils.FontUtils;
import org.astemir.cammod.client.screen.ui.utils.RenderUtils;

public class Label extends Node{


    private String text = "";
    private Font font;
    private boolean centered = true;

    public Label(String text) {
        this.text = text;
        font = Minecraft.getInstance().font;
        setSize(new Vector2(FontUtils.width(font,text),FontUtils.height(font,text)));
    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBegin();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        poseStack.pushPose();
        if (!text.isEmpty()) {
            if (isCentered()) {
                RenderUtils.drawStringCentered(poseStack, Minecraft.getInstance().font, text, x,y, Color.WHITE);
            }else{
                RenderUtils.drawString(poseStack, Minecraft.getInstance().font, text, x, y, Color.WHITE);
            }
        }
        poseStack.popPose();
        renderEnd();
    }

    @Override
    public Node setSize(Vector2 size) {
        return super.setSize(new Vector2(FontUtils.width(font,text),FontUtils.height(font,text)));
    }

    public void updateSize(){
        setSize(new Vector2(FontUtils.width(font,text),FontUtils.height(font,text)));
    }

    public String getText() {
        return text;
    }

    public Label text(String text) {
        this.text = text;
        updateSize();
        return this;
    }

    public boolean isCentered() {
        return centered;
    }

    public Label setCentered(boolean centered) {
        this.centered = centered;
        return this;
    }
}
