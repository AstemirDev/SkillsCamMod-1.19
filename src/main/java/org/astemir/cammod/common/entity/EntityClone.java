package org.astemir.cammod.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.astemir.api.client.display.IDisplayArgument;
import org.astemir.api.common.animation.*;
import org.astemir.api.common.animation.objects.IAnimatedEntity;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.misc.ICustomRendered;
import org.astemir.api.math.random.RandomUtils;
import org.checkerframework.checker.units.qual.K;

public class EntityClone extends PathfinderMob implements ICustomRendered, IAnimatedEntity, IEntityAdditionalSpawnData {

    public static EntityDataAccessor<String> MODEL = SynchedEntityData.defineId(EntityClone.class, EntityDataSerializers.STRING);

    public AnimationFactory animationFactory = new AnimationFactory(this,ANIMATION_IDLE,ANIMATION_WALK,ANIMATION_RUN,ANIMATION_CROUCHING,ANIMATION_CROUCHING_IDLE,ANIMATION_RIDING,ANIMATION_RIDING_IDLE,ANIMATION_FALLING,ANIMATION_JUMP, ANIMATION_LAND,ANIMATION_CONSUME,ANIMATION_SHOOT,ANIMATION_ATTACK_1,ANIMATION_ATTACK_2,ANIMATION_DIG_1,ANIMATION_DIG_2,ANIMATION_HURT,ANIMATION_SPYGLASS_1,ANIMATION_SPYGLASS_2,ANIMATION_ROCK);
    public static Animation ANIMATION_IDLE = new Animation("animation.model.idle",2.08f).smoothness(2).layer(0).loop();
    public static Animation ANIMATION_WALK = new Animation("animation.model.walk",0.96f).smoothness(2).layer(0).loop();
    public static Animation ANIMATION_RUN = new Animation("animation.model.run",0.48f).smoothness(1).layer(0).loop();
    public static Animation ANIMATION_CROUCHING = new Animation("animation.model.crouching",2.08f).smoothness(2).layer(0).loop();
    public static Animation ANIMATION_CROUCHING_IDLE = new Animation("animation.model.crouching_idle",2.08f).smoothness(2).layer(0).loop();
    public static Animation ANIMATION_RIDING = new Animation("animation.model.riding",2.08f).smoothness(1).layer(0).loop();
    public static Animation ANIMATION_RIDING_IDLE = new Animation("animation.model.riding_idle",2.08f).smoothness(1).layer(0).loop();
    public static Animation ANIMATION_FALLING = new Animation("animation.model.falling",0.48f).smoothness(2).layer(0).loop();
    public static Animation ANIMATION_JUMP = new Animation("animation.model.jump",0.36f).layer(1).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static Animation ANIMATION_LAND = new Animation("animation.model.land",0.52f).layer(1).loop(Animation.Loop.HOLD_ON_LAST_FRAME);
    public static Animation ANIMATION_CONSUME = new Animation("animation.model.consume",1.88f).layer(2).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_SHOOT = new Animation("animation.model.shoot",1.48f).layer(2).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_ATTACK_1 = new Animation("animation.model.attack1",0.4f).layer(2).speed(1f).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_ATTACK_2 = new Animation("animation.model.attack2",0.4f).layer(2).speed(1f).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_DIG_1 = new Animation("animation.model.dig1",0.44f).layer(2).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_DIG_2 = new Animation("animation.model.dig2",0.52f).layer(2).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_SPYGLASS_1 = new Animation("animation.model.spyglass1",2.08f).layer(2).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_SPYGLASS_2 = new Animation("animation.model.spyglass2",2.08f).layer(2).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_ROCK = new Animation("animation.model.rock",0.44f).layer(2).priority(1).loop(Animation.Loop.FALSE);
    public static Animation ANIMATION_HURT = new Animation("animation.model.hurt",0.2f).layer(3).priority(1).loop(Animation.Loop.FALSE);

    private Animation[] customAnimations;

    protected EntityClone(EntityType<? extends PathfinderMob> p_21683_, Level p_21684_) {
        super(p_21683_, p_21684_);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(MODEL,"default");
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        loadData(pCompound);
    }

    public void loadData(CompoundTag pCompound){
        if (pCompound.contains("Model")) {
            entityData.set(MODEL,pCompound.getString("Model"));
        }
        if (pCompound.contains("CustomAnimations")){
            ListTag animationsListTag = pCompound.getList("CustomAnimations", Tag.TAG_COMPOUND);
            if (!animationsListTag.isEmpty()) {
                customAnimations = new Animation[animationsListTag.size()];
                for (int i = 0; i < animationsListTag.size(); i++) {
                    Tag tag = animationsListTag.get(i);
                    if (tag instanceof CompoundTag animationCompoundTag) {
                        Animation animation = animationDeserialize(animationCompoundTag);
                        if (animation != null){
                            customAnimations[i] = animation;
                        }
                    }
                }
                animationFactory = new AnimationFactory(this,customAnimations);
            }
        }
    }

    private Animation animationDeserialize(CompoundTag tag){
        if (tag.contains("Name") && tag.contains("Length")){
            Animation animation = new Animation(tag.getString("Name"),tag.getFloat("Length"));
            if (tag.contains("Speed")) {
                animation = animation.speed(tag.getFloat("Speed"));
            }
            if (tag.contains("Smoothness")) {
                animation = animation.smoothness(tag.getFloat("Smoothness"));
            }
            return animation;
        }
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (customAnimations == null) {
            if (EntityUtils.isMoving(this,-0.1f,0.1f)){
                if (hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                    animationFactory.play(ANIMATION_CROUCHING);
                }else
                if (hasEffect(MobEffects.MOVEMENT_SPEED)){
                    animationFactory.play(ANIMATION_RUN);
                }else{
                    animationFactory.play(ANIMATION_WALK);
                }
            }else{
                if (hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                    animationFactory.play(ANIMATION_CROUCHING_IDLE);
                }else {
                    animationFactory.play(ANIMATION_IDLE);
                }
            }
        }
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        int size = customAnimations != null ? customAnimations.length : 0;
        buffer.writeInt(size);
        if (size > 0) {
            for (Animation animation : customAnimations) {
                buffer.writeUtf(animation.getName());
                buffer.writeFloat(animation.getLength());
                buffer.writeFloat(animation.getSpeed());
                buffer.writeFloat(animation.getSmoothness());
            }
        }
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        int size = additionalData.readInt();
        if (size > 0) {
            Animation[] animations = new Animation[size];
            for (int i = 0; i < size; i++) {
                Animation animation = new Animation(additionalData.readUtf(), additionalData.readFloat());
                animation.speed(additionalData.readFloat());
                animation.smoothness(additionalData.readFloat());
                animations[i] = animation;
            }
            animationFactory = new AnimationFactory(this, animations);
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (customAnimations == null) {
            if (RandomUtils.doWithChance(75)) {
                animationFactory.play(ANIMATION_ATTACK_1);
            }else{
                animationFactory.play(ANIMATION_ATTACK_2);
            }
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public String getModelName() {
        return entityData.get(MODEL);
    }

    @Override
    public <K extends IDisplayArgument> AnimationFactory getAnimationFactory(K argument) {
        return animationFactory;
    }
}
