package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;

public class EntityZombieChaos extends EntityZombie {

    public EntityZombieChaos(World par1World) {
        super(par1World);
    }

    @Override
    public EntityDamageResult attackEntityAsMob(Entity target) {
        EntityDamageResult result = super.attackEntityAsMob(target);
        if(result != null && target instanceof EntityPlayer)  {
            EntityPlayer entityPlayer = (EntityPlayer)target;
            entityPlayer.addPotionEffect(new MobEffect(MobEffectList.field_76442_z.id, 5 * 20,0));
        }
        return result;
    }
}
