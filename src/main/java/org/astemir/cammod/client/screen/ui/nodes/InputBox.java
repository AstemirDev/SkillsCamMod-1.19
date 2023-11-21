package org.astemir.cammod.client.screen.ui.nodes;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import org.astemir.api.math.components.Color;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.utils.FontUtils;
import org.astemir.cammod.client.screen.ui.utils.RenderUtils;

public class InputBox extends Node { ;


    private Font font;
    private String text = "";
    private boolean selected = false;
    private int currentIndex = 0;
    private int textOffset = 0;
    private int selectionIndex = -1;
    private Vector2 partSize = new Vector2(12,12);

    public InputBox(String text) {
        this.text = text;
        font = Minecraft.getInstance().font;
        setSize(new Vector2(128,24));
    }


    @Override
    public void onMouseClicked(double x, double y, int button) {
        super.onMouseClicked(x, y, button);
        if (getArea().contains((float)x,(float)y)){
            Vector2 pos = getGlobalPosition();
            currentIndex = font.plainSubstrByWidth(text, (int) (x-pos.x)).length();
            if (currentIndex < 0){
                currentIndex = 0;
            }
            if (currentIndex > text.length()){
                currentIndex = text.length();
            }
            selected = true;
        }else{
            selected = false;
        }
    }

    @Override
    public void onKeyPressed(int keyCode, int scanCode, int modifiers) {
        super.onKeyPressed(keyCode, scanCode, modifiers);
        if (Screen.isCopy(keyCode)){
            Minecraft.getInstance().keyboardHandler.setClipboard(text);
        }
        if (Screen.isPaste(keyCode)){
            text = Minecraft.getInstance().keyboardHandler.getClipboard();
            onTextContentChanged(text);
        }
        if (keyCode == InputConstants.KEY_BACKSPACE){
            if (currentIndex > 0){
                if (text.length() <= currentIndex) {
                    text = text.substring(0,currentIndex-1);
                    onTextContentChanged(text);
                    currentIndex = text.length();
                }else{
                    text = text.substring(0, currentIndex) + text.substring(currentIndex+1);
                    onTextContentChanged(text);
                }
            }
        }
        if (keyCode == InputConstants.KEY_LEFT){
            if (currentIndex > 0) {
                currentIndex--;
            }
        }
        if (keyCode == InputConstants.KEY_RIGHT){
            if (currentIndex < text.length()) {
                currentIndex++;
            }
        }

    }

    @Override
    public void onKeyReleased(int keyCode, int scanCode, int modifiers) {
        super.onKeyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public void onCharTyped(char codePoint, int modifiers) {
        super.onCharTyped(codePoint, modifiers);
        if (text.length() <= currentIndex) {
            text += codePoint;
            onTextContentChanged(text);
            currentIndex = text.length();
        }else{
            text = text.substring(0,currentIndex)+codePoint+text.substring(currentIndex);
            onTextContentChanged(text);
        }
    }

    public void renderText(PoseStack poseStack,float x,float y){
        String fitText = font.plainSubstrByWidth(text, (int) getSize().x-8);
        int unfitCharsCount = text.length()-fitText.length();
        String clippedText = text.substring(unfitCharsCount);
        if (!text.isEmpty()) {
            RenderUtils.drawString(poseStack, Minecraft.getInstance().font, clippedText, x+4, y+partSize.y-2, Color.WHITE);
        }
        String cursor = "";
        if (getScreen().getTicks() % 40 > 20) {
            cursor = "_";
        }else{
            cursor = "";
        }
        if (currentIndex <= clippedText.length()) {
            int i = currentIndex > 0 ? currentIndex-1 : 0;
            float cursorX = FontUtils.width(font, clippedText.substring(0, i));
            RenderUtils.drawString(poseStack, Minecraft.getInstance().font, cursor, (x + 4) + cursorX, y + partSize.y, Color.WHITE);
        }else{
            if (clippedText.length()-unfitCharsCount > 0 && clippedText.length()-unfitCharsCount <=clippedText.length()) {
                float cursorX = FontUtils.width(font, clippedText.substring(0, clippedText.length() - unfitCharsCount));
                RenderUtils.drawString(poseStack, Minecraft.getInstance().font, cursor, (x + 4) + cursorX, y + partSize.y, Color.WHITE);
            }
        }
    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBeginTexture();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        int xP = (int) (getSize().x/partSize.x);
        renderText(poseStack,x,y);
        poseStack.pushPose();
        AtlasTexture texture = getAtlas();
        for (int i = 0;i<=xP;i++){
            for (int j = 0;j<=1;j++){
                if (i == 0 && j == 0) {
                    texture.draw("top_0", poseStack, x + (i * partSize.x), y + (j * partSize.y));
                }
                if (i > 0 && i < xP-1 && j == 0){
                    texture.draw("top_1", poseStack, x + (i * partSize.x), y + (j * partSize.y));
                }
                if (i == xP-1 && j == 0){
                    texture.draw("top_2", poseStack, x + (i * partSize.x), y + (j * partSize.y));
                }
                if (i == 0 && j == 1) {
                    texture.draw("bottom_0", poseStack, x + (i * partSize.x), y + (j * partSize.y));
                }
                if (i > 0 && i < xP-1 && j == 1){
                    texture.draw("bottom_1", poseStack, x + (i * partSize.x), y + (j * partSize.y));
                }
                if (i == xP-1 && j == 1){
                    texture.draw("bottom_2", poseStack, x + (i * partSize.x), y + (j * partSize.y));
                }
            }
        }
        poseStack.popPose();
        renderEnd();
    }


    @Override
    public Node setSize(Vector2 size) {
        Vector2 partSize = new Vector2(12,12);
        int xP = (int) (size.x/partSize.x);
        return super.setSize(new Vector2(xP*partSize.x,partSize.y*2));
    }


    public void onTextContentChanged(String text){};

    public void updateSize(){
        setSize(new Vector2(FontUtils.width(font,text),FontUtils.height(font,text)));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
