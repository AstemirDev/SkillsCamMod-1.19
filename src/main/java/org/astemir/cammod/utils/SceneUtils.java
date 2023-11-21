package org.astemir.cammod.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.cammod.client.event.RenderEvents;
import org.astemir.cammod.scene.MainScene;

public class SceneUtils {


    public static void playOrStop(Level level){
        MainScene scene = MainScene.getInstance();
        if (scene.isPlaying()){
            scene.stop();
        }else{
            if (!scene.isRecording()) {
                scene.play(level);
            }
        }
    }

    public static boolean startRecording(Level level){
        MainScene scene = MainScene.getInstance();
        if (!scene.isRecording()){
            scene.record(level);
            return true;
        }else{
            return false;
        }
    }

    public static boolean stopRecording(Player player,Level level, Vec3 position){
        MainScene scene = MainScene.getInstance();
        if (scene.isRecording()){
            scene.stopRecording(player,level,position);
            return true;
        }else{
            return false;
        }
    }

    public static boolean pauseOrResume(){
        MainScene scene = MainScene.getInstance();
        scene.setPaused(!scene.isPaused());
        return scene.isPaused();
    }

    public static boolean enableNameTagsVisibility(){
        if (!RenderEvents.SHOW_NAMETAGS){
            RenderEvents.SHOW_NAMETAGS = true;
            return true;
        }else{
            RenderEvents.SHOW_NAMETAGS = false;
            return false;
        }
    }

}
