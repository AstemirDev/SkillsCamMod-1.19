package org.astemir.cammod.common;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

import java.util.Map;

public interface IAttributesMap {

    Map<Attribute, AttributeInstance> getAttributes();

}
