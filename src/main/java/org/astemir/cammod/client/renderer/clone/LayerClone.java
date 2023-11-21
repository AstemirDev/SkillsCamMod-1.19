package org.astemir.cammod.client.renderer.clone;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.client.SkillsRenderTypes;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsModelLayer;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.lib.shimmer.ShimmerLib;
import org.astemir.api.math.components.Color;
import org.astemir.cammod.common.entity.EntityClone;


public class LayerClone extends SkillsModelLayer<EntityClone, IDisplayArgument,ModelClone> {

	public CloneTexture texture;
	private boolean shimmering = false;
	private boolean glowing = false;
	private boolean transparent = false;
	private Color color = Color.WHITE;

	public LayerClone(ModelClone model,CloneTexture texture,Color color,boolean shimmering,boolean glowing) {
		super(model);
		this.texture = texture;
		this.shimmering = shimmering;
		this.glowing = glowing;
		this.color = color;
	}

	@Override
	public void render(PoseStack pPoseStack, MultiBufferSource pBuffer, EntityClone instance, int pPackedLight, float pPartialTick, float r, float g, float b, float a) {
		if (shimmering){
			ShimmerLib.postModelForce(pPoseStack,getModel(), getRenderType(instance),ShimmerLib.LIGHT_UNSHADED, OverlayTexture.NO_OVERLAY,color.r,color.g,color.b,color.a);
			ShimmerLib.renderEntityPost();
		}
		VertexConsumer vertexconsumer = pBuffer.getBuffer(getRenderType(instance));
		getModel().renderModel(pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, r,g,b,a, RenderCall.LAYER,false);
	}

	@Override
	public RenderType getRenderType(EntityClone instance) {
		if (transparent && glowing){
			return SkillsRenderTypes.entityTranslucentEmissive(getTexture(instance));
		}
		if (transparent){
			return SkillsRenderTypes.entityTranslucent(getTexture(instance));
		}
		if (glowing){
			return SkillsRenderTypes.eyesTransparentNoCull(getTexture(instance));
		}
		return SkillsRenderTypes.entityCutoutNoCull(getTexture(instance));
	}

	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	@Override
	public ResourceLocation getTexture(EntityClone target) {
		return texture.getTexture(target);
	}
}
