package org.astemir.cammod.capability;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CapabilityHandler {

    private static Map<ModCapability,Capability> capabilities = new HashMap<>();

    public static final CapabilityPlayerData PLAYER_DATA = register(new CapabilityPlayerData());

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (ModCapability capability : capabilities.keySet()) {
            event.register(capability.getClass());
        }
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> e) {
        for (ModCapability capability : capabilities.keySet()) {
            capability.onAttach(e);
        }
    }

    public static <T extends ModCapability> T register(T capability){
        capabilities.put(capability,CapabilityManager.get(new CapabilityToken<>(){}));
        return capability;
    }

    public static <T extends ModCapability> Capability<T> getCapability(T capability) {
        return capabilities.get(capability);
    }
}
