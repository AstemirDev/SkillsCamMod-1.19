package org.astemir.cammod.client.renderer.clone;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import org.astemir.api.client.animation.SmoothnessType;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.client.model.SkillsAnimatedModel;
import org.astemir.api.client.render.RenderCall;
import org.astemir.api.client.render.cube.ModelElement;
import org.astemir.api.math.MathUtils;
import org.astemir.cammod.common.entity.EntityClone;


public class ModelClone extends SkillsAnimatedModel<EntityClone, IDisplayArgument> {

	public CloneTexture texture;
	public int modelLight = 0;
	public boolean transparent = false;
	public ModelClone(CloneTexture texture, ResourceLocation model, ResourceLocation animations) {
		super(model,animations);
		this.texture = texture;
		this.smoothnessType(SmoothnessType.EXPONENTIAL);
		this.smoothness(4);
	}


	@Override
	public void customAnimate(EntityClone animated, IDisplayArgument argument, float limbSwing, float limbSwingAmount, float ticks, float delta, float headYaw, float headPitch) {
		ModelElement head = getModelElement("bipedHead");
		ModelElement rightArm = getModelElement("bipedRightArm");
		ModelElement leftArm = getModelElement("bipedLeftArm");
		if (head == null){
			head = getModelElement("head");
		}
		if (head == null){
			head = getModelElement("Head");
		}
		if (head != null) {
			head.customRotationY = headYaw * ((float) Math.PI / 180F);
			head.customRotationX = headPitch * ((float) Math.PI / 180F);
		}
		if (rightArm != null) {
			if (!animated.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
				rightArm.customRotationX = -MathUtils.rad(22.5f);
			}else{
				rightArm.customRotationX = 0;
			}
		}
		if (leftArm != null) {
			if (!animated.getItemInHand(InteractionHand.OFF_HAND).isEmpty()) {
				leftArm.customRotationX = -MathUtils.rad(22.5f);
			}else{
				leftArm.customRotationX = 0;
			}
		}
	}


	@Override
	public void onRenderModelCube(ModelElement cube, PoseStack matrixStackIn, VertexConsumer bufferIn, RenderCall renderCall, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (renderCall == RenderCall.MODEL) {
			EntityClone player = getRenderTarget();
			if (cube.getName().equals("ItemRight")) {
				if (player != null) {
					matrixStackIn.pushPose();
					matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
					matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
					matrixStackIn.translate(0, 0.0725D, -0.0725D);
					renderItem(player.getItemBySlot(EquipmentSlot.MAINHAND), ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, matrixStackIn, packedLightIn);
					matrixStackIn.popPose();
				}
			}
			if (cube.getName().equals("ItemLeft")) {
				if (player != null) {
					matrixStackIn.pushPose();
					matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
					matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
					matrixStackIn.translate(0, 0.0725D, -0.0725D);
					renderItem(player.getItemBySlot(EquipmentSlot.OFFHAND), ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, matrixStackIn, packedLightIn);
					matrixStackIn.popPose();
				}
			}
			bufferIn = returnDefaultBuffer();
		}
	}


	@Override
	public VertexConsumer returnDefaultBuffer() {
		if (transparent){
			EntityClone clone = getRenderTarget();
			if (clone != null) {
				return modelWrapper.getMultiBufferSource().getBuffer(RenderType.entityTranslucent(getTexture(clone)));
			}
		}
		return modelWrapper.getMultiBufferSource().getBuffer(modelWrapper.getRenderType());
	}

	@Override
	public ResourceLocation getTexture(EntityClone target) {
		return texture.getTexture(target);
	}
}
