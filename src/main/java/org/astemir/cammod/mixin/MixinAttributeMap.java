package org.astemir.cammod.mixin;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.astemir.cammod.common.IAttributesMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(value = AttributeMap.class,priority = 500)
public class MixinAttributeMap implements IAttributesMap {

    @Shadow @Final private Map<Attribute, AttributeInstance> attributes;

    @Override
    public Map<Attribute, AttributeInstance> getAttributes(){
        return attributes;
    }
}
