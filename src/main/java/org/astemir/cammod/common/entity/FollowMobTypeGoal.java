package org.astemir.cammod.common.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class FollowMobTypeGoal<T extends LivingEntity> extends Goal {


   private final PathfinderMob mob;
   private LivingEntity followTarget;
   private final LevelReader level;
   private final double speedModifier;
   private final PathNavigation navigation;
   private int timeToRecalcPath;
   private final float stopDistance;
   private final float startDistance;
   private float oldWaterCost;
   private final boolean canFly;
   protected final Class<T> followClass;


   public FollowMobTypeGoal(PathfinderMob pTamable, Class<T> classToFollow,double pSpeedModifier,float startDistance,float stopDistance, boolean pCanFly) {
      this.mob = pTamable;
      this.level = pTamable.level;
      this.speedModifier = pSpeedModifier;
      this.navigation = pTamable.getNavigation();
      this.startDistance = startDistance;
      this.stopDistance = stopDistance;
      this.canFly = pCanFly;
      this.followClass = classToFollow;
      this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(pTamable.getNavigation() instanceof GroundPathNavigation) && !(pTamable.getNavigation() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobTypeGoal");
      }
   }



   public boolean canUse() {
      this.followTarget = this.mob.level.getNearestEntity(this.mob.level.getEntitiesOfClass(this.followClass, this.mob.getBoundingBox().inflate((double)20, 3.0D, (double)20), (p_148078_) -> {
         return true;
      }), TargetingConditions.DEFAULT, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
      if (followTarget == null) {
         return false;
      } else if (followTarget.isSpectator()) {
         return false;
      } else if (this.mob.distanceToSqr(followTarget) < (double)(this.startDistance * this.startDistance)) {
         return false;
      }
      return true;
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean canContinueToUse() {
      if (this.navigation.isDone()) {
         return false;
      }else {
         return !(this.mob.distanceToSqr(this.followTarget) <= (double)(this.stopDistance * this.stopDistance));
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void start() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.mob.getPathfindingMalus(BlockPathTypes.WATER);
      this.mob.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void stop() {
      this.navigation.stop();
      this.mob.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
   }

   public void tick() {
      this.mob.getLookControl().setLookAt(this.followTarget, 10.0F, (float)this.mob.getMaxHeadXRot());
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = this.adjustedTickDelay(10);
         if (!this.mob.isLeashed() && !this.mob.isPassenger()) {
            if (this.mob.distanceToSqr(this.followTarget) >= 144.0D) {
               this.teleportToOwner();
            } else {
               this.navigation.moveTo(this.followTarget, this.speedModifier);
            }

         }
      }
   }

   private void teleportToOwner() {
      BlockPos blockpos = this.followTarget.blockPosition();

      for(int i = 0; i < 10; ++i) {
         int j = this.randomIntInclusive(-3, 3);
         int k = this.randomIntInclusive(-1, 1);
         int l = this.randomIntInclusive(-3, 3);
         boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
         if (flag) {
            return;
         }
      }

   }

   private boolean maybeTeleportTo(int pX, int pY, int pZ) {
      if (Math.abs((double)pX - this.followTarget.getX()) < 2.0D && Math.abs((double)pZ - this.followTarget.getZ()) < 2.0D) {
         return false;
      } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
         return false;
      } else {
         this.mob.moveTo((double)pX + 0.5D, (double)pY, (double)pZ + 0.5D, this.mob.getYRot(), this.mob.getXRot());
         this.navigation.stop();
         return true;
      }
   }

   private boolean canTeleportTo(BlockPos pPos) {
      BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, pPos.mutable());
      if (blockpathtypes != BlockPathTypes.WALKABLE) {
         return false;
      } else {
         BlockState blockstate = this.level.getBlockState(pPos.below());
         if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
            return false;
         } else {
            BlockPos blockpos = pPos.subtract(this.mob.blockPosition());
            return this.level.noCollision(this.mob, this.mob.getBoundingBox().move(blockpos));
         }
      }
   }

   private int randomIntInclusive(int pMin, int pMax) {
      return this.mob.getRandom().nextInt(pMax - pMin + 1) + pMin;
   }
}