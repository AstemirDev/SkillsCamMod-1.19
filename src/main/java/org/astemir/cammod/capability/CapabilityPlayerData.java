package org.astemir.cammod.capability;

import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.SkillsCamMod;
import org.astemir.cammod.scene.listener.ClientSceneListener;
import org.astemir.cammod.utils.ModUtils;

public class CapabilityPlayerData extends ModCapability{

    private CompoundTag morphNbt = new CompoundTag();
    private ConcurrentSet<String> animations = new ConcurrentSet<>();
    private Vector3 rotation = new Vector3(0,0,0);
    private Vector3 position = new Vector3(0,0,0);
    private Vector3 scale = new Vector3(1,1,1);
    private boolean morphed = false;
    private boolean provoke = false;
    private String provokeId = null;

    public CapabilityPlayerData() {
        super(SkillsCamMod.MOD_ID, "player_mod_data");
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag result =  new CompoundTag();
        morphNbt.put("Position", position.toNbt());
        morphNbt.put("Rotation", rotation.toNbt());
        morphNbt.put("Scale", scale.toNbt());
        result.put("Morph",morphNbt);
        ListTag listTag = new ListTag();
        for (String animation : animations) {
            CompoundTag animationTag = new CompoundTag();
            animationTag.putString("Name",animation);
            listTag.add(animationTag);
        }
        result.put("Animations",listTag);
        result.putBoolean("IsMorphed",morphed);
        if (provokeId != null) {
            result.putString("ProvokeId", provokeId);
        }
        result.putBoolean("Provoke",provoke);
        return result;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        morphNbt = nbt.getCompound("Morph");
        ListTag listTag =  nbt.getList("Animations",ListTag.TAG_COMPOUND);
        ConcurrentSet<String> animations = new ConcurrentSet<>();
        for (Tag tag : listTag) {
            animations.add(((CompoundTag)tag).getString("Name"));
        }
        this.position = Vector3.fromNbt(morphNbt.getCompound("Position"));
        this.rotation = Vector3.fromNbt(morphNbt.getCompound("Rotation"));
        this.scale = Vector3.fromNbt(morphNbt.getCompound("Scale"));
        this.animations = animations;
        this.morphed = nbt.getBoolean("IsMorphed");
        this.provokeId = nbt.getString("ProvokeId");
        this.provoke = nbt.getBoolean("Provoke");
    }

    @Override
    public void onAttach(AttachCapabilitiesEvent<Entity> e) {
        if (e.getObject() instanceof Player) {
            attach(e, new Provider());
        }
    }

    public void syncToClient(Entity entity){
        ModUtils.executeOnClient(entity, ClientSceneListener.EVENT_SYNC_PLAYER_DATA, PacketArgument.integer(entity.getId()),PacketArgument.nbt(serializeNBT()));
    }


    public void addAnimation(String animation){
        if (!animations.contains(animation)) {
            animations.add(animation);
        }
    }

    public void setMorphed(boolean morphed) {
        this.morphed = morphed;
    }

    public void setMorphNbt(CompoundTag morphNbt) {
        this.morphNbt = morphNbt;
    }

    public CompoundTag getMorphNbt() {
        return morphNbt;
    }

    public ConcurrentSet<String> getAnimations() {
        return animations;
    }

    public void setAnimations(ConcurrentSet<String> animations) {
        this.animations = animations;
    }

    public void stopAnimation(String animation){
        animations.remove(animation);
    }

    public void setRotation(Vector3 rotation) {
        this.rotation = rotation;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setScale(Vector3 scale) {
        this.scale = scale;
    }

    public boolean isMorphed() {
        return morphed;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getScale() {
        return scale;
    }

    public String getProvokeId() {
        return provokeId;
    }

    public void setProvokeId(String provoke) {
        this.provokeId = provoke;
    }

    public boolean isProvoke() {
        return provoke;
    }

    public void setProvoke(boolean provoke) {
        this.provoke = provoke;
    }

    public static class Provider extends ModCapabilityProvider<CapabilityPlayerData>{

        @Override
        public LazyOptional<CapabilityPlayerData> initializeOptional() {
            return LazyOptional.of(CapabilityPlayerData::new);
        }

        @Override
        public Capability<CapabilityPlayerData> getCapability() {
            return CapabilityHandler.getCapability(CapabilityHandler.PLAYER_DATA);
        }
    }
}
