package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.EntityMinerZombie;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PathfinderGoalNearestAttackableTarget.class)
public class PathfinderGoalNearestAttackableTargetTrans extends re {
    @Shadow
    private EntityLiving targetEntity;
    @Shadow
    @Final
    private  Class targetClass;

    public PathfinderGoalNearestAttackableTargetTrans(EntityCreature par1EntityCreature, boolean par2) {
        super(par1EntityCreature, par2);
    }

    @Inject(method = "shouldExecute", at = @At("HEAD"),  cancellable = true)
    public void preventHurtAnimalAndViligaer(CallbackInfoReturnable<Boolean> cir){
        if ((this.targetClass == EntityAnimal.class || this.targetClass == EntityVillager.class) && this.taskOwner instanceof EntityMinerZombie) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Shadow
    public boolean shouldExecute() {
        return false;
    }
}
