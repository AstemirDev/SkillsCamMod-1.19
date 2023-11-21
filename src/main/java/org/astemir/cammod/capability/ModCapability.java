package org.astemir.cammod.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;

import javax.annotation.Nullable;


public abstract class ModCapability implements INBTSerializable<CompoundTag> {

    private ResourceLocation id;


    public ModCapability(String modId, String id) {
        this.id = new ResourceLocation(modId,id);
    }

    public ResourceLocation getId() {
        return id;
    }

    protected <T extends ModCapability> void attach(AttachCapabilitiesEvent<Entity> e,ModCapabilityProvider<T> provider){
        e.addCapability(getId(),provider);
    }

    @Nullable
    public <T extends ModCapability> T get(Entity entity) {
        if (entity == null) return null;
        if (!entity.isAlive()) return null;
        return entity.getCapability(CapabilityHandler.getCapability(this)).isPresent() ? (T) entity.getCapability(CapabilityHandler.getCapability(this)).orElseThrow(() -> new IllegalArgumentException("Lazy optional must not be empty")) : null;
    }

    public abstract void onAttach(AttachCapabilitiesEvent<Entity> e);
}
