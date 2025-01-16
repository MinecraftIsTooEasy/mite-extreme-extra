package net.xiaoyu233.mitemod.miteite.render.entity;

import net.minecraft.*;

public class RenderSkeletonFastArrow extends bgu {
    public RenderSkeletonFastArrow() {
        super(new bbz(), 0.5F);
    }
    protected void setTextures() {
        this.setTexture(0, "textures/entity/skeleton_fast_arrow");
    }
    protected bjo a(EntitySkeleton entitySkeleton) {
        return this.textures[0];
    }
    protected bjo a(Entity par1Entity) {
        return this.a((EntitySkeleton) par1Entity);
    }
}
