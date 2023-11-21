package org.astemir.cammod.common.entity;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.astemir.api.common.entity.EntityData;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.cammod.scene.actors.SceneActor;


public class EntityTaunt extends Mob {

    public static EntityData<String> PROVOKE_TYPE = new EntityData<>(EntityTaunt.class,"ProvokeType", EntityDataSerializers.STRING,"");
    private SceneActor myActor;

    public EntityTaunt(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        PROVOKE_TYPE.register(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (getProvokeType() != null) {
            EntityType<?> type = EntityType.byString(getProvokeType()).get();
            for (Entity aggressor : EntityUtils.getEntities(type, level, blockPosition(), 30,(e)->true)) {
                if (aggressor instanceof Mob livingEntity){
                    livingEntity.setTarget(this);
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.OUT_OF_WORLD){
            return super.hurt(pSource,pAmount);
        }
        return false;
    }


    public SceneActor getMyActor() {
        return myActor;
    }

    public void setMyActor(SceneActor myActor) {
        this.myActor = myActor;
    }

    public String getProvokeType(){
        return PROVOKE_TYPE.get(this);
    }

    public void setProvokeType(String type){
        PROVOKE_TYPE.set(this,type);
    }
}
