package org.astemir.cammod.utils;

import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.common.animation.Animation;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.capability.CapabilityPlayerData;
import org.astemir.cammod.scene.listener.ClientSceneListener;


public class MorphUtils {

    public static void morphTo(Player player,ResourceLocation type, CompoundTag nbt){
        CompoundTag compoundtag = nbt.copy();
        compoundtag.putString("id", type.toString());
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.setMorphNbt(compoundtag);
            playerData.setMorphed(true);
            playerData.syncToClient(player);
            playerData.setRotation(new Vector3(0,0,0));
            playerData.setScale(new Vector3(1,1,1));
            playerData.setPosition(new Vector3(0,0,0));
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void unmorph(Player player){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.setAnimations(new ConcurrentSet<>());
            playerData.setMorphed(false);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void reset(Player player){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.setAnimations(new ConcurrentSet<>());
            playerData.setMorphed(false);
            playerData.setRotation(new Vector3(0,0,0));
            playerData.setScale(new Vector3(1,1,1));
            playerData.setPosition(new Vector3(0,0,0));
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void playAnimation(Player player,String animation){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.addAnimation(animation);
            playerData.syncToClient(player);
        }
    }

    public static void addCustomAnimation(Player player,String animation,float length){
        addCustomAnimation(player,animation,length,1.0f,2.0f);
    }

    public static void addAllAnimations(Player player,String modelName){
        WorldEventHandler.playClientEvent(player.level,player.blockPosition(),ClientSceneListener.EVENT_GET_ALL_ANIMATIONS,PacketArgument.integer(player.getId()),PacketArgument.str(modelName));
    }

    public static void clearRegisteredAnimations(Player player){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            CompoundTag nbt = playerData.getMorphNbt();
            nbt.put("CustomAnimations", new ListTag());
            playerData.setMorphNbt(nbt);
            playerData.syncToClient(player);
        }
    }

    public static void addCustomAnimations(Player player,CompoundTag animationList){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            CompoundTag nbt = playerData.getMorphNbt();
            nbt.put("CustomAnimations", animationList.getList("CustomAnimations",10));
            playerData.setMorphNbt(nbt);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void addCustomAnimation(Player player,String animation,float length,float speed,float smoothness){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            CompoundTag nbt = playerData.getMorphNbt();
            ListTag list = new ListTag();
            if (nbt.contains("CustomAnimations")) {
                list = nbt.getList("CustomAnimations", Tag.TAG_COMPOUND);
            }
            CompoundTag animTag = new CompoundTag();
            animTag.putString("Name", animation);
            animTag.putFloat("Length", length);
            animTag.putFloat("Speed", speed);
            animTag.putFloat("Smoothness", smoothness);
            list.add(animTag);
            nbt.put("CustomAnimations", list);
            playerData.setMorphNbt(nbt);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }


    public static void setProvoke(Player player,boolean value){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.setProvoke(value);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void setProvokeId(Player player,ResourceLocation provokeId){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.setProvokeId(provokeId.toString());
            playerData.setProvoke(true);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void setRenderPosition(Player player, Vector3 position){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            CompoundTag nbt = playerData.getMorphNbt();
            nbt.put("Position",position.toNbt());
            playerData.setPosition(position);
            playerData.setMorphNbt(nbt);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void setRenderRotation(Player player, Vector3 rotation){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            CompoundTag nbt = playerData.getMorphNbt();
            nbt.put("Rotation",rotation.toNbt());
            playerData.setRotation(rotation);
            playerData.setMorphNbt(nbt);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }

    public static void setRenderScale(Player player, Vector3 scale){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            CompoundTag nbt = playerData.getMorphNbt();
            nbt.put("Scale",scale.toNbt());
            playerData.setScale(scale);
            playerData.setMorphNbt(nbt);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }


    public static void updatePlayerMorph(Player player){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            CompoundTag nbt = playerData.getMorphNbt();
            playerData.setMorphNbt(nbt);
            playerData.syncToClient(player);
            ModUtils.executeOnClient(player, ClientSceneListener.EVENT_SYNC_PLAYER_MORPH, PacketArgument.integer(player.getId()));
        }
    }


    public static void stopAnimation(Player player,String animation){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.stopAnimation(animation);
            playerData.syncToClient(player);
        }
    }

    public static void stopAnimations(Player player){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData != null) {
            playerData.setAnimations(new ConcurrentSet<>());
            playerData.syncToClient(player);
        }
    }
}
