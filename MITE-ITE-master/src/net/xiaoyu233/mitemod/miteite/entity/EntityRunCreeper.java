package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.EntityCreeper;
import net.minecraft.GenericAttributes;
import net.minecraft.World;

public class EntityRunCreeper extends EntityCreeper {

    public EntityRunCreeper(World par1World) {
        super(par1World);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.setEntityAttribute(GenericAttributes.movementSpeed).setAttribute((double)0.5F);
    }

}
