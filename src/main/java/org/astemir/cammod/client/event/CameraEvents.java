package org.astemir.cammod.client.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.api.client.event.CameraAdvancedSetupEvent;
import org.astemir.api.client.event.CameraPreMatrixResetEvent;
import org.astemir.cammod.client.ClientProperties;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.camera.CameraController;

public class CameraEvents {

    @SubscribeEvent
    public static void onFovModifierCompute(ComputeFovModifierEvent e) {
        CameraController controller = CameraController.getInstance();
        if (controller.isZooming()) {
            float zoomValue = controller.getZoom().value(Minecraft.getInstance().getDeltaFrameTime());
            e.setNewFovModifier(e.getFovModifier() * (1 - zoomValue * controller.getZoomCoefficient()));
        }
    }


    @SubscribeEvent
    public static void onGlobalCameraSetup(CameraAdvancedSetupEvent e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (MainScene.INSTANCE.getProperties().unlockedCamera){
            if (e.isDetached()) {
                if (!properties.customCameraRotation) {
                    e.setRotation(0, 0);
                }
            }
        }
        if (properties.customCameraRotation){
            e.setRotation(properties.cameraRotation.x,properties.cameraRotation.y);
        }
        if (properties.customCameraPosition){
            e.setPosition(properties.cameraPosition.x,properties.cameraPosition.y,properties.cameraPosition.z);
        }
        if (properties.customCameraOffset){
            e.offset(properties.cameraOffset.x,properties.cameraOffset.y,properties.cameraOffset.z);
        }
        CameraController.getInstance().tick(e);
    }

    @SubscribeEvent
    public static void onCameraPreSetup(CameraPreMatrixResetEvent e){
        CameraController.getInstance().preSetup(e);
    }
}
