package org.astemir.cammod.common.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.astemir.cammod.SkillsCamMod;

@Mod.EventBusSubscriber(modid = SkillsCamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,"skillscammod");

    public static RegistryObject<EntityType> CLONE = ENTITIES.register("clone",()->EntityType.Builder.of(EntityClone::new,MobCategory.MISC).sized(0.6F, 1.8F).clientTrackingRange(32).updateInterval(2).build(new ResourceLocation("clone").toString()));
    public static RegistryObject<EntityType> TAUNT = ENTITIES.register("taunt",()->EntityType.Builder.of(EntityTaunt::new,MobCategory.MISC).sized(1f, 1f).clientTrackingRange(0).build(new ResourceLocation("taunt").toString()));


    @SubscribeEvent
    public static void onRegisterAttributes(EntityAttributeCreationEvent e){
        e.put(CLONE.get(),Mob.createMobAttributes().add(Attributes.MAX_HEALTH,20).add(Attributes.MOVEMENT_SPEED,0.4f).build());
        e.put(TAUNT.get(), Mob.createMobAttributes().add(Attributes.MAX_HEALTH,9999).add(Attributes.MOVEMENT_SPEED,0.4f).build());
    }
}
