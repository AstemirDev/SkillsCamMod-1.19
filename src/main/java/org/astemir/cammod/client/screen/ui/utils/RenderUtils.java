package org.astemir.cammod.client.screen.ui.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.math.components.Color;
public class RenderUtils {



    public static void begin(ResourceLocation texture,float r,float g,float b,float a){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(r,g,b,a);
    }

    public static void begin(float r,float g,float b,float a){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(r,g,b,a);
    }

    public static void end(){
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
    }

    public static void drawString(PoseStack poseStack, Font font, String text, float x, float y, float scaleX,float scaleY,int pColor) {
        if (!text.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(x*(1-scaleX),y*(1-scaleY),0);
            poseStack.scale(scaleX,scaleY,1);
            font.drawShadow(poseStack, text, x, y, pColor);
            poseStack.popPose();
        }
    }

    public static void drawString(PoseStack poseStack, Font font, String text, float x, float y, float scaleX,float scaleY,Color color) {
        if (!text.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(x*(1-scaleX),y*(1-scaleY),0);
            poseStack.scale(scaleX,scaleY,1);
            drawString(poseStack, font, text, x, y, color.toRGB());
            poseStack.popPose();
        }
    }

    public static void drawStringCentered(PoseStack poseStack, Font font, String text, float x, float y, float scaleX,float scaleY,Color color) {
        if (!text.isEmpty()) {
            float width = font.width(text);
            float height = font.wordWrapHeight(text, (int) width);
            poseStack.pushPose();
            poseStack.translate(x*(1-scaleX),y*(1-scaleY),0);
            poseStack.scale(scaleX,scaleY,1);
            drawString(poseStack, font, text, x - width / 2, y - height / 2, color.toRGB());
            poseStack.popPose();
        }
    }

    public static void drawString(PoseStack poseStack, Font font, String text, float x, float y, int pColor) {
        if (!text.isEmpty()) {
            font.drawShadow(poseStack, text, x, y, pColor);
        }
    }

    public static void drawString(PoseStack poseStack, Font font, String text, float x, float y, Color color) {
        if (!text.isEmpty()) {
            drawString(poseStack, font, text, x, y, color.toRGB());
        }
    }

    public static void drawStringCentered(PoseStack poseStack, Font font, String text, float x, float y, Color color) {
        if (!text.isEmpty()) {
            float width = font.width(text);
            float height = font.wordWrapHeight(text, (int) width);
            drawString(poseStack, font, text, x - width / 2, y - height / 2, color.toRGB());
        }
    }

    public static void blit(PoseStack poseStack, float x, float y, float uOffset, float vOffset, float width, float height,float scaleX,float scaleY) {
        poseStack.pushPose();
        poseStack.translate(x*(1-scaleX),y*(1-scaleY),0);
        poseStack.scale(scaleX,scaleY,1);
        blit(poseStack, x, y, 0, uOffset, vOffset, width, height, 256, 256);
        poseStack.popPose();
    }


    public static void blit(PoseStack poseStack, float x, float y, float uOffset, float vOffset, float width, float height) {
        blit(poseStack, x, y, 0, uOffset, vOffset, width, height, 256, 256);
    }

    public static void blit(PoseStack poseStack, float x, float y, int pBlitOffset, float uOffset, float vOffset, float width, float height, int textureWidth, int textureHeight) {
        innerBlit(poseStack, x, x + width, y, y + height, pBlitOffset, width, height, uOffset, vOffset, textureWidth, textureHeight);
    }

    private static void innerBlit(PoseStack pPoseStack, float x1, float x2, float y1, float y2, int blitOffset, float width, float height, float uOffset, float vOffset, float textureWidth, float textureHeight) {
        innerBlit(pPoseStack.last().pose(), x1, x2, y1, y2, blitOffset, (uOffset + 0.0F) / (float)textureWidth, (uOffset + (float)width) / (float)textureWidth, (vOffset + 0.0F) / (float)textureHeight, (vOffset + (float)height) / (float)textureHeight);
    }

    private static void innerBlit(Matrix4f pMatrix, float x1, float x2, float y1, float y2, int blitOffset, float minU, float maxU, float minV, float maxV) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(pMatrix, x1, y2, (float)blitOffset).uv(minU, maxV).endVertex();
        bufferbuilder.vertex(pMatrix, x2, y2, (float)blitOffset).uv(maxU, maxV).endVertex();
        bufferbuilder.vertex(pMatrix, x2, y1, (float)blitOffset).uv(maxU, minV).endVertex();
        bufferbuilder.vertex(pMatrix, x1, y1, (float)blitOffset).uv(minU, minV).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }

    public static void fillColor(PoseStack pPoseStack, float minX, float minY, float maxX, float maxY, Color color,float alpha) {
        innerFill(pPoseStack.last().pose(), minX, minY, maxX, maxY, color,alpha);
    }

    private static void innerFill(Matrix4f pMatrix, float pMinX, float pMinY, float pMaxX, float pMaxY, Color color,float alpha) {
        if (pMinX < pMaxX) {
            float i = pMinX;
            pMinX = pMaxX;
            pMaxX = i;
        }
        if (pMinY < pMaxY) {
            float j = pMinY;
            pMinY = pMaxY;
            pMaxY = j;
        }
        int pColor = color.toRGB();
        float f3 = (float)(pColor >> 24 & 255) / 255.0F;
        float f = (float)(pColor >> 16 & 255) / 255.0F;
        float f1 = (float)(pColor >> 8 & 255) / 255.0F;
        float f2 = (float)(pColor & 255) / 255.0F;
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1,1,1,alpha);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferbuilder.vertex(pMatrix, pMinX, pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, pMaxX, pMaxY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, pMaxX, pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        bufferbuilder.vertex(pMatrix, pMinX, pMinY, 0.0F).color(f, f1, f2, f3).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }


}
