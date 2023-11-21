package org.astemir.cammod.client.screen.ui.nodes.button;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import org.astemir.api.math.components.Vector2;
import org.astemir.cammod.client.screen.ui.misc.Area;
import org.astemir.cammod.client.screen.ui.misc.AtlasTexture;
import org.astemir.cammod.client.screen.ui.misc.UIProperty;
import org.astemir.cammod.client.screen.ui.nodes.Node;
import org.astemir.cammod.client.screen.ui.nodes.panel.Window;

public class PanelButton extends Node implements IButton {

    private PanelButtonType type = PanelButtonType.CLOSE;
    private Window panel;

    public PanelButton(Window panel) {
        setSize(new Vector2(9,9));
        this.panel = panel;
    }

    @Override
    public Area getClickableArea() {
        return getArea();
    }

    @Override
    public void onButtonPressed(int button) {
        Minecraft.getInstance().player.playSound(SoundEvents.UI_BUTTON_CLICK,1,1);
    }

    @Override
    public void onButtonReleased(int button) {

    }

    @Override
    public void onRender(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBeginTexture();
        float x = getGlobalPosition().x;
        float y = getGlobalPosition().y;
        AtlasTexture texture = getTheme().getAtlases(getClass()).get(UIProperty.isSelected(getArea().contains((float) mouseX, (float) mouseY)));
        poseStack.pushPose();
        poseStack.translate(getGlobalPosition().x * (1 - getScale().x), getGlobalPosition().y * (1 - getScale().y), 0);
        poseStack.scale(getScale().x, getScale().y, 1);
        texture.draw(type.name().toLowerCase() + "_button", poseStack, x, y);
        poseStack.popPose();
        renderEnd();
    }

    public PanelButton type(PanelButtonType type) {
        this.type = type;
        return this;
    }
}
