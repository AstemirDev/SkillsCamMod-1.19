package org.astemir.cammod.client.event;


import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.astemir.cammod.SkillsCamMod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SkillsCamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegisterEvents {


}
