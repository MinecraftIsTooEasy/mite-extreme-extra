package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityAnimalWatcher;
import net.minecraft.EntityMonster;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.entity.EntityMinerZombie;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAnimalWatcher.class)
public class EntityAnimalWatcherTrans  extends EntityMonster {

    public EntityAnimalWatcherTrans(World par1World) {
        super(par1World);
    }

    @Inject(method = "getCooloffForBlock", at = @At("HEAD"), cancellable = true)
    public void boostMinerDigSpeed(CallbackInfoReturnable<Integer> cir){
        if(ReflectHelper.dyCast(EntityAnimalWatcher.class, this) instanceof EntityMinerZombie){
            cir.setReturnValue(5);
            cir.cancel();
        }

    }
}
