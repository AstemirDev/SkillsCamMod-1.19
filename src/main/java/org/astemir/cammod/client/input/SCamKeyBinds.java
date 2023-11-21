package org.astemir.cammod.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.client.screen.ui.screens.ScreenEnvironmentalSettings;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.camera.CameraController;
import org.astemir.cammod.scene.listener.ServerSceneListener;
import org.astemir.cammod.utils.ModUtils;

import javax.swing.*;


public class SCamKeyBinds {

    public static KeyMapping RECORD = new KeyMapping("Запись", InputConstants.Type.KEYSYM, InputConstants.KEY_UP,"SkillsCam");
    public static KeyMapping PLAY = new KeyMapping("Воспроизвести", InputConstants.Type.KEYSYM, InputConstants.KEY_RIGHT,"SkillsCam");
    public static KeyMapping PAUSE = new KeyMapping("Пауза", InputConstants.Type.KEYSYM, InputConstants.KEY_LEFT,"SkillsCam");
    public static KeyMapping MENU = new KeyMapping("Меню", InputConstants.Type.KEYSYM, InputConstants.KEY_M,"SkillsCam");
    public static KeyMapping ZOOM = new KeyMapping("Зум", InputConstants.Type.KEYSYM, InputConstants.KEY_Z,"SkillsCam");

    public static void onRegisterKeys(final RegisterKeyMappingsEvent e){
        zoomButton(e);
        recordButton(e);
        playButton(e);
        pauseButton(e);
        menuButton(e);
    }

    private static void zoomButton(RegisterKeyMappingsEvent e){
        KeyBinds.addBind(new KeyBind(ZOOM,0) {
            @Override
            void onPressed(Minecraft minecraft) {
                CameraController controller = CameraController.getInstance();
                controller.setZooming(true);
                controller.getZoom().setTo(1);
                Minecraft.getInstance().options.smoothCamera = true;
            }

            @Override
            void onReleased(Minecraft minecraft) {
                CameraController controller = CameraController.getInstance();
                controller.setZooming(false);
                controller.getZoom().setTo(0);
                controller.setZoomCoefficient(0.75f);
                Minecraft.getInstance().options.smoothCamera = false;
            }
        });
        e.register(ZOOM);
    }

    private static void pauseButton(RegisterKeyMappingsEvent e){
        KeyBinds.addBind(new KeyBind(PAUSE,5) {
            @Override
            void onPressed(Minecraft minecraft) {
                Player player = minecraft.player;
                ModUtils.executeOnServer(player, ServerSceneListener.EVENT_PAUSE_SCENE);
                if (MainScene.getInstance().isPaused()) {
                    ModUtils.sendMessage(player, "Пауза отключена");
                }else{
                    ModUtils.sendMessage(player, "Пауза включена");
                }
            }
        });
        e.register(PAUSE);
    }

    private static void menuButton(RegisterKeyMappingsEvent e){
        KeyBinds.addBind(new KeyBind(MENU,40) {
            @Override
            void onPressed(Minecraft minecraft) {
                minecraft.setScreen(new ScreenEnvironmentalSettings(Minecraft.getInstance().screen));
            }
        });
        e.register(MENU);
    }

    private static void recordButton(RegisterKeyMappingsEvent e){
        KeyBinds.addBind(new KeyBind(RECORD,10) {
            @Override
            void onPressed(Minecraft minecraft) {
                MainScene scene = MainScene.getInstance();
                Player player = minecraft.player;
                Vec3 pos = player.position();
                if (scene.isRecording()){
                    int recordLength = scene.getTicks();
                    ModUtils.executeOnServer(player, ServerSceneListener.EVENT_STOP_RECORDING, PacketArgument.integer(player.getId()), PacketArgument.vec3(pos),PacketArgument.integer(recordLength));
                    ModUtils.sendMessage(player,"Запись движений остановлена");
                    return;
                }
                if (!scene.isRecording()){
                    ModUtils.executeOnServer(player, ServerSceneListener.EVENT_START_RECORDING);
                    ModUtils.sendMessage(player,"Запись движений начата");
                    return;
                }
            }
        });
        e.register(RECORD);
    }

    private static void playButton(RegisterKeyMappingsEvent e){
        KeyBinds.addBind(new KeyBind(PLAY,5) {
            @Override
            void onPressed(Minecraft minecraft) {
                MainScene scene = MainScene.getInstance();
                Player player = minecraft.player;
                if (scene.isPlaying()){
                    scene.stop();
                    return;
                }
                if (!scene.isPlaying() && !scene.isRecording()){
                    ModUtils.executeOnServer(player, ServerSceneListener.EVENT_PLAY_SCENE);
                    return;
                }
            }
        });
        e.register(PLAY);
    }
}
