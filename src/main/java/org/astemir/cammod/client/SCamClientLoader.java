package org.astemir.cammod.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import org.astemir.api.IClientLoader;
import org.astemir.api.common.event.EventManager;
import org.astemir.cammod.client.event.CameraEvents;
import org.astemir.cammod.client.event.InputEvents;
import org.astemir.cammod.client.event.RenderEvents;
import org.astemir.cammod.client.event.TickEvents;
import org.astemir.cammod.client.renderer.clone.RendererClone;
import org.astemir.cammod.common.entity.ModEntities;
import org.astemir.cammod.scene.MainScene;

public class SCamClientLoader implements IClientLoader {


    @Override
    public void load() {
        ReloadableResourceManager resourceManager = (ReloadableResourceManager)Minecraft.getInstance().getResourceManager();
        resourceManager.registerReloadListener(MainScene.getInstance().getProperties());
        EventManager.registerForgeEventClass(InputEvents.class);
        EventManager.registerForgeEventClass(TickEvents.class);
        EventManager.registerForgeEventClass(RenderEvents.class);
        EventManager.registerForgeEventClass(CameraEvents.class);
        EntityRenderers.register(ModEntities.CLONE.get(), RendererClone::new);
    }
}
