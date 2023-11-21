package org.astemir.cammod.scene;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.capability.CapabilityPlayerData;
import org.astemir.cammod.client.ClientProperties;
import org.astemir.cammod.scene.actors.ActorManager;
import org.astemir.cammod.scene.actors.SceneActor;
import org.astemir.cammod.scene.data.EntityTimedState;
import java.util.*;

public class MainScene {


    public static MainScene INSTANCE = new MainScene();

    private TreeMap<Integer, EntityTimedState> recordedStates = new TreeMap<>();

    private ActorManager actorManager = new ActorManager();
    private ClientProperties properties = new ClientProperties();

    private int ticks = 0;
    private boolean recording = false;
    private boolean paused = false;


    public boolean record(Level level){
        recording = true;
        recordedStates.clear();
        revertBlockChanges(level);
        clearBlockChanges();
        if (!isPlaying()) {
            play(level);
        }
        return true;
    }

    public boolean stopRecording(Player player,Level level, Vec3 pos){
        return stopRecording(player,level,pos,ticks);
    }

    public boolean stopRecording(Player player,Level level, Vec3 pos, int length){
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        if (playerData.isMorphed()){
            actorManager.addMorphActor(player,level,pos,length);
        }
        recording = false;
        ticks = 0;
        recording = false;
        paused = false;
        revertBlockChanges(level);
        return true;
    }

    public void stop(){
        paused = false;
        actorManager.hideAll();
        SceneActor actor = actorManager.getFirst();
        if (actor != null) {
            revertBlockChanges(actor.getLevel());
        }
    }

    public void play(Level level){
        actorManager.showAll();
        revertBlockChanges(level);
    }

    public void updateClient(){
        AbstractClientPlayer player = Minecraft.getInstance().player;
        if (recording && player != null) {
            if (!isPaused()) {
                ticks++;
            }
            EntityTimedState state = new EntityTimedState(player);
            CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
            state.setCustomPosition(playerData.getPosition());
            state.setCustomScale(playerData.getScale());
            state.setCustomRotation(playerData.getRotation());
            if (playerData.getAnimations() != null) {
                state.setAnimations(new HashSet<>(playerData.getAnimations()));
            }
            recordedStates.put(ticks,state);
        }
        if (isPlaying()) {
            actorManager.updateClient();
        }
    }

    public void updateServer(MinecraftServer server){
        actorManager.updateServer(server);
    }

    public void revertBlockChanges(Level level){
        recordedStates.descendingMap().forEach((time,changes)->{
            if (!changes.getBlockChanges().isEmpty()) {
                changes.getBlockChanges().revert(level);
            }
        });
    }

    public void clearBlockChanges(){
        for (EntityTimedState value : recordedStates.values()) {
            value.getBlockChanges().clear();
        }
    }


    public boolean isPlaying() {
        for (SceneActor actor : actorManager.getActors()) {
            if (!actor.isHidden()){
                return true;
            }
        }
        return false;
    }

    public EntityTimedState getCurrentTickState(){
        return recordedStates.get(ticks);
    }

    public TreeMap<Integer, EntityTimedState> getRecordedStates() {
        return recordedStates;
    }

    public ActorManager getActorManager() {
        return actorManager;
    }

    public static MainScene getInstance() {
        return INSTANCE;
    }

    public boolean isRecording() {
        return recording;
    }

    public int getTicks() {
        return ticks;
    }

    public ClientProperties getProperties() {
        return properties;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
