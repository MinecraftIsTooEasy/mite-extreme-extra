package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.entity.*;
//import net.xiaoyu233.mitemod.miteite.entity.EntityThunderMan;
import net.xiaoyu233.mitemod.miteite.render.entity.*;
//import net.xiaoyu233.mitemod.miteite.render.entity.RenderEntityThunderMan;
import net.xiaoyu233.mitemod.miteite.trans.entity.EntityZombieChaos;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(bgl.class)
public class RenderManagerTrans {
   @Shadow
   private final Map<Class<? extends Entity>, bgm> q = new HashMap<>();

   private RenderManagerTrans() {}

   @Inject(
           method = "<init>",
           at = @At(value = "RETURN"))
   private void injectRegister(CallbackInfo callback){
      this.q.put(EntityAncientDragon.class, new RenderAncientDragon());
      this.q.put(EntityAnnihilationSkeleton.class, new RenderAnnihilationSkeleton());
      this.q.put(EntityWanderingWitch.class, new RenderWanderingWitch());
      this.q.put(EntityZombieFlyAway.class, new RenderZombieFlyAway());
      this.q.put(EntityZombieChaos.class, new RenderZombieChaos());
      this.q.put(EntitySkeletonFastArrow.class, new RenderZombieChaos());
      this.q.put(EntityInvisibleCreeper.class, new RenderInvisibleCreeper());
      this.q.put(EntityRunCreeper.class, new RenderRunCreeper());
      this.q.put(EntityPhaseCreeper.class, new RenderPhaseCreeper());

//      this.q.put(EntityThunderMan.class, new RenderEntityThunderMan());
      for (bgm o : this.q.values()) {
         o.a(ReflectHelper.dyCast(bgl.class, this));
      }
   }
}
