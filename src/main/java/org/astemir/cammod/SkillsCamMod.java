package org.astemir.cammod;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.astemir.api.IClientLoader;
import org.astemir.api.SkillsForgeMod;
import org.astemir.api.common.event.EventManager;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.client.SCamClientLoader;
import org.astemir.cammod.client.input.SCamKeyBinds;
import org.astemir.cammod.common.command.SCamCommands;
import org.astemir.cammod.scene.listener.ClientSceneListener;
import org.astemir.cammod.common.event.CommonEvents;
import org.astemir.cammod.common.entity.ModEntities;
import org.astemir.cammod.scene.listener.ServerSceneListener;

import static org.astemir.cammod.SkillsCamMod.MOD_ID;


@Mod(MOD_ID)
public class SkillsCamMod extends SkillsForgeMod {

    public static final String MOD_ID = "skillscammod";

    public static SkillsCamMod INSTANCE;

    public SkillsCamMod() {
        INSTANCE = this;
        WorldEventHandler.getInstance().addClientListener(new ResourceLocation(SkillsCamMod.MOD_ID,"client_listener"),new ClientSceneListener());
        WorldEventHandler.getInstance().addServerListener(new ResourceLocation(SkillsCamMod.MOD_ID,"server_listener"),new ServerSceneListener());
        ModEntities.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    @Override
    protected void onCommonSetup(FMLCommonSetupEvent event) {
        EventManager.registerForgeEventClass(SCamCommands.class);
        EventManager.registerForgeEventClass(CommonEvents.class);
        EventManager.registerFMLEvent(CapabilityHandler::registerCapabilities);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityHandler::attachEntityCapability);
    }

    @Override
    protected void onUnsafeClientSetup() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SCamKeyBinds::onRegisterKeys);
    }

    @Override
    public IClientLoader getClientLoader() {
        return new SCamClientLoader();
    }
}
