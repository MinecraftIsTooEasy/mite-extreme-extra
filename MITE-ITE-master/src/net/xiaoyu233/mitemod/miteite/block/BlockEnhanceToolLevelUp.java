package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;

public class BlockEnhanceToolLevelUp extends Block {

    public BlockEnhanceToolLevelUp(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
        this.setCreativeTab(CreativeModeTab.tabBlock);
        this.setBounds(true);
        this.setHardness(BlockHardness.stone);
        this.setResistance(10.0F);
        this.setStepSound(soundStoneFootstep);
    }

    public int dropBlockAsEntityItem(BlockBreakInfo info) {
        if (!info.wasExploded() && !info.wasCrushed()) {
            return super.dropBlockAsEntityItem(info, Blocks.blockEnhanceToolLevelUp);
        } else {
            return 0;
        }
    }

    private void setBounds(boolean for_all_threads) {
        //minX minY minZ maxX maxY maxZ
        this.setBlockBounds(0.1d, 0d, 0.3d, 0.9d, 0.3d, 0.7d, for_all_threads);
    }
    public boolean isStandardFormCube(boolean[] is_standard_form_cube, int metadata) {
        return false;
    }

    public void setBlockBoundsForItemRender(int item_damage) {
        this.setBounds(false);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, EnumFace face, float offset_x, float offset_y, float offset_z) {
        if(world.isWorldServer()){
            ItemStack itemStack =  player.getHeldItemStack();
            if (itemStack != null && (itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemArmor)) {
                if(itemStack.stackTagCompound != null) {
                    if (itemStack.stackTagCompound.hasKey("enhance_times")) {
                        int enhance_times = itemStack.stackTagCompound.getInteger("enhance_times");
                        if (enhance_times < 3) {
                            int currentLevel = itemStack.stackTagCompound.getInteger("tool_level");
                            int nextLevelExpReq = itemStack.getItem().getExpReqForLevel(currentLevel, itemStack.getItem().isWeapon(itemStack.getItem()));
                            itemStack.getItem().addExpForTool(itemStack, player, nextLevelExpReq - itemStack.stackTagCompound.getInteger("tool_exp"));
                            world.setBlockToAir(x, y, z);
                            itemStack.stackTagCompound.setInteger("enhance_times", enhance_times + 1);
                        } else {
                            player.addChatMessage("该物品最多增加3次副属性等级");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
