package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.EntityInsentient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityInsentient.class)
public class EntityInsentientTrans {
    @Redirect(method = "findPlayerToAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;setErrorMessage(Ljava/lang/String;)V"))
    public void removeErrorMessage(String message){

    }
}
