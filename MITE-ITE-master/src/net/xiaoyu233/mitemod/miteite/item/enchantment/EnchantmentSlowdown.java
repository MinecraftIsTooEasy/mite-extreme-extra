package net.xiaoyu233.mitemod.miteite.item.enchantment;

import net.minecraft.*;

public class EnchantmentSlowdown extends Enchantment{
    protected EnchantmentSlowdown(int id, yq rarity, int difficulty) {
        super(id, rarity, difficulty);
    }

    @Override
    public int getNumLevels() {
        return 5;
    }

    @Override
    public String getNameSuffix() {
        return "slowdown";
    }

    @Override
    public boolean canEnchantItem(Item item) {
        return (item instanceof ItemBow);
    }

    @Override
    public boolean isOnCreativeTab(CreativeModeTab creative_tab)
    {
        return this == looting ? creative_tab == CreativeModeTab.tabCombat : creative_tab == CreativeModeTab.tabTools;
    }
}
