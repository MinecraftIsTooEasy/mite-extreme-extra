package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EntityArrow.class)
public class EntityArrowTrans extends Entity {
   @Shadow
   private Entity shootingEntity;
   @Shadow
   private int ticksInGround;

   public EntityArrowTrans(World par1World) {
      super(par1World);
   }

   @Shadow
   protected void entityInit() {
   }

   @Shadow
   private ItemStack getLauncher() {
      return null;
   }

   @Shadow
   protected void readEntityFromNBT(NBTTagCompound nbtTagCompound) {
   }

   @Overwrite
   public void setThrowableHeading(double par1, double par3, double par5, float velocity, float par8) {
      ItemStack launcher = this.getLauncher();


      if (launcher != null && launcher.getItem() == Items.VIBRANIUM_BOW && this.shootingEntity instanceof EntityPlayer) {
         velocity *= 1.75F;
      }

      if (launcher != null && launcher.getItem() == Item.bowMithril && this.shootingEntity instanceof EntityPlayer) {
         velocity *= 1.25F;
      }

      if (launcher != null && launcher.getItem() == Item.bowAncientMetal && this.shootingEntity instanceof EntityPlayer) {
         velocity *= 1.1F;
      }

      float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
      par1 /= var9;
      par3 /= var9;
      par5 /= var9;
      par1 += super.rand.nextGaussian() * (double)(super.rand.nextBoolean() ? -1 : 1) * 1.856746317E-314D * (double)par8;
      par3 += super.rand.nextGaussian() * (double)(super.rand.nextBoolean() ? -1 : 1) * 1.856746317E-314D * (double)par8;
      par5 += super.rand.nextGaussian() * (double)(super.rand.nextBoolean() ? -1 : 1) * 1.856746317E-314D * (double)par8;
      par1 *= velocity;
      par3 *= velocity;
      par5 *= velocity;
      super.motionX = par1;
      super.motionY = par3;
      super.motionZ = par5;
      float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
      super.prevRotationYaw = super.rotationYaw = (float)(Math.atan2(par1, par5) * 0.0D / 6.984873503E-315D);
      super.prevRotationPitch = super.rotationPitch = (float)(Math.atan2(par3, var10) * 0.0D / 6.984873503E-315D);
      this.ticksInGround = 0;
   }

   @Shadow
   protected void writeEntityToNBT(NBTTagCompound nbtTagCompound) {
   }

   @Redirect(method = "onUpdate",at = @At(ordinal = 0, value = "INVOKE",target = "Lnet/minecraft/ItemArrow;getDamage()F"))
   public float skeletonAddExtraDamage(ItemArrow itemArrow) {
      // 32天 -> 最大200天
      int day = Math.max(Math.min(this.worldObj.getDayOfOverworld() - 32, 168), 0);
      return (itemArrow.getDamage() + day * 0.025f );
   }

   @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityArrow;getLauncher()Lnet/minecraft/ItemStack;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
   public void injectEnchantment(CallbackInfo ci, int var16, Vec3D current_pos, Vec3D future_pos, Raycast raycast, RaycastCollision var4, RaycastCollision block_collision, float var20, Entity entity_hit, float damage_multiplier, float var24, DamageSource var22, Damage damage, boolean entity_immune_to_arrow, EntityDamageResult result, EntityLiving var25){
      ItemStack launcher = this.getLauncher();
      if (launcher != null && this.rand.nextFloat() < launcher.getEnchantmentLevelFraction(Enchantments.enchantmentSlowdown)) {
         int level = launcher.getEnchantmentLevel(Enchantments.enchantmentSlowdown);
         var25.addPotionEffect(new MobEffect(MobEffectList.moveSlowdown.id, 160 + launcher.getEnchantmentLevelFractionOfInteger(Enchantments.enchantmentSlowdown, 240), level > 0 ? rand.nextInt(level) : 0));
      }
   }

}
