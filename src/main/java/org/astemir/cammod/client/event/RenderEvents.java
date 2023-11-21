package org.astemir.cammod.client.event;


import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.api.client.event.*;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.capability.CapabilityPlayerData;
import org.astemir.cammod.client.ClientUtils;
import org.astemir.cammod.client.renderer.MorphRenderer;
import org.astemir.cammod.client.ClientProperties;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.actors.ActorManager;
import org.astemir.cammod.scene.data.EntityTimedState;
import org.astemir.cammod.scene.listener.ServerSceneListener;
import org.astemir.cammod.utils.ModUtils;


public class RenderEvents {

    public static ResourceLocation RECORDING_ICON = new ResourceLocation("skillscammod:textures/ui/recording.png");

    public static MorphRenderer RENDERER = new MorphRenderer();

    public static boolean SHOW_NAMETAGS = false;


    @SubscribeEvent
    public static void onFogColorCompute(ViewportEvent.ComputeFogColor e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customFogColor){
            e.setRed(properties.fogColor.r);
            e.setGreen(properties.fogColor.g);
            e.setBlue(properties.fogColor.b);
        }
    }

    @SubscribeEvent
    public static void onFogRender(ViewportEvent.RenderFog e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customFogShape){
            e.setFogShape(properties.fogShape);
        }
        if (properties.customFogDistance){
            e.setNearPlaneDistance(properties.fogNearDistance);
            e.setFarPlaneDistance(properties.fogFarDistance);
        }
        if (properties.customFogShape || properties.customFogDistance){
            e.setCanceled(true);
        }
    }


    @SubscribeEvent
    public static void onSunEventRender(SkyRenderEvent e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customSunSize){
            e.setSunSize(properties.sunSize);
        }
        if (properties.customSunRot){
            e.setXRot(properties.sunXRot);
            e.setYRot(properties.sunYRot);
        }
        if (properties.customSunColor){
            e.setColor(properties.sunColor);
        }
    }

    @SubscribeEvent
    public static void onDarknessCompute(SkySetupEvent.ComputeDarkness e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customDarkness){
            e.setDarkness(properties.darkness);
        }
    }

    @SubscribeEvent
    public static void onStarBrightnessCompute(SkySetupEvent.ComputeStarBrightness e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customStarBrightness){
            e.setBrightness(properties.starBrightness);
        }
    }

    @SubscribeEvent
    public static void onCloudColorCompute(SkySetupEvent.ComputeCloudColor e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customCloudsColor){
            e.setColor(properties.cloudsColor.toVec3());
        }
    }

    @SubscribeEvent
    public static void onSunriseColorCompute(SkySetupEvent.ComputeSunriseColor e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customSunriseColor){
            e.setSunriseColor(new float[]{properties.sunriseColor.r,properties.sunriseColor.g,properties.sunriseColor.b,properties.sunriseColor.a});
        }
    }

    @SubscribeEvent
    public static void onSkyColorCompute(SkySetupEvent.ComputeSkyColor e){
        ClientProperties properties = MainScene.getInstance().getProperties();
        if (properties.customSkyColor){
            e.setColor(properties.skyColor.toVec3());
        }
    }

    @SubscribeEvent
    public static void onLivingRenderRotate(LivingTransformEvent.Rotation e){
        if (MainScene.getInstance().getActorManager().isActor(e.getEntityLiving())){
            EntityTimedState state = MainScene.getInstance().getActorManager().getActor(e.getEntityLiving()).getState();
            if (state != null) {
                e.getMatrixStack().translate(state.getCustomPosition().x, state.getCustomPosition().y, state.getCustomPosition().z);
                e.getMatrixStack().mulPose(Vector3f.ZP.rotationDegrees(state.getCustomRotation().z));
                e.getMatrixStack().mulPose(Vector3f.YP.rotationDegrees(state.getCustomRotation().y));
                e.getMatrixStack().mulPose(Vector3f.XP.rotationDegrees(state.getCustomRotation().x));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingRenderScale(LivingTransformEvent.Scale e){
        if (MainScene.getInstance().getActorManager().isActor(e.getEntityLiving())){
            EntityTimedState state = MainScene.getInstance().getActorManager().getActor(e.getEntityLiving()).getState();
            if (state != null) {
                e.getMatrixStack().scale(state.getCustomScale().x, state.getCustomScale().y, state.getCustomScale().z);
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerRenderPre(RenderLivingEvent.Pre event){
        MainScene scene = MainScene.getInstance();
        ActorManager actorManager = scene.getActorManager();
        if (actorManager.isActor(event.getEntity())) {
            if (SHOW_NAMETAGS) {
                Entity entity = event.getEntity();
                EntityRenderDispatcher renderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
                PoseStack stack = event.getPoseStack();
                float f = entity.getBbHeight() + 0.75F;
                stack.pushPose();
                event.getPoseStack().translate(0.0D, (double) f, 0.0D);
                event.getPoseStack().mulPose(renderDispatcher.cameraOrientation());
                event.getPoseStack().scale(-0.05F, -0.05F, 0.05F);
                Matrix4f matrix4f = stack.last().pose();
                float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                int j = (int) (f1 * 255.0F) << 24;
                Font font = event.getRenderer().getFont();
                Component name = Component.literal(actorManager.getActorId(entity) + "");
                float f2 = (float) (-font.width(name) / 2);
                font.drawInBatch(name, f2, 0, 553648127, false, matrix4f, event.getMultiBufferSource(), false, j, event.getPackedLight());
                font.drawInBatch(name, f2, 0, -1, false, matrix4f, event.getMultiBufferSource(), false, 0, event.getPackedLight());
                stack.popPose();
            }
        }
        if (event.getEntity() instanceof Player player) {
            CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
            if (playerData != null) {
                if (playerData.isMorphed()) {
                    RENDERER.render(player, event.getEntity().yHeadRot, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLayer(RenderGuiOverlayEvent.Post e) {
        Minecraft minecraft = Minecraft.getInstance();
        AbstractClientPlayer player = minecraft.player;
        MainScene scene = MainScene.getInstance();
        if (player != null) {
            if (scene.isRecording()) {
                int offsetX = 4;
                int offsetY = 4;
                int time = scene.getTicks()/20;
                String timeString = String.format("%d:%02d:%02d", time / 3600, (time % 3600) / 60, (time % 60));
                int textWidth = minecraft.font.width(timeString);
                Gui.drawCenteredString(e.getPoseStack(), minecraft.font,timeString , offsetX+textWidth/2, offsetY+32,0xFFFFFF);
                if (player.tickCount % 40 >= 20) {
                    ClientUtils.drawSprite(e.getPoseStack(), RECORDING_ICON, 4, 4, 32, 32, 1, 1, 1,1);
                } else {
                    ClientUtils.drawSprite(e.getPoseStack(), RECORDING_ICON, 4, 4, 32, 32, 0.5f, 0.5f, 0.5f,1);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAnimationEnd(ClientAnimationEvent.End e){
        if (e.getEntity() instanceof Entity entity) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                Entity morph = RENDERER.getMorph(player);
                if (morph != null) {
                    if (entity.getId() == morph.getId()) {
                        int id = Minecraft.getInstance().player.getId();
                        ModUtils.executeOnServer(Minecraft.getInstance().player, ServerSceneListener.EVENT_ANIMATION_END, PacketArgument.integer(id), PacketArgument.str(e.getAnimation().getName()));
                    }
                }
            }
        }
    }
}
