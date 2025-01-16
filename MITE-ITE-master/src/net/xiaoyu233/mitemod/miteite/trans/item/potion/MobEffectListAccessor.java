package net.xiaoyu233.mitemod.miteite.trans.item.potion;

import net.minecraft.MobEffectList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobEffectList.class)
public interface MobEffectListAccessor {
    @Accessor("field_76442_z")
    public static void setField_76442_z(MobEffectList mobEffectList) {
        throw new AssertionError();
    }
}
