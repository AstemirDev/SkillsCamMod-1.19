package org.astemir.cammod.scene.data;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityAnimationState {

    private int hurtTime;
    private float animationPosition;
    private float animationSpeed;
    private float animationSpeedOld;
    private int deathTime;
    private float attackAnim;
    private float oAttackAnim;
    private boolean hasImpulse;
    private float fallDistance;
    private boolean hurtMarked;

    public EntityAnimationState(Entity entity) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            hurtTime = livingEntity.hurtTime;
            animationPosition = livingEntity.animationPosition;
            animationSpeed = livingEntity.animationSpeed;
            animationSpeedOld = livingEntity.animationSpeedOld;
            deathTime = livingEntity.deathTime;
            attackAnim = livingEntity.attackAnim;
            oAttackAnim = livingEntity.oAttackAnim;
        }
        hasImpulse = entity.hasImpulse;
        fallDistance = entity.fallDistance;
        hurtMarked = entity.hurtMarked;
    }

    public EntityAnimationState() {
    }

    public void apply(Entity entity){
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.hurtTime = hurtTime;
            livingEntity.animationPosition = animationPosition;
            livingEntity.animationSpeed = animationSpeed;
            livingEntity.animationSpeedOld = animationSpeedOld;
            livingEntity.deathTime = deathTime;
            livingEntity.attackAnim = attackAnim;
            livingEntity.oAttackAnim = oAttackAnim;
        }
        entity.hasImpulse = hasImpulse;
        entity.fallDistance = fallDistance;
        entity.hurtMarked = hurtMarked;
    }


    public void write(FriendlyByteBuf buf){
        buf.writeInt(hurtTime);
        buf.writeFloat(animationPosition);
        buf.writeFloat(animationSpeed);
        buf.writeFloat(animationSpeedOld);
        buf.writeInt(deathTime);
        buf.writeFloat(attackAnim);
        buf.writeFloat(oAttackAnim);
        buf.writeBoolean(hasImpulse);
        buf.writeFloat(fallDistance);;
        buf.writeBoolean(hurtMarked);
    }

    public static EntityAnimationState read(FriendlyByteBuf buf){
        EntityAnimationState state = new EntityAnimationState();
        state.hurtTime = buf.readInt();
        state.animationPosition = buf.readFloat();
        state.animationSpeed = buf.readFloat();
        state.animationSpeedOld = buf.readFloat();
        state.deathTime = buf.readInt();
        state.attackAnim = buf.readFloat();
        state.oAttackAnim = buf.readFloat();
        state.hasImpulse = buf.readBoolean();
        state.fallDistance = buf.readFloat();
        state.hurtMarked = buf.readBoolean();
        return state;
    }

}
