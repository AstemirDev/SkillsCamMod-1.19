package org.astemir.cammod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;

public class ClientUtils {

    public static void drawSprite(PoseStack stack, ResourceLocation texture, int x, int y, int width, int height, float r, float g, float b, float a){
        int x1 = x, y1 = y, x2 = width, y2 =height;
        stack.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(r,g,b,a);
        RenderSystem.setShaderTexture(0, texture);
        Gui.blit(stack, x1, y1, 0, 0, x2, y2, x2, y2);
        RenderSystem.disableBlend();
        RenderSystem.disableTexture();
        stack.popPose();
    }
}
