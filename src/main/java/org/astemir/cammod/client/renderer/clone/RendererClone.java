package org.astemir.cammod.client.renderer.clone;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import org.astemir.api.client.render.SkillsRendererLivingEntity;
import org.astemir.cammod.common.entity.EntityClone;

public class RendererClone extends SkillsRendererLivingEntity<EntityClone, WrapperClone> {

    public RendererClone(EntityRendererProvider.Context context) {
        super(context,new WrapperClone());
    }


    @Override
    protected int getBlockLightLevel(EntityClone pEntity, BlockPos pPos) {
        ModelClone modelClone = (ModelClone) getModel().getModel(pEntity);
        if (modelClone.modelLight > 0){
            return modelClone.modelLight;
        }else {
            return super.getBlockLightLevel(pEntity, pPos);
        }
    }
}
