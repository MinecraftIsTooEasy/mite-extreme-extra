package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
@Mixin(bew.class)
public class MovementInputFromOptionsTrans extends bev {
    @Shadow
    private aul e;
    @Overwrite
    public void a() {
        this.a = 0.0F;
        this.b = 0.0F;
        if (!Minecraft.O.h.isGhost()) {
            if (this.e.I.e) {
                if(Minecraft.getClientPlayer().isPotionActive(MobEffectList.field_76442_z)) {
                    --this.b;
                } else {
                    ++this.b;
                }
            }
            if (this.e.K.e) {
                if(Minecraft.getClientPlayer().isPotionActive(MobEffectList.field_76442_z)) {
                    ++this.b;
                } else {
                    --this.b;
                }
            }

            if (this.e.J.e) {
                if(Minecraft.getClientPlayer().isPotionActive(MobEffectList.field_76442_z)) {
                    --this.a;
                } else {
                    ++this.a;
                }
            }

            if (this.e.L.e) {
                if(Minecraft.getClientPlayer().isPotionActive(MobEffectList.field_76442_z)) {
                    ++this.a;
                } else {
                    --this.a;
                }
            }
            this.c = this.e.M.e;
            this.d = this.e.Q.e;
            if (this.d) {
                this.a = (float)((double)this.a * 0.3);
                this.b = (float)((double)this.b * 0.3);
            }

        }
    }
}
