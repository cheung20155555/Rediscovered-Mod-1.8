package com.stormister.rediscovered;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRediscoveredPotion extends EntityThrowable
{
    private ItemStack potionDamage;
    int meta, color = 16388, randomTilt;
    PotionEffect potioneffect;
    
    public EntityRediscoveredPotion(World worldIn)
    {
    	super(worldIn);
    }

    public EntityRediscoveredPotion(World worldIn, EntityLivingBase p_i1790_2_, ItemStack p_i1790_3_)
    {
        super(worldIn, p_i1790_2_);
        this.potionDamage = p_i1790_3_;
        randomTilt = rand.nextInt(360);
        
        if(potionDamage.getMetadata() == 100){
            potioneffect = new PotionEffect(9, 720, 0);
            color = 16388;
        }
        else if(potionDamage.getMetadata() == 101){
        	potioneffect = new PotionEffect(15, 720, 0);
        	color = 16393;
        }
        else if(potionDamage.getMetadata() == 102){
        	potioneffect = new PotionEffect(4, 720, 0);
        	color = 16398;
        }
        else{
        	potioneffect = new PotionEffect(0, 0, 0);
        	color = 0;
        }
        setMeta(p_i1790_3_);
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    protected float getVelocity()
    {
        return 0.5F;
    }

    protected float getInaccuracy()
    {
        return -20.0F;
    }
    
    public int getRandomTilt(){
    	return randomTilt;
    }
    
    public int getMeta(){
    	return meta;
    }
    
    public void setMeta(ItemStack stack){
    	this.meta = stack.getMetadata();
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (!this.worldObj.isRemote)
        {
            
                AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
                List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

                if (!list1.isEmpty())
                {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                        double d0 = this.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entitylivingbase == p_70184_1_.entityHit)
                            {
                                d1 = 1.0D;
                            }
                            
                            int i = potioneffect.getPotionID();
                            int j = (int)(d1 * (double)potioneffect.getDuration() + 0.5D);

                            if (j > 20)
                            {
                            	entitylivingbase.addPotionEffect(new PotionEffect(i, j, potioneffect.getAmplifier()));
                            }                            
                        }
                    }
                }
            
            this.worldObj.playAuxSFX(2002, new BlockPos(this), color);
            this.setDead();
        }
    }
}