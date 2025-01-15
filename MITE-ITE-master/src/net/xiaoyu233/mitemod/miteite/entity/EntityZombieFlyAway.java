package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;

public class EntityZombieFlyAway extends EntityZombie {
    public EntityZombieFlyAway(World par1World) {
        super(par1World);
    }

    @Override
    public EntityDamageResult attackEntityAsMob(Entity target) {
        int day = this.worldObj.getDayOfWorld();
        EntityDamageResult result  = super.attackEntityAsMob(target);
        if(result != null) {
            target.motionY += Math.min(0.5d + Math.floor((double) day / 32d) * 0.1d, 1d);
        }
        return result;
    }
}