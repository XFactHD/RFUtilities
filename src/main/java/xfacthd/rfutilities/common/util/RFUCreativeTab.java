package xfacthd.rfutilities.common.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import xfacthd.rfutilities.RFUtilities;
import xfacthd.rfutilities.common.RFUContent;
import xfacthd.rfutilities.common.data.CapacitorType;

public final class RFUCreativeTab extends CreativeModeTab
{
    public static final RFUCreativeTab INSTANCE = new RFUCreativeTab();

    private ItemStack stack = null;

    private RFUCreativeTab() { super(RFUtilities.MOD_ID); }

    @Override
    public ItemStack makeIcon()
    {
        if (stack == null)
        {
            stack = new ItemStack(RFUContent.getCapacitorBlock(CapacitorType.THERMAL_BASIC));
        }
        return stack;
    }

    @Override
    public void fillItemList(NonNullList<ItemStack> items)
    {
        super.fillItemList(items);
        //TODO: sort
    }
}
