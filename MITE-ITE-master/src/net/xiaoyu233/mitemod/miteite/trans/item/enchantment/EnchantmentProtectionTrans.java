package net.xiaoyu233.mitemod.miteite.trans.item.enchantment;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentProtection.class)
public class EnchantmentProtectionTrans extends EnchantmentTrans {

   @Shadow
   public boolean canEnchantItem(Item item) {
      return false;
   }

   @Shadow
   public boolean canReduceDamage(DamageSource damage_source) {
      return false;
   }

   public boolean canReduceDamageP(DamageSource damage_source) {
      return this.canReduceDamage(damage_source);
   }

   @Shadow
   public String getNameSuffix() {
      return null;
   }

   @SoftOverride
   public int getNumLevelsForVibranium() {
      return 5;
   }

   @Shadow
   public boolean isOnCreativeTab(CreativeModeTab creativeModeTab) {
      return false;
   }

   @Redirect(method = "getTotalProtectionOfEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/ItemStack;getEnchantmentLevelFraction(Lnet/minecraft/Enchantment;)F"))
   private static float addProtectionByGem(ItemStack instance, Enchantment enchantment) {
      float before = instance.getEnchantmentLevelFraction(enchantment);
      if(instance.getItem() instanceof ItemCuirass) {
         before += (float) instance.getGemMaxLevel(GemModifierTypes.fireproof) / 6f;
      }
      if(instance.getItem() instanceof ItemLeggings) {
         before += (float) instance.getGemMaxLevel(GemModifierTypes.explosionProof) / 6f;
      }
      return before;
   }
}
