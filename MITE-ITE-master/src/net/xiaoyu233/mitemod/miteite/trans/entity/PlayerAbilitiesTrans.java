package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.GemModifierTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAbilities.class)
public class PlayerAbilitiesTrans {
    @Redirect(method = "getWalkSpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/EnchantmentManager;getSpeedModifier(Lnet/minecraft/EntityLiving;)F"))
    public float getWalkSpeedEnhance(EntityLiving par0EntityLivingBase){
        float before = EnchantmentManager.getSpeedModifier(par0EntityLivingBase);
        if(par0EntityLivingBase instanceof EntityPlayer){
            EntityPlayer entityPlayer =  (EntityPlayer) par0EntityLivingBase;
            ItemStack boots = entityPlayer.getBoots();
            if(boots != null) {
                before += boots.getGemMaxNumeric(GemModifierTypes.speed) * 0.05f;
            }
        }
        return before;
    }
}
