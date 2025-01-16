package net.xiaoyu233.mitemod.miteite.trans.item.potion;

import net.minecraft.MobEffectList;
import net.xiaoyu233.mitemod.miteite.item.potion.MobEffectListChaos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEffectList.class)
public class MobEffectListTrans{

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void injectCtor(CallbackInfo callbackInfo) {
        MobEffectListAccessor.setField_76442_z(new MobEffectListChaos(24, true, 0xFF0000));
    }
}
