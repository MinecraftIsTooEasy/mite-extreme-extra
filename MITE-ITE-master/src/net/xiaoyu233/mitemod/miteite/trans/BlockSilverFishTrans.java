package net.xiaoyu233.mitemod.miteite.trans;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockMonsterEggs.class)
public class BlockSilverFishTrans extends Block{
    protected BlockSilverFishTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Overwrite
    public static EntitySilverfish getSilverfishTypeForMetadata(World world, int metadata) {
        if (metadata == 3) {
            return new EntityNetherspawn(world);
        } else if (metadata == 4) {
            return new EntityCopperspine(world);
        } else {
            return (EntitySilverfish)(world.isUnderworld() ? new EntityHoarySilverfish(world) : world.rand.nextFloat() < 0.2f ? new EntityNetherspawn(world) : new EntitySilverfish(world));
        }
    }
}
