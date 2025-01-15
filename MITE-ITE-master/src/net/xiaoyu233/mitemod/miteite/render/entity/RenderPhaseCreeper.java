package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.Entity;
import net.minecraft.EntityInsentient;
import net.minecraft.bgg;
import net.minecraft.bjo;

public class RenderPhaseCreeper extends bgg {
    protected void setTextures() {
        this.setTexture(0, "textures/entity/phase_creeper");
    }

    protected bjo a(EntityInsentient par1EntityLiving) {
        return this.textures[0];
    }

    protected bjo a(Entity par1Entity) {
        return this.textures[0];
    }
}
