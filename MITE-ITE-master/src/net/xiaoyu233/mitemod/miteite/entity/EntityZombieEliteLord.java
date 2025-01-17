package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class EntityZombieEliteLord extends EntityRevenant {
    public static int[] spawnEntityList = new int[]{EntityTypes.getEntityID(EntityMinerZombie.class), EntityTypes.getEntityID(EntityGhoul.class), EntityTypes.getEntityID(EntityWight.class), EntityTypes.getEntityID(EntityRevenant.class)};
    private int spawnCounter;

    private int spawnSums;
    public EntityZombieEliteLord(World par1World) {
        super(par1World);
    }

    @Override
    protected void addRandomEquipment() {
        int day = this.getWorld().getDayOfOverworld();
        this.setCurrentItemOrArmor(0, (new ItemStack(Items.VIBRANIUM_WAR_HAMMER, 1)).randomizeForMob(this, day > 64));
        this.setCurrentItemOrArmor(1, (new ItemStack(Items.VIBRANIUM_HELMET, 1)).randomizeForMob(this, day > 64));
        this.setCurrentItemOrArmor(2, (new ItemStack(Items.VIBRANIUM_CHESTPLATE, 1)).randomizeForMob(this, day > 64));
        this.setCurrentItemOrArmor(3, (new ItemStack(Items.VIBRANIUM_LEGGINGS, 1)).randomizeForMob(this, day > 64));
        this.setCurrentItemOrArmor(4, (new ItemStack(Items.VIBRANIUM_BOOTS, 1)).randomizeForMob(this, day > 64));
        this.addPotionEffect(new MobEffect(1, 2147483647, 0));
        this.addPotionEffect(new MobEffect(5, 2147483647, 0));
    }

    @Override
    public boolean getCanSpawnHere(boolean perform_light_check) {
        Boolean before=  super.getCanSpawnHere(perform_light_check);
        if(before  && this.worldObj.getDayOfOverworld() > 32) {
            return true;
        } else{
            return false;
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        double x = day / 7 - 7;
        double rate = (0.5+ x / (20 + Math.abs(x)));
        int healthRate = Math.min(day / 16, 10);
        this.setEntityAttribute(GenericAttributes.attackDamage, 12.0D + (double)day / 24.0D);
        this.setEntityAttribute(GenericAttributes.maxHealth, rate * 60 + healthRate * 15);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.3D);
    }

    @Override
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            int day = this.getWorld().getDayOfOverworld();
            int diamond_count = (day / 32) >= 3 ? 3 : ((day / 32) + 1);
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.emerald);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.getWorld().isRemote){
            EntityLiving target = this.getAttackTarget();
            if(target instanceof EntityPlayer) {
                if (spawnSums < 20) {
                    if (this.spawnCounter < 50) {
                        ++this.spawnCounter;
                    } else {
                        Entity entity = EntityTypes.createEntityByID(spawnEntityList[rand.nextInt(spawnEntityList.length)], this.worldObj);
                        if(entity instanceof EntityMonster){
                            EntityMonster entityMonster = (EntityMonster) entity;
                            entityMonster.setPosition(this.posX, this.posY, this.posZ);
                            entityMonster.refreshDespawnCounter(-9600);
                            entityMonster.addPotionEffect(new MobEffect(MobEffectList.damageBoost.id, 2147483647, 0));
                            this.worldObj.spawnEntityInWorld(entity);
                            entityMonster.onSpawnWithEgg(null);
                            entityMonster.setAttackTarget(this.getTarget());
                            entity.entityFX(EnumEntityFX.summoned);
                            this.spawnCounter = 0;
                            ++spawnSums;
                        }
                    }
                }
            }
        }
    }

}
