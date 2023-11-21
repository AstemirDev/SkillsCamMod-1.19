package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.cammod.client.renderer.MorphRenderer;
import org.astemir.cammod.common.IAttributesMap;
import org.astemir.cammod.common.entity.FollowMobTypeGoal;

import java.util.Optional;
import java.util.function.Function;


public class AddGoalCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("goal");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument priority = CommandArgument.integer("priority");
        CommandArgument speed = CommandArgument.doubleArg("speed");
        CommandArgument sprintSpeed = CommandArgument.doubleArg("sprint_speed");
        CommandArgument distance = CommandArgument.doubleArg("distance");
        CommandArgument targetPriority = CommandArgument.integer("target_priority");
        CommandArgument entityType = CommandArgument.summonArgument("entity_type").suggestion(SuggestionProviders.SUMMONABLE_ENTITIES);
        builder.variants(
                CommandVariant.arguments(target,CommandPart.create("stroll"),priority,speed).execute((p)->{
                    for (Entity entity : target.getEntities(p)) {
                        if (entity instanceof PathfinderMob mob){
                            mob.goalSelector.addGoal(priority.getInt(p),new WaterAvoidingRandomStrollGoal(mob,speed.getDouble(p)));
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("attack"),priority,CommandPart.create("player"),targetPriority,speed).execute((p)->{
                    for (Entity entity : target.getEntities(p)) {
                        if (entity instanceof PathfinderMob mob){
                            AttributeInstance attributeinstance = new AttributeInstance(Attributes.ATTACK_DAMAGE, (p_22273_) -> { });
                            attributeinstance.setBaseValue(5);
                            ((IAttributesMap) mob.getAttributes()).getAttributes().put(Attributes.ATTACK_DAMAGE, attributeinstance);
                            mob.goalSelector.addGoal(priority.getInt(p), new MeleeAttackGoal(mob, speed.getDouble(p), true));
                            mob.targetSelector.addGoal(targetPriority.getInt(p), new NearestAttackableTargetGoal(mob, Player.class, true));
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("attack"),priority,entityType,targetPriority,speed).execute((p)->{
                    Optional<EntityType<?>> typeOptional = Registry.ENTITY_TYPE.getOptional(entityType.getSummonArgument(p));
                    if (typeOptional.isPresent()) {
                        Entity ent2 = typeOptional.get().create(p.getSource().getLevel());
                        if (ent2 instanceof LivingEntity livingEntity) {
                            for (Entity entity : target.getEntities(p)) {
                                if (entity instanceof PathfinderMob mob){
                                    AttributeInstance attributeinstance = new AttributeInstance(Attributes.ATTACK_DAMAGE, (p_22273_) -> { });
                                    attributeinstance.setBaseValue(5);
                                    ((IAttributesMap) mob.getAttributes()).getAttributes().put(Attributes.ATTACK_DAMAGE, attributeinstance);
                                    mob.goalSelector.addGoal(priority.getInt(p), new MeleeAttackGoal(mob, speed.getDouble(p), true));
                                    mob.targetSelector.addGoal(targetPriority.getInt(p), new NearestAttackableTargetGoal(mob, livingEntity.getClass(), true));
                                }
                            }
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("follow"),priority,CommandPart.create("player"),speed).execute((p)->{
                    for (Entity entity : target.getEntities(p)) {
                        if (entity instanceof PathfinderMob mob){
                            mob.goalSelector.addGoal(priority.getInt(p), new FollowMobTypeGoal(mob, Player.class, speed.getDouble(p), 2, 2, false));
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("follow"),priority,entityType,speed).execute((p)->{
                    Optional<EntityType<?>> typeOptional = Registry.ENTITY_TYPE.getOptional(entityType.getSummonArgument(p));
                    if (typeOptional.isPresent()) {
                        Entity ent2 = typeOptional.get().create(p.getSource().getLevel());
                        if (ent2 instanceof LivingEntity livingEntity) {
                            for (Entity entity : target.getEntities(p)) {
                                if (entity instanceof PathfinderMob mob){
                                    mob.goalSelector.addGoal(priority.getInt(p), new FollowMobTypeGoal(mob, livingEntity.getClass(), speed.getDouble(p), 2, 2, false));
                                }
                            }
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("fear"),priority,CommandPart.create("player"),targetPriority,speed,sprintSpeed,distance).execute((p)->{
                    for (Entity entity : target.getEntities(p)) {
                        if (entity instanceof PathfinderMob mob) {
                            mob.goalSelector.addGoal(priority.getInt(p), new AvoidEntityGoal(mob, Player.class, (float) distance.getDouble(p), speed.getDouble(p), sprintSpeed.getDouble(p)));
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("fear"),priority,entityType,targetPriority,speed,sprintSpeed,distance).execute((p)->{
                    Optional<EntityType<?>> typeOptional = Registry.ENTITY_TYPE.getOptional(entityType.getSummonArgument(p));
                    if (typeOptional.isPresent()) {
                        Entity ent2 = typeOptional.get().create(p.getSource().getLevel());
                        if (ent2 instanceof LivingEntity livingEntity) {
                            for (Entity entity : target.getEntities(p)) {
                                if (entity instanceof PathfinderMob mob) {
                                    mob.goalSelector.addGoal(priority.getInt(p), new AvoidEntityGoal(mob, livingEntity.getClass(), (float) distance.getDouble(p), speed.getDouble(p), sprintSpeed.getDouble(p)));
                                }
                            }
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("revenge"),priority,speed).execute((p)->{
                    for (Entity entity : target.getEntities(p)) {
                        if (entity instanceof PathfinderMob mob) {
                            AttributeInstance attributeinstance = new AttributeInstance(Attributes.ATTACK_DAMAGE, (p_22273_) -> { });
                            attributeinstance.setBaseValue(5);
                            ((IAttributesMap) mob.getAttributes()).getAttributes().put(Attributes.ATTACK_DAMAGE, attributeinstance);
                            mob.goalSelector.addGoal(priority.getInt(p), new MeleeAttackGoal(mob, speed.getDouble(p), true));
                            mob.targetSelector.addGoal(priority.getInt(p), new HurtByTargetGoal(mob));
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("look_at"),priority,CommandPart.create("player")).execute((p)->{
                    for (Entity entity : target.getEntities(p)) {
                        if (entity instanceof PathfinderMob mob) {
                            mob.goalSelector.addGoal(priority.getInt(p), new LookAtPlayerGoal(mob, Player.class, 10));
                        }
                    }
                    return 1;
                }),
                CommandVariant.arguments(target,CommandPart.create("look_at"),priority,entityType).execute((p)->{
                    Optional<EntityType<?>> typeOptional = Registry.ENTITY_TYPE.getOptional(entityType.getSummonArgument(p));
                    if (typeOptional.isPresent()) {
                        Entity ent2 = typeOptional.get().create(p.getSource().getLevel());
                        if (ent2 instanceof LivingEntity livingEntity) {
                            for (Entity entity : target.getEntities(p)) {
                                if (entity instanceof PathfinderMob mob) {
                                    mob.goalSelector.addGoal(priority.getInt(p), new LookAtPlayerGoal(mob, livingEntity.getClass(), 10));
                                }
                            }
                        }
                    }
                    return 1;
                })
        );
        dispatcher.register(builder.build());
    }

}
