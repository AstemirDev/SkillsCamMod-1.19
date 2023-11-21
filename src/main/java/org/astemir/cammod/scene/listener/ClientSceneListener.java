package org.astemir.cammod.scene.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.client.animation.AnimationTrack;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.common.handler.ClientEventHandler;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.capability.CapabilityPlayerData;
import org.astemir.cammod.client.event.RenderEvents;
import org.astemir.cammod.client.renderer.clone.ModelClone;
import org.astemir.cammod.client.renderer.clone.WrapperClone;
import org.astemir.cammod.common.entity.EntityClone;

public class ClientSceneListener implements ClientEventHandler {

    public static final int EVENT_SYNC_PLAYER_DATA = 713914144;
    public static final int EVENT_SYNC_PLAYER_MORPH = 133954342;
    public static final int EVENT_MOTION = 833238211;
    public static final int EVENT_GET_ALL_ANIMATIONS = 1438228211;
    public static final int EVENT_GET_ALL_ANIMATIONS_FOR_ENTITY = 823482831;


    @Override
    public void onHandleEvent(ClientLevel level, BlockPos pos, int event, PacketArgument[] arguments) {
        if (event == EVENT_SYNC_PLAYER_DATA){
            Entity entity = level.getEntity(arguments[0].asInt());
            if (entity != null){
                CapabilityPlayerData clientData = CapabilityHandler.PLAYER_DATA.get(entity);
                clientData.deserializeNBT(arguments[1].asNBT());
            }
        }
        if (event == EVENT_SYNC_PLAYER_MORPH){
            Entity entity = level.getEntity(arguments[0].asInt());
            if (entity != null){
                RenderEvents.RENDERER.updateMorph((Player) entity);
            }
        }else
        if (event == EVENT_MOTION) {
            Entity entity = level.getEntity(arguments[0].asInt());
            if (entity != null) {
                Vec3 mot = arguments[1].asVec3();
                entity.setDeltaMovement(mot.x,mot.y,mot.z);
            }
        }else
        if (event == EVENT_GET_ALL_ANIMATIONS){
            int playerId = arguments[0].asInt();
            String modelId = arguments[1].asString();
            ModelClone model = WrapperClone.models.get(modelId);
            if (model != null) {
                CompoundTag nbt = new CompoundTag();
                ListTag listTag = new ListTag();
                for (AnimationTrack animation : model.animations) {
                    CompoundTag animTag = new CompoundTag();
                    animTag.putString("Name", animation.getName());
                    animTag.putFloat("Length", (float) animation.getLength());
                    animTag.putFloat("Speed", 1);
                    animTag.putFloat("Smoothness", 1);
                    listTag.add(animTag);
                }
                nbt.put("CustomAnimations", listTag);
                WorldEventHandler.playServerEvent(level, pos, ServerSceneListener.EVENT_REG_ALL_ANIMATIONS, PacketArgument.integer(playerId), PacketArgument.nbt(nbt));
            }
        }else
        if (event == EVENT_GET_ALL_ANIMATIONS_FOR_ENTITY){
            int entityId = arguments[0].asInt();
            String modelId = arguments[1].asString();
            ModelClone model = WrapperClone.models.get(modelId);
            if (model != null) {
                CompoundTag nbt = new CompoundTag();
                ListTag listTag = new ListTag();
                for (AnimationTrack animation : model.animations) {
                    CompoundTag animTag = new CompoundTag();
                    animTag.putString("Name", animation.getName());
                    animTag.putFloat("Length", (float) animation.getLength());
                    animTag.putFloat("Speed", 1);
                    animTag.putFloat("Smoothness", 1);
                    listTag.add(animTag);
                }
                nbt.put("CustomAnimations", listTag);
                Entity entity = level.getEntity(entityId);
                if (entity instanceof EntityClone clone){
                    clone.loadData(nbt);
                }
                WorldEventHandler.playServerEvent(level, pos, ServerSceneListener.EVENT_REG_ALL_ANIMATIONS_FOR_ENTITY, PacketArgument.integer(entityId), PacketArgument.nbt(nbt));
            }
        }
    }
}
