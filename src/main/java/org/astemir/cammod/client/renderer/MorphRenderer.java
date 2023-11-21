package org.astemir.cammod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.client.render.ISkillsRenderer;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.mixin.MixinEntity;
import org.astemir.api.mixin.client.MixinClientLevel;
import org.astemir.api.mixin.client.MixinLevelRenderer;
import org.astemir.api.mixin.client.MixinLivingEntityRenderer;
import org.astemir.cammod.capability.CapabilityHandler;
import org.astemir.cammod.capability.CapabilityPlayerData;
import org.astemir.cammod.scene.data.EntityTimedState;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MorphRenderer {

    private Map<Integer,Entity> morphs = new HashMap<>();

    public void render(Player player, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        if (player != null) {
            Entity morph = morphs.get(player.getId());
            if (morph != null) {
                CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
                if (playerData != null) {
                    EntityTimedState property = new EntityTimedState(player);
                    property.setAnimations(playerData.getAnimations());
                    property.apply(morph);
                    if (morph instanceof LivingEntity livingEntity) {
                        livingEntity.yBodyRot = player.yBodyRot;
                        livingEntity.yBodyRotO = player.yBodyRotO;
                    }
                    property.syncAnimations(morph);
                    morph.tickCount = player.tickCount;
                    EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
                    EntityRenderer renderer = dispatcher.getRenderer(morph);
                    if (renderer instanceof ISkillsRenderer iSkillsRenderer){
                        iSkillsRenderer.animate((ICustomRendered)morph, p_114487_);
                    }
                    p_114488_.pushPose();
                    float scaleX = playerData.getScale().x;
                    float scaleY = playerData.getScale().y;
                    float scaleZ = playerData.getScale().z;
                    p_114488_.translate(playerData.getPosition().x,playerData.getPosition().y,playerData.getPosition().z);
                    p_114488_.scale(scaleX,scaleY, scaleZ);
                    p_114488_.mulPose(Vector3f.ZP.rotationDegrees(playerData.getRotation().z));
                    p_114488_.mulPose(Vector3f.YP.rotationDegrees(playerData.getRotation().y));
                    p_114488_.mulPose(Vector3f.XP.rotationDegrees(playerData.getRotation().x));
                    renderer.render(morph,p_114486_, p_114487_, p_114488_, p_114489_, p_114490_);
                    p_114488_.popPose();
                }
            }
        }
    }

    public void updateMorph(Player player) {
        CapabilityPlayerData playerData = CapabilityHandler.PLAYER_DATA.get(player);
        CompoundTag tag = playerData.getMorphNbt();
        morphs.put(player.getId(),EntityType.loadEntityRecursive(tag, Minecraft.getInstance().level, Function.identity()));
    }

    public Entity getMorph(Player player) {
        return morphs.get(player.getId());
    }
}
