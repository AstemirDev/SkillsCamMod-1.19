package org.astemir.cammod.scene.actors;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.cammod.common.entity.EntityClone;
import org.astemir.cammod.common.entity.EntityTaunt;
import org.astemir.cammod.common.entity.ModEntities;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.data.EntityTimedState;
import java.util.Map;
import java.util.TreeMap;


public class SceneActor {

    private Entity entityDummy;
    private Level level;
    private TreeMap<Integer, EntityTimedState> states = new TreeMap<>();
    private int recordLength = 0;
    public int ticks = 0;
    public boolean hidden = true;
    private EntityTaunt taunt;
    private String tauntType;
    private boolean taunting = false;

    public SceneActor(Level level, Vec3 pos, CompoundTag tag) {
        this.level = level;
        this.entityDummy = EntityType.loadEntityRecursive(tag, level, (p_138828_) -> {
            p_138828_.moveTo(pos.x(), pos.y(), pos.z(), p_138828_.getYRot(), p_138828_.getXRot());
            return p_138828_;
        });
        new EntityTimedState(Minecraft.getInstance().player).apply(entityDummy);
    }

    public void add(){
        ticks = 0;
        hidden = false;
        if (level.getServer() != null) {
            level.getServer().getPlayerList().broadcastAll(entityDummy.getAddEntityPacket());
            level.getServer().getPlayerList().broadcastAll(new ClientboundSetEntityDataPacket(this.entityDummy.getId(), this.entityDummy.getEntityData(), true));
        }
        if (isTaunting()){
//            taunt = (EntityTaunt) ModEntities.TAUNT.get().create(level);
//            taunt.setMyActor(this);
//            taunt.setProvokeType(tauntType);
//            taunt.moveTo(entityDummy.getX(),entityDummy.getY(),entityDummy.getZ(), 0,0);
//            level.addFreshEntity(taunt);
        }
        revertBlockChanges();
    }

    public void show(){
        if (level.getServer() != null) {
            level.getServer().getPlayerList().broadcastAll(entityDummy.getAddEntityPacket());
            level.getServer().getPlayerList().broadcastAll(new ClientboundSetEntityDataPacket(this.entityDummy.getId(), this.entityDummy.getEntityData(), true));
        }
        hidden = false;
    }

    public void remove(){
        ticks = 0;
        hidden = true;
        if (level.getServer() != null){
            level.getServer().getPlayerList().broadcastAll(new ClientboundRemoveEntitiesPacket(entityDummy.getId()));
            for (Entity passenger : entityDummy.getPassengers()) {
                level.getServer().getPlayerList().broadcastAll(new ClientboundRemoveEntitiesPacket(passenger.getId()));
            }
        }
        if (isTaunting()){
            if (taunt != null) {
                taunt.kill();
            }
        }
        revertBlockChanges();
    }

    public void revertBlockChanges(){
        states.descendingMap().forEach((time,value)->{
            value.getBlockChanges().revert(level);
        });
    }

    public void update(){
        if (!hidden) {
            if (ticks < recordLength) {
                if (!MainScene.INSTANCE.isPaused()) {
                    ticks++;
                }
            } else {
                remove();
            }
        }
    }


    public EntityTimedState getState(){
        EntityTimedState res = null;
        for (Map.Entry<Integer, EntityTimedState> entry : states.entrySet()) {
            if (entry.getKey() <= ticks){
                res = entry.getValue();
            }else{
                return res;
            }
        }
        return res;
    }


    public Level getLevel() {
        return level;
    }

    public boolean isHidden() {
        return hidden;
    }

    public int getTicks() {
        return ticks;
    }

    public int getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(int recordLength) {
        this.recordLength = recordLength;
    }

    public Entity getClientEntity(){
        for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
            if (entity.getUUID().equals(getEntityDummy().getUUID())){
                return entity;
            }
        }
        return null;
    }

    public void setTauntType(String tauntType) {
        this.tauntType = tauntType;
    }

    public void setTaunting(boolean taunting) {
        this.taunting = taunting;
    }

    public EntityTaunt getTaunt() {
        return taunt;
    }

    public boolean isTaunting() {
        return taunting;
    }

    public TreeMap<Integer, EntityTimedState> getStates() {
        return states;
    }

    public void setStates(TreeMap<Integer, EntityTimedState> states) {
        this.states = states;
    }

    public Entity getEntityDummy() {
        return entityDummy;
    }
}
