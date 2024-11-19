package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityCreeper.class)
public class EntityCreeperTrans extends EntityMonster {
   @Shadow
   protected float explosionRadius;
   @Shadow
   private int fuseTime;

   public EntityCreeperTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.setEntityAttribute(GenericAttributes.movementSpeed).setAttribute(this.worldObj.isUnderworld() ? 0.3D : 0.25D);
      this.setEntityAttribute(GenericAttributes.maxHealth, 20 + (double)day / 4.0D);
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.explosionRadius = 1.25f;
//      this.setExplosionTime(Math.max(this.getExplosionTime() * 3 - (int)((double)day * 0.6D), 40));
      this.setExplosionTime(Configs.wenscConfig.creeperFuseTime.ConfigValue);
   }

   public int getExplosionTime() {
      return this.fuseTime;
   }

   public void setExplosionTime(int br) {
      this.fuseTime = br;
   }

   @Inject(method = "onDeath",at = @At("HEAD"))
   public void injectOnDeath(DamageSource par1DamageSource, CallbackInfo callbackInfo){
         if (!this.worldObj.isRemote) {
               if(this.rand.nextInt(100) < 3){
                     for(int integer = 0; integer < 4; ++integer) {
                           EntityCreeper creeper = new EntityCreeper(this.worldObj);
                           creeper.setPosition(this.getBlockPosX(), this.getFootBlockPosY(), this.getBlockPosZ());
                           creeper.refreshDespawnCounter(-9600);
                           this.worldObj.spawnEntityInWorld(creeper);
                           creeper.onSpawnWithEgg(null);
                           creeper.setAttackTarget(this.getTarget());
                     }
               }
         }
   }

}
