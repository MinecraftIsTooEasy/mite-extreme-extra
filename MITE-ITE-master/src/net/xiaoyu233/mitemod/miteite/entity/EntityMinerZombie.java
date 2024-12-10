package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.util.Constant;

public class EntityMinerZombie extends EntityZombie {

    public EntityMinerZombie(World par1World) {
        super(par1World);
    }

    protected void addRandomEquipment() {
        ItemStack pickaxe = new ItemStack(Items.VIBRANIUM_PICKAXE);
        pickaxe.addEnchantment(Enchantment.efficiency, 10);
        this.setCurrentItemOrArmor(0, pickaxe);
    }

    @Override
    public boolean canNeverPickUpItem(Item item) {
        return true;
    }

    @Override
    public boolean canBeDisarmed() {
        return false;
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
    protected void dropFewItems(boolean recently_hit_by_player, DamageSource damage_source) {
        if (recently_hit_by_player){
            int day = this.getWorld().getDayOfOverworld();
            int diamond_count = (day / 32) > 3 ? 3 : (day / 32);
            for (int i1 = 0; i1 < diamond_count; i1++) {
                this.dropItem(Item.emerald);
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        int day = this.getWorld().getDayOfOverworld();
        double x = day / 8 - 8;
        double rate = (0.5+ x / (20 + Math.abs(x)));
        int healthRate = Math.min(day / 16, 10);
        this.setEntityAttribute(GenericAttributes.attackDamage, rate * 50);
        this.setEntityAttribute(GenericAttributes.maxHealth, rate * 60 + healthRate * 10);
        this.setEntityAttribute(GenericAttributes.movementSpeed, 0.3D);
    }
}
