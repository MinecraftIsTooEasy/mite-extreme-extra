package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.EntitySkeleton;
import net.minecraft.GroupDataEntity;
import net.minecraft.PathfinderGoalArrowAttack;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Shadow;

public class EntitySkeletonFastArrow extends EntitySkeleton {

    private PathfinderGoalArrowAttack aiArrowAttack;
    public EntitySkeletonFastArrow(World par1World) {
        super(par1World);
        this.setSkeletonType(0);
        this.aiArrowAttack = new PathfinderGoalArrowAttack(this, (double)1.0F, 20, 30, 15.0F);
    }

    @Override
    public GroupDataEntity onSpawnWithEgg(GroupDataEntity par1EntityLivingData) {
        this.tasks.addTask(4, this.aiArrowAttack);
        return super.onSpawnWithEgg(par1EntityLivingData);
    }
}
