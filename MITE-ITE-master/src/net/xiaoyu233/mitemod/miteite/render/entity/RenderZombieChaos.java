package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.*;

public class RenderZombieChaos extends bgu {
    public RenderZombieChaos() {
        super(new bcm(), 0.5F, 1.0F);
    }
    protected void setTextures() {
        this.setTexture(0, "textures/entity/zombie_chaos");
    }
    protected bjo a(EntityZombie par1EntityZombie) {
        return this.textures[0];
    }
    protected bjo a(Entity par1Entity) {
        return this.a((EntityZombie)par1Entity);
    }
}
