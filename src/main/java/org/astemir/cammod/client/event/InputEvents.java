package org.astemir.cammod.client.event;


import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.math.NumberUtils;
import org.astemir.api.math.MathUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.client.input.KeyBind;
import org.astemir.cammod.client.input.KeyBinds;
import org.astemir.cammod.client.input.SCamKeyBinds;
import org.astemir.cammod.scene.camera.CameraController;
import org.astemir.cammod.scene.listener.ServerSceneListener;
import org.astemir.cammod.utils.ModUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowContentScaleCallback;

public class InputEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key e){
        input(e.getKey(),e.getAction());
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent e){
        float partial = Minecraft.getInstance().getDeltaFrameTime();
        CameraController controller = CameraController.getInstance();
        if (controller.isZooming()) {
            float delta = MathUtils.clamp((float) e.getScrollDelta(), -1, 1);
            float zoom = controller.getZoomCoefficient();
            zoom = MathUtils.clamp(MathUtils.lerp(zoom, zoom + delta/17f, partial), 0.75f, 1f);
            controller.setZoomCoefficient(zoom);
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton e){
        input(toKey(e.getButton()),e.getAction());
    }

    private static int toKey(int key){
        switch (key){
            case GLFW.GLFW_MOUSE_BUTTON_LEFT:{
                return InputConstants.MOUSE_BUTTON_LEFT;
            }
            case GLFW.GLFW_MOUSE_BUTTON_RIGHT:{
                return InputConstants.MOUSE_BUTTON_RIGHT;
            }
            case GLFW.GLFW_MOUSE_BUTTON_MIDDLE:{
                return InputConstants.MOUSE_BUTTON_MIDDLE;
            }
        }
        return -1;
    }

    private static void input(int key,int action){
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            int id = Minecraft.getInstance().player.getId();
            ModUtils.executeOnServer(Minecraft.getInstance().player, ServerSceneListener.EVENT_KEY, PacketArgument.integer(id),PacketArgument.integer(key),PacketArgument.integer(action));
        }
    }
}
