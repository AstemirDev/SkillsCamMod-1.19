package org.astemir.cammod.scene.listener;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import org.astemir.api.common.handler.ServerEventHandler;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.capability.CapabilityPlayerData;
import org.astemir.cammod.common.entity.EntityClone;
import org.astemir.cammod.common.event.PlayerKeyInputEvent;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.action.*;
import org.astemir.cammod.utils.MorphUtils;

public class ServerSceneListener implements ServerEventHandler {

    public static final int EVENT_STOP_RECORDING = 131314444;
    public static final int EVENT_PLAY_SCENE = 414918141;
    public static final int EVENT_PAUSE_SCENE = 911494242;
    public static final int EVENT_START_RECORDING = 87632389;
    public static final int EVENT_KEY= 1576832342;
    public static final int EVENT_ANIMATION_END = 123434448;
    public static final int EVENT_SPRINT = 843112118;
    public static final int EVENT_REG_ALL_ANIMATIONS = 828822811;
    public static final int EVENT_REG_ALL_ANIMATIONS_FOR_ENTITY = 844288213;

    @Override
    public void onHandleEvent(ServerLevel level, BlockPos pos, int event, PacketArgument[] arguments) {
        MainScene scene = MainScene.getInstance();
        if (event == EVENT_STOP_RECORDING){
            int entityId = arguments[0].asInt();
            Vec3 position = arguments[1].asVec3();
            int ticks = arguments[2].asInt();
            Entity entity = level.getEntity(entityId);
            if (entity != null) {
                scene.stopRecording((Player) entity, level, position, ticks);
            }
        }else
        if (event == EVENT_PLAY_SCENE){
            scene.play(level);
        }else
        if (event == EVENT_START_RECORDING){
            scene.record(level);
        }else
        if (event == EVENT_PAUSE_SCENE){
            scene.setPaused(!scene.isPaused());
        }else
        if (event == EVENT_KEY){
            int entityId = arguments[0].asInt();
            int key = arguments[1].asInt();
            int action = arguments[2].asInt();
            Entity entity = level.getEntity(entityId);
            if (entity != null) {
                if (entity instanceof Player player) {
                    MinecraftForge.EVENT_BUS.post(new PlayerKeyInputEvent(player,key,action));
                }
            }
        }else
        if (event == EVENT_ANIMATION_END){
            for (BindAction action : ActionController.getInstance().getActions()) {
                if (action instanceof BindAnimation animationAction){
                    int entityId = arguments[0].asInt();
                    String animation = arguments[1].asString();
                    Entity entity = level.getEntity(entityId);
                    if (entity != null) {
                        if (entity instanceof Player player) {
                            if (animationAction.getType() == BindType.ANIMATION_END) {
                                if (animationAction.getAnimationName().equals(animation)){
                                    animationAction.run(player);
                                }
                            }
                        }
                    }
                }
            }
        }else
        if (event == EVENT_SPRINT){
            for (BindAction action : ActionController.getInstance().getActions()) {
                if (action.getType() == BindType.SPRINT){
                    int entityId = arguments[0].asInt();
                    Entity entity = level.getEntity(entityId);
                    if (entity != null) {
                        if (entity instanceof Player player) {
                            action.run(player);
                        }
                    }
                }
            }
        }else
        if (event == EVENT_REG_ALL_ANIMATIONS){
            int playerId = arguments[0].asInt();
            CompoundTag tag = arguments[1].asNBT();
            Entity playerEntity = level.getEntity(playerId);
            if (playerEntity != null){
                Player player = (Player) playerEntity;
                MorphUtils.addCustomAnimations(player,tag);
            }
        }else
        if (event == EVENT_REG_ALL_ANIMATIONS_FOR_ENTITY){
            int entityId = arguments[0].asInt();
            CompoundTag tag = arguments[1].asNBT();
            Entity cloneEntity = level.getEntity(entityId);
            if (cloneEntity != null){
                EntityClone clone = (EntityClone) cloneEntity;
                clone.loadData(tag);
            }
        }
    }

}
