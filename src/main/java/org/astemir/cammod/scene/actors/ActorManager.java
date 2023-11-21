package org.astemir.cammod.scene.actors;

import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.capability.CapabilityPlayerData;
import org.astemir.cammod.common.entity.EntityClone;
import org.astemir.cammod.common.entity.EntityTaunt;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.data.EntityTimedState;

import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActorManager {

    private CopyOnWriteArrayList<SceneActor> actors = new CopyOnWriteArrayList<>();


    public void updateClient(){
        for (SceneActor dummy : actors) {
            Entity clientEntity = dummy.getClientEntity();
            if (clientEntity != null) {
                EntityTimedState state =  dummy.getState();
                if (state != null) {
                    state.applyClient(clientEntity);
                    state.syncAnimations(clientEntity);
                }
            }
        }
    }

    public void updateServer(MinecraftServer server){
        for (SceneActor dummy : actors) {
            dummy.update();
            if (MainScene.getInstance().isPlaying()) {
                EntityTimedState current = dummy.getState();
                if (current != null) {
                    Entity dummyEntity = dummy.getEntityDummy();
                    current.apply(dummyEntity);
                    dummyEntity.tickCount = dummy.ticks;
                    if (dummyEntity instanceof IAnimatedEntity) {
                        ((IAnimatedEntity) dummyEntity).getAnimationFactory().updateAnimations();
                    }
                    if (!current.getBlockChanges().isEmpty()) {
                        current.getBlockChanges().apply(dummy.getLevel());
                    }
                    Vec3 vec3 = current.getPosition();
                    if (current.getVehicle() != null) {
                        Entity vehicle = current.getVehicle();
                        int vehicleId = getActorId(vehicle);
                        SceneActor vehicleActor = getActor(vehicleId);
                        if (vehicleActor != null) {
                            EntityTimedState vehicleState = getActor(vehicleId).getState();
                            if (vehicleState != null) {
                                Vec3 vehiclePos = vehicleState.getPosition();
                                double d0 = vehiclePos.y + vehicle.getPassengersRidingOffset() + dummyEntity.getMyRidingOffset();
                                vec3 = new Vec3(vehiclePos.x, d0, vehiclePos.z);
                            }
                        }
                    }
                    dummy.getEntityDummy().moveTo(vec3.x, vec3.y, vec3.z, current.getYBodyRot(), current.getXRot());
                    if (dummy.isTaunting()){
//                        if (dummy.getTaunt() != null) {
//                            dummy.getTaunt().moveTo(vec3.x, vec3.y, vec3.z);
//                        }
                    }
                    if (dummy.getEntityDummy() instanceof EntityClone dopelganger){
                        dopelganger.loadData(current.getNbt());
                        server.getPlayerList().broadcastAll(new ClientboundSetEntityDataPacket(dummy.getEntityDummy().getId(), dummy.getEntityDummy().getEntityData(), true));
                    }
                    server.getPlayerList().broadcastAll(new ClientboundSetEquipmentPacket(dummy.getEntityDummy().getId(), current.getEquipment()));
                    server.getPlayerList().broadcastAll(new ClientboundTeleportEntityPacket(dummy.getEntityDummy()));
                    server.getPlayerList().broadcastAll(new ClientboundRotateHeadPacket(dummy.getEntityDummy(), (byte) Mth.floor(current.getYHeadRot() * 256.0F / 360.0F)));
                }
            }
        }
    }


    public void removeActor(SceneActor actor){
        actor.remove();
        actors.remove(actor);
    }

    public void showAll(){
        for (SceneActor actor : actors){
            actor.add();
        }
    }

    public boolean deleteActorById(int id){
        SceneActor dummy = getActor(id);
        if (dummy != null){
            removeActor(dummy);
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteActorLast(){
        if (actors.size() > 0) {
            SceneActor dummy = actors.get(actors.size() - 1);
            if (dummy != null) {
                removeActor(dummy);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void deleteAllActors(){
        for (SceneActor dummy : actors) {
            dummy.remove();
        }
        actors.clear();
    }

    public void hideAll(){
        int[] ids = new int[actors.size()];
        Level level = null;
        if (ids.length > 0){
            level = actors.get(0).getLevel();
            for (int i = 0; i < actors.size(); i++) {
                SceneActor actor = actors.get(i);
                actor.ticks = 0;
                actor.hidden = true;
                if (actor.isTaunting()){
                    //actor.getTaunt().kill();
                }
                ids[i] = actor.getEntityDummy().getId();
            }
            if (level.getServer() != null){
                level.getServer().getPlayerList().broadcastAll(new ClientboundRemoveEntitiesPacket(ids));
            }
        }
    }

    public boolean show(int id){
        SceneActor actor = getActor(id);
        if (actor != null){
            actor.show();
            return true;
        }
        return false;
    }

    public boolean hide(int id){
        SceneActor actor = getActor(id);
        if (actor != null) {
            Level level = actor.getEntityDummy().getLevel();
            if (level != null) {
                actor.hidden = true;
                int entityId = actor.getEntityDummy().getId();
                if (level.getServer() != null) {
                    level.getServer().getPlayerList().broadcastAll(new ClientboundRemoveEntitiesPacket(entityId));
                    return true;
                }
            }
        }
        return false;
    }

    public void addMorphActor(Player player,Level level, Vec3 pos, int length){
        MainScene scene = MainScene.getInstance();
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        SceneActor actor = new SceneActor(level,pos,playerData.getMorphNbt());
        actor.setRecordLength(length);
        actor.setStates(new TreeMap<>(scene.getRecordedStates()));
        if (playerData.isProvoke()){
            actor.setTaunting(true);
            actor.setTauntType(playerData.getProvokeId());
        }
        actors.add(actor);
    }

    public SceneActor getFirst(){
        if (actors.size() > 0){
            return actors.get(0);
        }
        return null;
    }

    public CopyOnWriteArrayList<SceneActor> getActors() {
        return actors;
    }

    public SceneActor getActor(int id){
        if (actors.size() > id) {
            return actors.get(id);
        }else{
            return null;
        }
    }

    public boolean isActor(Entity entity){
        return getActorId(entity) !=-1;
    }

    public int getActorId(Entity entity){
        for (int i = 0; i < actors.size(); i++) {
            Entity ent = actors.get(i).getEntityDummy();
            if (ent.getId() == entity.getId()){
                return i;
            }
        }
        return -1;
    }

    public SceneActor getActor(Entity entity){
        for (int i = 0; i < actors.size(); i++) {
            Entity ent = actors.get(i).getEntityDummy();
            if (ent.getId() == entity.getId()){
                return actors.get(i);
            }
        }
        return null;
    }

}
