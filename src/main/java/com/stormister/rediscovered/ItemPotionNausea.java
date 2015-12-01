package com.stormister.rediscovered;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPotionNausea extends Item
{
	private String name = "Nausea";
	private boolean splash;
    public ItemPotionNausea(int par2, float par3, boolean par4)
    {
        super();
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabBrewing);
        this.splash = par4;
        if(splash)
        	name = name+"splash";
        GameRegistry.registerItem(this, name);
        setUnlocalizedName(mod_Rediscovered.modid + "_" + name);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {
	    entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 30 * 20, 6));
	    
	    if (!entityPlayer.capabilities.isCreativeMode)
        {
	    	itemStack.stackSize--;
            if (itemStack.stackSize <= 0)
            {
                return new ItemStack(Items.glass_bottle);
            }

            entityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
	
	    return itemStack;
    }    
    
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.DRINK;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
    {
        if (splash)
        {
            if (!playerIn.capabilities.isCreativeMode)
            {
                --itemStackIn.stackSize;
            }

            worldIn.playSoundAtEntity(playerIn, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!worldIn.isRemote)
            {
                worldIn.spawnEntityInWorld(new EntityRediscoveredPotion(worldIn, playerIn, itemStackIn));
            }

            return itemStackIn;
        }
        else
        {
            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
            return itemStackIn;
        }
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
            par3List.add("\u00a77" + "Dizziness (0:30)");                
        
    }

    public String getName()
    {
    	return name;
    }
}
