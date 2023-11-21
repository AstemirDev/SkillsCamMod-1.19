package org.astemir.cammod.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ModCapabilityProvider<T extends ModCapability> implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    private final LazyOptional<T> instance = initializeOptional();

    public abstract LazyOptional<T> initializeOptional();

    public abstract Capability<T> getCapability();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability().orEmpty(cap, instance.cast());
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.orElseThrow(NullPointerException::new).serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.orElseThrow(NullPointerException::new).deserializeNBT(nbt);
    }
}
