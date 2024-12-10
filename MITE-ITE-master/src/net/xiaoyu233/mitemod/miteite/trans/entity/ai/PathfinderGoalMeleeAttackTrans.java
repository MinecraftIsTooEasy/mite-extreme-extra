package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityMinerZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PathfinderGoalMeleeAttack.class)
public class PathfinderGoalMeleeAttackTrans {
    @Shadow
    EntityCreature attacker;
    @Shadow
    Class classTarget;
    @Inject(method = "shouldExecute", at = @At("HEAD"),  cancellable = true)
    public void preventHurtAnimalAndViligaer(CallbackInfoReturnable<Boolean> cir){
        if ((this.classTarget == EntityAnimal.class || this.classTarget == EntityVillager.class) && this.attacker instanceof EntityMinerZombie) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
